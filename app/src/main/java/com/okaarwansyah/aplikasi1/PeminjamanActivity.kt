package com.okaarwansyah.aplikasi1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.okaarwansyah.aplikasi1.model.Barang
import com.okaarwansyah.aplikasi1.model.Siswa
import com.okaarwansyah.aplikasi1.network.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PeminjamanActivity : AppCompatActivity() {
    private lateinit var spinnerSiswa: Spinner
    private lateinit var spinnerBarang: Spinner
    private lateinit var buttonSubmit: Button
    private lateinit var siswaList: List<Siswa>
    private lateinit var barangList: List<Barang>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.peminjaman)

        spinnerSiswa = findViewById(R.id.spinnerSiswa)
        spinnerBarang = findViewById(R.id.spinnerBarang)
        buttonSubmit = findViewById(R.id.btnSubmit) // Hubungkan tombol submit

        // Ambil data siswa dan barang dari API
        loadSiswa()
        loadBarang()

        // Set listener pada tombol submit
        buttonSubmit.setOnClickListener {
            val selectedSiswaIndex = spinnerSiswa.selectedItemPosition
            val selectedBarangIndex = spinnerBarang.selectedItemPosition

            // Pastikan pilihan valid
            if (selectedSiswaIndex >= 0 && selectedBarangIndex >= 0) {
                val selectedSiswa = siswaList[selectedSiswaIndex]
                val selectedBarang = barangList[selectedBarangIndex]

                // Kirim data ke server
                submitPeminjaman(selectedSiswa.nis, selectedBarang.id)
            } else {
                Toast.makeText(this@PeminjamanActivity, "Pilih siswa dan barang terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadSiswa() {
        RetrofitClient.api.getSiswa().enqueue(object : Callback<List<Siswa>> {
            override fun onResponse(call: Call<List<Siswa>>, response: Response<List<Siswa>>) {
                if (response.isSuccessful) {
                    siswaList = response.body()!!
                    val siswaNames = siswaList.map { it.nama }
                    val adapter = ArrayAdapter(this@PeminjamanActivity, android.R.layout.simple_spinner_item, siswaNames)
                    spinnerSiswa.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Siswa>>, t: Throwable) {
                Toast.makeText(this@PeminjamanActivity, "Gagal mengambil data siswa", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadBarang() {
        RetrofitClient.api.getBarang().enqueue(object : Callback<List<Barang>> {
            override fun onResponse(call: Call<List<Barang>>, response: Response<List<Barang>>) {
                if (response.isSuccessful) {
                    barangList = response.body()!!
                    val barangNames = barangList.map { it.nama }
                    val adapter = ArrayAdapter(this@PeminjamanActivity, android.R.layout.simple_spinner_item, barangNames)
                    spinnerBarang.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<Barang>>, t: Throwable) {
                Toast.makeText(this@PeminjamanActivity, "Gagal mengambil data barang", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun submitPeminjaman(nis: String, idBarang: Int) {
        val apiService = RetrofitClient.api
        val tanggalPinjam = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) // Tanggal sekarang

        apiService.submitPeminjaman(nis, idBarang, tanggalPinjam).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@PeminjamanActivity, "Data berhasil disubmit", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@PeminjamanActivity, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@PeminjamanActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
