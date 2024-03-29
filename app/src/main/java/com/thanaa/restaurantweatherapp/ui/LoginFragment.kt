package com.thanaa.restaurantweatherapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.thanaa.restaurantweatherapp.R
import com.thanaa.restaurantweatherapp.databinding.FragmentLoginBinding
import com.thanaa.restaurantweatherapp.model.User
import com.thanaa.restaurantweatherapp.utils.Constants


class LoginFragment : Fragment() {
    private val TAG = "LoginFragment"
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var bottomAppBar: BottomAppBar
    lateinit var fab: FloatingActionButton
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val fireStoreDB = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        private const val RC_SIGN_IN = 111
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.login)
        hideNavigation()
        _binding = FragmentLoginBinding.inflate(inflater, container, false)


        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        //Firebase auth instance
        auth = FirebaseAuth.getInstance()
        //if the user already logged in then go to UserInfoFragment
        if (auth.currentUser != null)
            findNavController().navigate(R.id.mapsFragment)

        binding.loginGoogle.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            signIn()
        }

        return binding.root
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!

                    firebaseAuthWithGoogle(account.idToken!!)
                    getUserDetails()
                } catch (e: ApiException) {
                    print(e)
                }
            }
        }
        binding.progressBar.visibility = View.GONE
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    auth = FirebaseAuth.getInstance()
                    val docRef = fireStoreDB.collection("users").document(auth.currentUser?.uid!!)
                    docRef.addSnapshotListener { snapshot, e ->


                        if (auth.currentUser != null) {
                            val user = User(
                                firebaseUser.uid,
                                firebaseUser.displayName.toString(),
                                firebaseUser.email.toString(),
                                auth.currentUser!!.photoUrl.toString(),
                                snapshot!!.get("score").toString().toInt(),
                                snapshot.get("level").toString()
                            )

                            fireStoreDB.collection(Constants.USERS)
                                .document(user.id)
                                .set(user, SetOptions.merge())
                                .addOnSuccessListener {

                                }.addOnFailureListener {

                                }
                        }
                    }
                    findNavController().navigate(R.id.mapsFragment)
                }

            }
    }

    private fun getUserDetails() {
        fireStoreDB.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    //hide navigation button
    private fun hideNavigation() {
        bottomNavigationView = (activity as MainActivity).bottomNavigationView
        fab = (activity as MainActivity).fab
        bottomAppBar = (activity as MainActivity).bottomAppBar
        bottomNavigationView.visibility = View.GONE
        bottomAppBar.visibility = View.GONE
        fab.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}