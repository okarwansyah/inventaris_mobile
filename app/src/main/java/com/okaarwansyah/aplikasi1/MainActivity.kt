package com.okaarwansyah.aplikasi1
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Menghubungkan ke file layout XML

        val buttonSiswa = findViewById<Button>(R.id.buttonSiswa) // variabel pemanggilan Button DataSiswa
        val buttonBarang = findViewById<Button>(R.id.buttonBarang)
        val buttonPeminjaman = findViewById<Button>(R.id.buttonPeminjaman)

        buttonSiswa.setOnClickListener {
            val intent = Intent(this, DataSiswa::class.java) // perintah pindah page
            startActivity(intent)
        }

        buttonBarang.setOnClickListener {
            val intent = Intent(this, DataBarang::class.java)
            startActivity(intent)
        }

        buttonPeminjaman.setOnClickListener {
            val intent = Intent(this, DataPeminjaman::class.java)
            startActivity(intent)
        }
    }
}

