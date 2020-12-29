package com.thanaa.restaurantweatherapp.ui

import android.animation.Animator
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidadvance.topsnackbar.TSnackbar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.thanaa.restaurantweatherapp.R
import com.thanaa.restaurantweatherapp.adapter.PlanAdapter
import com.thanaa.restaurantweatherapp.database.AppDatabase
import com.thanaa.restaurantweatherapp.databinding.FragmentPlanBinding
import com.thanaa.restaurantweatherapp.model.Plan
import com.thanaa.restaurantweatherapp.repository.PlanRepository
import com.thanaa.restaurantweatherapp.utils.SwipeToComplete
import com.thanaa.restaurantweatherapp.utils.SwipeToDelete
import com.thanaa.restaurantweatherapp.viewmodel.PlanViewModel
import com.thanaa.restaurantweatherapp.viewmodel.providerfactory.PlanProviderFactory


class PlanFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentPlanBinding? = null
    private val binding get() = _binding!!
    private lateinit var planViewModel: PlanViewModel
    lateinit var recyclerView: RecyclerView
    private lateinit var auth: FirebaseAuth
    private val fireStoreDB = FirebaseFirestore.getInstance()
    private lateinit var mediaPlayer: MediaPlayer
    private val adapter: PlanAdapter by lazy { PlanAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.plans)
        _binding = FragmentPlanBinding.inflate(inflater, container, false)
        val repository = PlanRepository(AppDatabase.getDatabase(requireContext()))
        val factory = PlanProviderFactory(repository)
        planViewModel = ViewModelProvider(this, factory).get(PlanViewModel::class.java)
        firebaseProfile()
        setData()

        //Menu of Deletion and Sorting
        setHasOptionsMenu(true)

        binding.addButton.setOnClickListener {
            val action = PlanFragmentDirections.actionPlanFragmentToAddFragment()
            findNavController().navigate((action))
        }
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmRemoval()
            R.id.newest -> planViewModel.sortByNewDate.observe(this, { adapter.setData(it) })
            R.id.oldest -> planViewModel.sortByOldDate.observe(this, { adapter.setData(it) })
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }


    private fun firebaseProfile() {
        //Firebase auth instance
        auth = FirebaseAuth.getInstance()

        //set up auth image to image view
        Glide.with(this).load(auth.currentUser?.photoUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(binding.profileImage)
        //set up username
        binding.username.text = auth.currentUser?.displayName
        binding.email.text = auth.currentUser?.email
    }

    private fun setData() {
        recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        swipeToDelete(recyclerView)
        swipeToComplete(recyclerView)
        planViewModel.getAllData.observe(viewLifecycleOwner, {
            planViewModel.checkIfDatabaseEmpty(it)
            adapter.setData(it)

        })
        //check if the database is empty to show empty view
        planViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyView(it)
        })

    }


    private fun showEmptyView(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            binding.emptyView.visibility = View.VISIBLE
            binding.emptyListText.visibility = View.VISIBLE
        } else {
            binding.emptyView.visibility = View.INVISIBLE
            binding.emptyListText.visibility = View.INVISIBLE
        }
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            planViewModel.deleteAll()
            val snackbar = TSnackbar.make(
                requireView(),
                getString(R.string.plans_successfully_deleted),
                TSnackbar.LENGTH_LONG
            )
            snackbar.setActionTextColor(Color.WHITE)
            val snackbarView = snackbar.view
            snackbarView.setBackgroundColor(Color.parseColor("#99CC00"))
            val textView =
                snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.WHITE)
            snackbar.show()
        }
        builder.setNegativeButton(getString(R.string.no)) { _, _ -> }
        builder.setTitle(getString(R.string.delete_all_plans))
        builder.setMessage(getString(R.string.are_you_sure_plans))
        builder.create().show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchInDB(query)
            //Hide soft keys
            hideKeyBoard()
        }
        return true
    }


    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchInDB(query)
        }
        return true
    }

    private fun searchInDB(query: String) {
        var searchQuery = query
        searchQuery = "%$searchQuery%"
        planViewModel.searchTitle(searchQuery).observe(this, { list ->
            list?.let {
                adapter.setData(it)
            }
        })
    }

    private fun hideKeyBoard() {
        activity?.let {
            val inputManager =
                it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val view = it.currentFocus
            if (view != null) {
                inputManager.hideSoftInputFromWindow(
                    view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }

    }

    private fun swipeToDelete(recycleView: RecyclerView) {
        val swipeToDeleteCallBack = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]
                planViewModel.deleteItem(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                restoreDeletedData(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recycleView)
    }


    private fun swipeToComplete(recycleView: RecyclerView) {
        val swipeToDeleteCallBack = object : SwipeToComplete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]
                planViewModel.deleteItem(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                increaseScore()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recycleView)
    }

    private fun restoreDeletedData(view: View, deleteItem: Plan, position: Int) {
        val snackBar = Snackbar.make(
            view,
            "'${deleteItem.title}'" + getString(R.string.has_been_deleted),
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") {
            planViewModel.insertData(deleteItem)
            adapter.notifyItemChanged(position)
        }
        snackBar.show()
    }

    fun increaseScore() {
        //increase score
        setUserScore()
        //Congrats snackbar
        val snackbar = TSnackbar.make(
            requireView(),
            getString(R.string.congrats),
            TSnackbar.LENGTH_LONG
        )
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.parseColor("#99CC00"))
        val textView =
            snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        snackbar.show()

        //show coin
        binding.coin.visibility = View.VISIBLE
        binding.coin.playAnimation()
        binding.coin.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                binding.coin.visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
        //play sound
        playSound()

    }

    private fun playSound() {
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.ching)
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
        }


    }

    private fun setUserScore() {
        val docRef = fireStoreDB.collection("users").document(auth.currentUser?.uid!!)
        val updates = hashMapOf<String, Any>(
            "score" to FieldValue.increment(10)
        )
        docRef.update(updates).addOnCompleteListener { }
    }


}
