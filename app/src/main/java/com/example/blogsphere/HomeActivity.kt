package com.example.blogsphere

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        val fabAddBlog =
            findViewById<FloatingActionButton>(R.id.fabAddBlog)

        fabAddBlog.setOnClickListener {

            startActivity(
                Intent(this, CreateBlogActivity::class.java)
            )

        }
    }
}