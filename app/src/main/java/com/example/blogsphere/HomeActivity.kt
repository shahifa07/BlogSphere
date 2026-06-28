package com.example.blogsphere

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var blogList: ArrayList<Blog>
    private lateinit var adapter: BlogAdapter
    private lateinit var database: DatabaseReference
    private lateinit var fabAddBlog: FloatingActionButton
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize Views
        recyclerView = findViewById(R.id.recyclerBlogs)
        fabAddBlog = findViewById(R.id.fabAddBlog)
        btnLogout = findViewById(R.id.btnLogout)

        // RecyclerView Setup
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        blogList = ArrayList()
        adapter = BlogAdapter(blogList)
        recyclerView.adapter = adapter

        // Firebase Database Reference
        database = FirebaseDatabase.getInstance().getReference("Blogs")

        // Load Blogs
        loadBlogs()

        // Open Create Blog Screen
        fabAddBlog.setOnClickListener {
            startActivity(Intent(this, CreateBlogActivity::class.java))
        }

        // Logout
        btnLogout.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            Toast.makeText(
                this,
                "Logged out successfully",
                Toast.LENGTH_SHORT
            ).show()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish()
        }
    }

    private fun loadBlogs() {

        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                blogList.clear()

                for (blogSnapshot in snapshot.children) {

                    val blog = blogSnapshot.getValue(Blog::class.java)

                    if (blog != null) {
                        blogList.add(blog)
                    }
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(
                    this@HomeActivity,
                    "Failed to load blogs",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}