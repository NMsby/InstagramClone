package org.myd.instagramclone.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import org.myd.instagramclone.R
import org.myd.instagramclone.databinding.PostRvBinding
import org.myd.instagramclone.models.Post
import org.myd.instagramclone.models.User
import org.myd.instagramclone.utils.USER_NODE


class PostAdapter(var context : Context, private var postList : ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.MyHolder>() {
    inner class MyHolder(var binding : PostRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = PostRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            Firebase.firestore.collection(USER_NODE).document(postList[position].uid).get()
                .addOnSuccessListener {
                    val user = it.toObject<User>()
                    Glide.with(context).load(user!!.image).placeholder(R.drawable.user)
                        .into(holder.binding.profileImage)
                    holder.binding.name.text = user.name
                }
        } catch (_:Exception) {

        }
        Glide.with(context).load(postList[position].postUrl).placeholder(R.drawable.loading).into(holder.binding.postImage)
        try {
            val text = TimeAgo.using(postList[position].time.toLong())

            holder.binding.time.text = text

        } catch (e:Exception) {
            holder.binding.time.text=""
        }

        holder.binding.share.setOnClickListener {
            var i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_TEXT, postList[position].postUrl)
            context.startActivity(i)
        }

        holder.binding.caption.text = postList[position].caption
        holder.binding.like.setOnClickListener {
            holder.binding.like.setImageResource(R.drawable.red_like)
        }
    }
}