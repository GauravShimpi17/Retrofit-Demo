package com.example.retrofitdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.retrofitdemo.databinding.ItemTodoBinding
import com.example.retrofitdemo.model.Todo

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(val binding: ItemTodoBinding) : ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<Todo>(){
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }
        private val differ = AsyncListDiffer(this, diffCallBack)
        var todos : List<Todo>
            get() = differ.currentList
            set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.TodoViewHolder {
        return TodoViewHolder(ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: TodoAdapter.TodoViewHolder, position: Int) {

        holder.binding.apply {
            val todo = todos[position]
            tvTitle.text = todo.title
            cbCheck.isChecked = todo.completed
        }

    }

    override fun getItemCount(): Int = todos.size
}