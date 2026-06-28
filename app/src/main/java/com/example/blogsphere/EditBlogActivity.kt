package com.example.blogsphere

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class EditBlogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_blog)

        val etTitle = findViewById<EditText>(R.id.etEditTitle)
        val etContent = findViewById<EditText>(R.id.etEditContent)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)

        val blogId = intent.getStringExtra("id") ?: ""
        val title = intent.getStringExtra("title") ?: ""
        val content = intent.getStringExtra("content") ?: ""
        val author = intent.getStringExtra("author") ?: ""

        etTitle.setText(title)
        etContent.setText(content)

        btnUpdate.setOnClickListener {

            val updatedTitle = etTitle.text.toString().trim()
            val updatedContent = etContent.text.toString().trim()

            if (updatedTitle.isEmpty() || updatedContent.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedBlog = Blog(
                id = blogId,
                title = updatedTitle,
                content = updatedContent,
                author = author,
                timestamp = System.currentTimeMillis()
            )

            FirebaseDatabase.getInstance()
                .getReference("Blogs")
                .child(blogId)
                .setValue(updatedBlog)
                .addOnSuccessListener {
                    Toast.makeText(this, "Blog Updated", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
                }
        }
    }
}