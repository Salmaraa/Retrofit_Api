package com.example.retrofit.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit.MovieAdapter
import com.example.retrofit.R
import com.example.retrofit.RClient
import com.example.retrofit.databinding.FragmentDataBinding
import com.example.retrofit.modeldata.MovieData
import com.example.retrofit.modeldata.SearchData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DataFragment : Fragment() {
    private var _binding: FragmentDataBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val list = ArrayList<MovieData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDataBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        binding.rvData.setHasFixedSize(true)
        binding.rvData.layoutManager = LinearLayoutManager(context)

        val bundle = arguments
        val s = bundle?.getString("carimovie")
        val apikey = "3c720af1"

        RClient.instances.getMovies(s,apikey).enqueue(object : Callback<SearchData>{
            override fun onResponse(call: Call<SearchData>, response: Response<SearchData>) {
                val responseCode = response.code()

                //Ketika judul film tidak ditemukan
                val cekRes = response.body()?.res

                if(cekRes == "True"){
                    response.body()?.let{ list.addAll(it.data)}
                    val adapter = MovieAdapter(list, requireContext())
                    binding.rvData.adapter = adapter
                }else{
                    Toast.makeText(context, "Movie not found",Toast.LENGTH_LONG).show()
                }

                response.body()?.let { list.addAll(it.data) }
                val adapter = MovieAdapter(list, requireContext())
                binding.rvData.adapter = adapter
            }

            override fun onFailure(call: Call<SearchData>, t: Throwable) {
                Log.e(TAG, "Error: ${t.message}")
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}