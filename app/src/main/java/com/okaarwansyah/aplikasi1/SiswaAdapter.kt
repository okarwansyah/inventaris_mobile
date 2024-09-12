package com.okaarwansyah.aplikasi1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.okaarwansyah.aplikasi1.R
import com.okaarwansyah.aplikasi1.model.Siswa

class SiswaAdapter(private val siswaList: List<Siswa>) : RecyclerView.Adapter<SiswaAdapter.SiswaViewHolder>() {

    class SiswaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNis: TextView = itemView.findViewById(R.id.tvNis)
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvJurusan: TextView = itemView.findViewById(R.id.tvKelas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiswaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_siswa, parent, false)
        return SiswaViewHolder(view)
    }

    override fun onBindViewHolder(holder: SiswaViewHolder, position: Int) {
        val siswa = siswaList[position]
        holder.tvNis.text = siswa.nis
        holder.tvNama.text = siswa.nama
        holder.tvJurusan.text = siswa.kelas
    }

    override fun getItemCount(): Int {
        return siswaList.size
    }
}
