package com.example.maps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HotelAdapter(private val hotels: List<Hotel>) : RecyclerView.Adapter<HotelAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewHotelName: TextView = itemView.findViewById(R.id.textViewHotelName)
        val textViewVialityType: TextView = itemView.findViewById(R.id.textViewVialityType)
        val textViewVialityName: TextView = itemView.findViewById(R.id.textViewVialityName)
        val textViewSettlementType: TextView = itemView.findViewById(R.id.textViewSettlementType)
        val textViewSettlementName: TextView = itemView.findViewById(R.id.textViewSettlementName)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hotel, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotel = hotels[position]
        holder.textViewHotelName.text = hotel.hotelName
        holder.textViewVialityType.text = hotel.vialityType
        holder.textViewVialityName.text = hotel.vialityName
        holder.textViewSettlementType.text = hotel.settlementType
        holder.textViewSettlementName.text = hotel.settlementName
    }
    override fun getItemCount(): Int {
        return hotels.size
    }
}

