package com.example.postwithapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.postwithapi.R

class PostAdapter(
    private var posts: MutableList<PostData>,
    private var myInterface: MyInterface = object : MyInterface {
        override fun onClick(postData: PostData) {}
    }
) :
    RecyclerView.Adapter<PostAdapter.MyHolder>() {

    class MyHolder(private var itemView: View) : RecyclerView.ViewHolder(itemView) {

        var img: ImageView = itemView.findViewById(R.id.postImg)
        var title: TextView = itemView.findViewById(R.id.postName)
        var content: TextView = itemView.findViewById(R.id.postContent)
        var date: TextView = itemView.findViewById(R.id.publishedDate)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return MyHolder(view)

    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val postList = posts[position]
        holder.title.text = postList.title
        holder.content.text = postList.content
        holder.img.load(postList.image)

        holder.img.setOnClickListener {

        }
    }

    interface MyInterface {

        fun onClick(postData: PostData)

    }

}