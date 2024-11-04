package org.myd.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.myd.instagramclone.databinding.MyPostRvDesignBinding
import org.myd.instagramclone.models.Post

class MyPostRVAdapter (var context: Context, private var postList: ArrayList<Post>) :
    RecyclerView.Adapter<MyPostRVAdapter.ViewHolder>() {

        inner class ViewHolder(var binding :MyPostRvDesignBinding) :
            RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = MyPostRvDesignBinding.inflate(LayoutInflater.from(context), parent, false)
            return ViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return postList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            Picasso.get().load(postList[position].postUrl).into(holder.binding.postImage)
        }
}