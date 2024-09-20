package com.okaarwansyah.aplikasi1

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.okaarwansyah.aplikasi1.model.Barang
import com.okaarwansyah.aplikasi1.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeminjamanBarang : AppCompatActivity() {
    private lateinit var spinnerItem: Spinner
    private lateinit var submitButton: Button
    private var itemList: List<Barang> = listOf()
    private var selectedItemId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.peminjaman)

        spinnerItem = findViewById(R.id.spinnerBarang)
        submitButton = findViewById(R.id.submitButton)

        //ambil data barang
        RetrofitClient.api.getBarang().enqueue(object : Callback<List<Barang>> {
            override fun onResponse(call: Call<List<Barang>>, response: Response<List<Barang>>) {
                // Dalam onResponse
                if (response.isSuccessful) {
                    itemList = response.body()!! // Memperbarui itemList
                    val adapter = ArrayAdapter(
                        this@PeminjamanBarang,
                        android.R.layout.simple_spinner_item,
                        itemList.map { it.nama } // Misalnya, nama_barang adalah field yang ingin ditampilkan
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerItem.adapter = adapter
                }

            }

            override fun onFailure(call: Call<List<Barang>>, t: Throwable) {
                Toast.makeText(this@PeminjamanBarang, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        })
        spinnerItem.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItemId = itemList[position].id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Tidak ada item yang dipilih
            }
        }

        submitButton.setOnClickListener {
            val valueToInsert = "value"
            RetrofitClient.api.insertToTablePinjaman(selectedItemId, valueToInsert).enqueue(object : Callback<Response> {
                override fun onResponse(call: Call<Response>, response: Response<Response>{
                    if (response.isSuccessful){

                        Toast.makeText(this@PeminjamanBarang, "Data Disimpan", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(this@PeminjamanBarang, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}