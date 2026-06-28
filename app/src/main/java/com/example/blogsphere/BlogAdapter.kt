package com.example.blogsphere

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BlogAdapter(
    private val blogList: ArrayList<Blog>
) : RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {

    class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvBlogTitle)
        val author: TextView = itemView.findViewById(R.id.tvBlogAuthor)
        val content: TextView = itemView.findViewById(R.id.tvBlogContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_blog, parent, false)
        return BlogViewHolder(view)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {

        val blog = blogList[position]

        holder.title.text = blog.title
        holder.author.text = "By ${blog.author}"
        holder.content.text = blog.content

        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, BlogDetailActivity::class.java)
            intent.putExtra("id", blog.id)
            intent.putExtra("title", blog.title)
            intent.putExtra("author", blog.author)
            intent.putExtra("content", blog.content)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return blogList.size
    }
}