package com.okaarwansyah.aplikasi1.network

import com.okaarwansyah.aplikasi1.model.Barang
import com.okaarwansyah.aplikasi1.model.Siswa
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @GET("https://inventaris.seaware.tech//siswa/") //endpoint API (sesuaikan dengan URL)
    fun getSiswa(): Call<List<Siswa>> // Menggunakan model data 'Siswa'

    @GET("https://inventaris.seaware.tech//barang/")
    fun getBarang(): Call<List<Barang>>

    @FormUrlEncoded
    @POST("insertToTablePinjaman")
    fun insertToTablePinjaman(
        @Field("id") itemId: Int,
        @Field("value") value: String
    )
}