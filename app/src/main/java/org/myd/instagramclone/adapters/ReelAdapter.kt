package org.myd.instagramclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.myd.instagramclone.R
import org.myd.instagramclone.databinding.ReelDgBinding
import org.myd.instagramclone.models.Reel

class ReelAdapter(var context: Context, var reelList:ArrayList<Reel>) :  RecyclerView.Adapter<ReelAdapter.ViewHolder>() {
    inner  class ViewHolder(var binding: ReelDgBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ReelDgBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(reelList[position].profileLink).placeholder(R.drawable.user).into(holder.binding.profileImage)
        holder.binding.caption.text = reelList[position].caption
        holder.binding.videoView.setVideoPath(reelList[position].reelUrl)
        holder.binding.videoView.setOnPreparedListener {
            holder.binding.videoView.start()
        }
    }
}