package com.example.retrofit

import android.graphics.Movie
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.retrofit.databinding.ActivityDetailMovieBinding
import com.example.retrofit.modeldata.MovieDetailData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailMovieBinding

    var b:Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        b = intent.extras
        val i = b?.getString("imdbid")
        val apikey = "3c720af1"

        RClient.instances.getDetailMovie(i, apikey).enqueue(object : Callback<MovieDetailData>{
            override fun onResponse(
                call: Call<MovieDetailData>,
                response: Response<MovieDetailData>
            ) {
                //Buat manggil text
                binding.tvTahun.text = response.body()?.Year
                binding.tvJudulmovie.text = response.body()?.Title
                binding.tvTglrilis.text = response.body()?.rilis

                //Buat menampilkan gambar
                Glide.with(this@DetailMovieActivity).load(response.body()?.gambar).into(binding.imgGambarposter)

            }

            override fun onFailure(call: Call<MovieDetailData>, t: Throwable) {

            }

        })

    }
}