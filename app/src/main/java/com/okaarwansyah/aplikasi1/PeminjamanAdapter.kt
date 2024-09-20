package com.okaarwansyah.aplikasi1

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.okaarwansyah.aplikasi1.model.Peminjaman
import android.widget.Toast
import com.okaarwansyah.aplikasi1.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
// form encoding
import retrofit2.http.Field


class PeminjamanAdapter (private val peminjamanList: List<Peminjaman>) : RecyclerView.Adapter<PeminjamanAdapter.PeminjamanViewHolder>(){
    class PeminjamanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // fields : nis, nama, id_barang, status, tanggal_peminjaman, tanggal_pengembalian
        val tvNis: TextView = itemView.findViewById(R.id.tvNis)
        val tvNamaBarang: TextView = itemView.findViewById(R.id.tvNamaBarang)
        val tvStatusBarang: TextView = itemView.findViewById(R.id.tvStatusBarang)
        val tvTanggalPeminjaman: TextView = itemView.findViewById(R.id.tvTanggalPeminjaman)
        val tvTanggalPengembalian: TextView = itemView.findViewById(R.id.tvTanggalPengembalian)
        // save button kembalikan to a variable
        val btnKembalikan: Button = itemView.findViewById(R.id.btnKembalikan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeminjamanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_peminjaman, parent, false)
        return PeminjamanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PeminjamanViewHolder, position: Int) {
        val peminjaman = peminjamanList[position]
        holder.tvNis.text = peminjaman.nis
        holder.tvNamaBarang.text = peminjaman.nama
        holder.tvStatusBarang.text = peminjaman.status
        holder.tvTanggalPeminjaman.text = peminjaman.tanggal_peminjaman
        if (peminjaman.tanggal_pengembalian == null) {
            holder.tvTanggalPengembalian.text = "Belum Dikembalikan"
        } else {
            holder.tvTanggalPengembalian.text = peminjaman.tanggal_pengembalian
        }
        if (peminjaman.status == "dipinjam") {
            holder.btnKembalikan.visibility = View.VISIBLE
        } else {
            holder.btnKembalikan.visibility = View.GONE
        }

        holder.btnKembalikan.setOnClickListener {
            // call api to return the item kembalikanBarang(nis, id_barang)
            holder.btnKembalikan.setOnClickListener {
                val nis = peminjaman.nis
                val idBarang = peminjaman.id_barang
                // RetrofitClient.api.kembalikanBarang(nis, idBarang).enqueue(object : Callback<Void> {
                //     override fun onResponse(call: Call<Void>, response: Response<Void>) {
                //         if (response.isSuccessful) {
                //             Toast.makeText(holder.itemView.context, "Barang berhasil dikembalikan", Toast.LENGTH_SHORT).show()
                //         } else {
                //             Toast.makeText(holder.itemView.context, "Gagal mengembalikan barang", Toast.LENGTH_SHORT).show()
                //         }
                //     }

                //     override fun onFailure(call: Call<Void>, t: Throwable) {
                //         Toast.makeText(holder.itemView.context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                //     }
                // })
                // use form encoding to send the data
                RetrofitClient.api.kembalikanBarang(nis, idBarang).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            Toast.makeText(holder.itemView.context, "Barang berhasil dikembalikan", Toast.LENGTH_SHORT).show()
                            // restart DataPeminjaman activity to refresh the data
                            val intent = Intent(holder.itemView.context, DataPeminjaman::class.java)
                            holder.itemView.context.startActivity(intent)
                        } else {
                            Toast.makeText(holder.itemView.context, "Gagal mengembalikan barang", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(holder.itemView.context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                })

            }
        }
    }

    override fun getItemCount(): Int {
        return peminjamanList.size
    }
}