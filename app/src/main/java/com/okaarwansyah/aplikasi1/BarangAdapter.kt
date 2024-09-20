package com.okaarwansyah.aplikasi1

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.okaarwansyah.aplikasi1.model.Barang

class BarangAdapter (private val barangList: List<Barang>) : RecyclerView.Adapter<BarangAdapter.BarangViewHolder>(){
    class BarangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvKodeBarang: TextView = itemView.findViewById(R.id.tvKodeBarang)
        val tvNamaBarang: TextView = itemView.findViewById(R.id.tvNamaBarang)
        val tvKeteranganBarang: TextView = itemView.findViewById(R.id.tvKeteranganBarang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarangViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_barang, parent, false)
        return BarangViewHolder(view)
    }

    override fun onBindViewHolder(holder: BarangViewHolder, position: Int) {
        val barang = barangList[position]
        holder.tvKodeBarang.text = barang.id.toString()
        holder.tvNamaBarang.text = barang.nama
        holder.tvKeteranganBarang.text = barang.keterangan
    }

    override fun getItemCount(): Int {
        return barangList.size
    }
}