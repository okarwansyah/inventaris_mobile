package com.okaarwansyah.aplikasi1

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.okaarwansyah.aplikasi1.adapter.SiswaAdapter
import com.okaarwansyah.aplikasi1.model.Siswa
import com.okaarwansyah.aplikasi1.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataSiswa : AppCompatActivity() {
    private lateinit var recyclerViewSiswa: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Menggunakan layout yang sesuai
        setContentView(R.layout.data_siswa)
        recyclerViewSiswa = findViewById(R.id.recyclerViewSiswa)
        recyclerViewSiswa.layoutManager = LinearLayoutManager(this)

        // Memanggil API untuk mendapatkan data siswa
        RetrofitClient.api.getSiswa().enqueue(object : Callback<List<Siswa>> {
            override fun onResponse(call: Call<List<Siswa>>, response: Response<List<Siswa>>) {
                if (response.isSuccessful) {
                    val allSiswa = response.body()

                    // Pastikan data siswa tidak kosong
                    if (!allSiswa.isNullOrEmpty()) {
                        val adapter = SiswaAdapter(allSiswa)
                        recyclerViewSiswa.adapter = adapter
                    } else {
                        Toast.makeText(this@DataSiswa, "Tidak ditemukan data", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Log.e("DataSiswa", "Gagal memuat data: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<Siswa>>, t: Throwable) {
                Log.e("DataSiswa", "Error: ${t.message}")
                Toast.makeText(
                    this@DataSiswa,
                    "Terjadi kesalahan saat memuat data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
