package com.thanaa.restaurantweatherapp

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.thanaa.restaurantweatherapp.model.Plan

class PlanAdapter : RecyclerView.Adapter<PlanAdapter.MyViewHolder>() {

    var dataList = emptyList<Plan>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title_tv)
        private val description: TextView = view.findViewById(R.id.description_tv)
        private val date: TextView = view.findViewById(R.id.date_tv)
        private val location: TextView = view.findViewById(R.id.location)
        private val flag: ImageView = view.findViewById(R.id.flag)
        private val color: ImageView = view.findViewById(R.id.image_view_color)
        private val rowBackground: ConstraintLayout = view.findViewById(R.id.row_background)
        fun bind(plan: Plan, holder: MyViewHolder) {

            title.text = plan.title
            description.text = plan.description
            location.text = plan.location
            flag.setImageDrawable(ContextCompat.getDrawable(holder.flag.context, plan.icon!!))
            color.setImageDrawable(ContextCompat.getDrawable(holder.color.context, plan.color!!))
            val dateFormat = DateFormat.format("EEE, MMM, dd", plan.date).toString()
            date.text = dateFormat

            //when user clicks on item it navigate to update of the data position
            rowBackground.setOnClickListener {
                val action = PlanFragmentDirections.actionPlanFragmentToUpdateFragment(plan)
                holder.itemView.findNavController().navigate(action)
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.plan_row, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position], holder)


    }

    //notify the recycler view about these changes
    fun setData(plans: List<Plan>) {
        this.dataList = plans
        notifyDataSetChanged()
    }


}