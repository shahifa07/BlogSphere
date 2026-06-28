package com.example.blogsphere

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CreateBlogActivity : AppCompatActivity() {

    private lateinit var etBlogTitle: EditText
    private lateinit var etBlogContent: EditText
    private lateinit var btnPublish: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_blog)

        etBlogTitle = findViewById(R.id.etBlogTitle)
        etBlogContent = findViewById(R.id.etBlogContent)
        btnPublish = findViewById(R.id.btnPublish)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        btnPublish.setOnClickListener {

            val title = etBlogTitle.text.toString().trim()
            val content = etBlogContent.text.toString().trim()

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val blogId = database.reference.child("Blogs").push().key!!

            val author = auth.currentUser?.email ?: "Anonymous"

            val blog = Blog(
                id = blogId,
                title = title,
                content = content,
                author = author,
                timestamp = System.currentTimeMillis()
            )

            database.reference.child("Blogs").child(blogId)
                .setValue(blog)
                .addOnSuccessListener {

                    Toast.makeText(
                        this,
                        "Blog Published Successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()

                }
                .addOnFailureListener {

                    Toast.makeText(
                        this,
                        "Failed: ${it.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }
}