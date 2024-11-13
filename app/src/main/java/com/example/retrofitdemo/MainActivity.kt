package com.example.retrofitdemo

import android.os.Build
import android.os.Bundle
import android.os.ext.SdkExtensions
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitdemo.adapter.TodoAdapter
import com.example.retrofitdemo.databinding.ActivityMainBinding
import com.example.retrofitdemo.model.Todo
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

const val TAG = "Main Activity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter: TodoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecycler()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.progressBar.isVisible = true

//                RetrofitInstance.api.getData().enqueue(object: Callback<List<Todo>>{
//                    override fun onResponse(p0: Call<List<Todo>>, p1: Response<List<Todo>>) {
//
//                    }
//
//                    override fun onFailure(p0: Call<List<Todo>>, p1: Throwable) {
//
//                    }
//                })


                val response =
                    try {
                        RetrofitInstance.api.getData()
                    } catch (e: IOException) {
                        Log.e(TAG, "IO Exception")
                        binding.progressBar.isVisible = false
                        return@repeatOnLifecycle
                    } catch (e: HttpException) {
                        Log.e(TAG, "HTTP Exception")
                        binding.progressBar.isVisible = false
                        return@repeatOnLifecycle
                    }

                val body = response.body()
                if (response.isSuccessful && body != null){
                    todoAdapter.todos = body
                }else{
                    Log.e(TAG, "Response Not successful")
                }

                
                binding.progressBar.isVisible = false
            }
        }


    }

    private fun setUpRecycler() = binding.rvTodos.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)

    }
}
