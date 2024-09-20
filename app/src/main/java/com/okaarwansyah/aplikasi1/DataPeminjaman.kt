package com.okaarwansyah.aplikasi1

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.okaarwansyah.aplikasi1.PeminjamanAdapter
import com.okaarwansyah.aplikasi1.model.Peminjaman
import com.okaarwansyah.aplikasi1.model.Siswa
import com.okaarwansyah.aplikasi1.network.RetrofitClient
import retrofit2.Call
import android.content.Intent
import android.widget.Button
import retrofit2.Callback
import retrofit2.Response


class DataPeminjaman : AppCompatActivity() {
    private lateinit var recyclerViewPeminjaman : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_peminjaman)
        recyclerViewPeminjaman = findViewById(R.id.recyclerViewPeminjaman)
        recyclerViewPeminjaman.layoutManager = LinearLayoutManager(this)

        val buttomTambahPeminjaman = findViewById<Button>(R.id.btnTambahPeminjaman)
        buttomTambahPeminjaman.setOnClickListener {
            val intent = Intent(this, PeminjamanActivity::class.java)
            startActivity(intent)
        }

        RetrofitClient.api.getPeminjaman().enqueue(object : Callback<List<Peminjaman>> {
            override fun onResponse(call: Call<List<Peminjaman>>, response: Response<List<Peminjaman>>) {
                if(response.isSuccessful){
                    val allPeminjaman = response.body()

                    if (!allPeminjaman.isNullOrEmpty()){
                        val adapter = PeminjamanAdapter(allPeminjaman)
                        recyclerViewPeminjaman.adapter = adapter
                    } else {
                        Toast.makeText(this@DataPeminjaman, "Tidak Ada Data", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Log.e("Data Peminjaman", "Gagal memuat data: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<Peminjaman>>, t: Throwable) {
                Log.e("Data Peminjaman", "Error: ${t.message}")
                Toast.makeText(this@DataPeminjaman, "Terjadi Kesalahan", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}