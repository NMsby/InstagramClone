package org.myd.instagramclone.post

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import org.myd.instagramclone.HomeActivity
import org.myd.instagramclone.R
import org.myd.instagramclone.databinding.ActivityPostBinding
import org.myd.instagramclone.models.Post
import org.myd.instagramclone.utils.POST
import org.myd.instagramclone.utils.POST_FOLDER
import org.myd.instagramclone.utils.uploadImage

class PostActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    private var imageUrl: String? = null
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, POST_FOLDER) {
                url ->
                if (url != null) {
                    binding.selectImage.setImageURI(uri)
                    imageUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        binding.postButton.setOnClickListener {
            val post = Post(imageUrl!!, binding.caption.editText?.text.toString())

            Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post).addOnSuccessListener {
                    startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                    finish()
                }
            }
        }
    }
}