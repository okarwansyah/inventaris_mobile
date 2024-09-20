package com.okaarwansyah.aplikasi1.model

data class Peminjaman(
    val nis: String,
    val nama: String,
    val buku: String,
    val id_barang: String,
    val status: String,
    val tanggal_peminjaman: String,
    val tanggal_pengembalian: String
)
