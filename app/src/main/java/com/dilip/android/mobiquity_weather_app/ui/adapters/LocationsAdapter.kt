package com.dilip.android.mobiquity_weather_app.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dilip.android.mobiquity_weather_app.R
import com.dilip.android.mobiquity_weather_app.db.entities.LocationEntity
import kotlinx.android.synthetic.main.locations_list_row.view.*

class LocationsAdapter(
    val context: Context,
    val onItemClickListener: ((LocationEntity) -> Unit)? = null,
    val onItemDeleteClickListener: ((LocationEntity) -> Unit)? = null
) : RecyclerView.Adapter<LocationsAdapter.MyViewHolder>() {

    private var list:  MutableList<LocationEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.locations_list_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun refresh(list:  MutableList<LocationEntity> = mutableListOf()){
        this.list = list
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(locationEntity: LocationEntity) {
            itemView.tvLocationName.text = locationEntity.locationName
            itemView.latLang.text = context.getString(R.string.latlang_text,String.format("%.4f",locationEntity.latitude),String.format("%.4f",locationEntity.longitude))
            itemView.locationCardView.setOnClickListener {
                onItemClickListener?.invoke(locationEntity)
            }
            itemView.ivDelete.setOnClickListener {
                onItemDeleteClickListener?.invoke(locationEntity)
            }
        }
    }

}
