package com.thanaa.restaurantweatherapp

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thanaa.restaurantweatherapp.model.Plan

class PlanAdapter : RecyclerView.Adapter<PlanAdapter.MyViewHolder>() {

    var dataList = emptyList<Plan>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title_tv)
        private val description: TextView = view.findViewById(R.id.description_tv)
        private val date: TextView = view.findViewById(R.id.date)
        private val location: TextView = view.findViewById(R.id.location)
        private val flag: ImageView = view.findViewById(R.id.flag)
        private val color: ImageView = view.findViewById(R.id.image_view_color)
        fun bind(plan: Plan) {

            title.text = plan.title
            description.text = plan.description
            location.text = plan.location
//        flag.setImageDrawable(ResourcesCompat.getDrawable(plan.icon))
//        color.setImageDrawable(ResourcesCompat.getDrawable(plan.color))
            val dateFormat = DateFormat.format("EEE, MMM, dd", plan.date).toString()
            date.text = dateFormat

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
        holder.bind(dataList[position])


    }

    //notify the recycler view about these changes
    fun setData(plans: List<Plan>) {
        this.dataList = plans
        notifyDataSetChanged()
    }


}