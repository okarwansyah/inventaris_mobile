package com.okaarwansyah.aplikasi1

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.okaarwansyah.aplikasi1.model.Barang
import com.okaarwansyah.aplikasi1.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataBarang : AppCompatActivity() {
    private lateinit var recyclerViewBarang : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_barang)
        recyclerViewBarang = findViewById(R.id.recyclerViewBarang)
        recyclerViewBarang.layoutManager = LinearLayoutManager(this)

        RetrofitClient.api.getBarang().enqueue(object : Callback<List<Barang>> {
            override fun onResponse(call: Call<List<Barang>>, response: Response<List<Barang>>) {
                if(response.isSuccessful){
                    val allBarang = response.body()

                    if (!allBarang.isNullOrEmpty()){
                        val adapter = BarangAdapter(allBarang)
                        recyclerViewBarang.adapter = adapter
                    } else {
                        Toast.makeText(this@DataBarang, "Tidak Ada Data", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Log.e("Data Barang", "Gagal memuat data: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<Barang>>, t: Throwable) {
                Log.e("Data Barang", "Error: ${t.message}")
                Toast.makeText(this@DataBarang, "Terjadi Kesalahan", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}