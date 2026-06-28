package com.example.blogsphere

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class BlogDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog_detail)

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val tvAuthor = findViewById<TextView>(R.id.tvAuthor)
        val tvContent = findViewById<TextView>(R.id.tvContent)

        val btnEdit = findViewById<Button>(R.id.btnEdit)
        val btnDelete = findViewById<Button>(R.id.btnDelete)

        val blogId = intent.getStringExtra("id") ?: ""

        tvTitle.text = intent.getStringExtra("title")
        tvAuthor.text = "By ${intent.getStringExtra("author")}"
        tvContent.text = intent.getStringExtra("content")

        btnDelete.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Delete Blog")
                .setMessage("Are you sure you want to delete this blog?")
                .setPositiveButton("Yes") { _, _ ->

                    FirebaseDatabase.getInstance()
                        .getReference("Blogs")
                        .child(blogId)
                        .removeValue()
                        .addOnSuccessListener {

                            Toast.makeText(
                                this,
                                "Blog deleted successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            finish()
                        }
                        .addOnFailureListener {

                            Toast.makeText(
                                this,
                                "Delete failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
                .setNegativeButton("No", null)
                .show()
        }

        btnEdit.setOnClickListener {

            val intent = Intent(this, EditBlogActivity::class.java)

            intent.putExtra("id", blogId)
            intent.putExtra("title", intent.getStringExtra("title"))
            intent.putExtra("content", intent.getStringExtra("content"))
            intent.putExtra("author", intent.getStringExtra("author"))

            startActivity(intent)
        }
    }
}