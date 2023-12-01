package com.example.retrofit

import android.content.Context
import android.content.Intent
import android.graphics.Movie
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofit.databinding.ListDatamovieBinding
import com.example.retrofit.modeldata.MovieData

class MovieAdapter(
    private val listMovie:ArrayList<MovieData>,
    private val context: Context
): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    inner class  MovieViewHolder(itemView:ListDatamovieBinding):RecyclerView.ViewHolder(itemView.root){
        private val binding = itemView
        fun bind(movieData: MovieData){
            with(binding){
                Glide.with(itemView).load(movieData.gambar).into(imgPoster)
                tvTittle.text = movieData.Title
                tvYear.text = movieData.Year

                cvIdmovie.setOnClickListener{
                    var i = Intent(context, DetailMovieActivity::class.java).apply {
                        putExtra("imdbid",movieData.idmovie)
                    }
                    context.startActivity(i)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(ListDatamovieBinding.inflate(LayoutInflater.from(parent.context),
            parent,false
            ))
    }

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }
}