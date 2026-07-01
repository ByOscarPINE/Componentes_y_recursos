package com.example.componentesyrecursos

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.componentesyrecursos.databinding.ActivityMainBinding
import java.net.URI

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        enableEdgeToEdge()

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loadImage("https://example.com/image.jpg")
        binding.button1.setOnClickListener {
            Toast.makeText(this, "Button 1 clicked", Toast.LENGTH_SHORT).show()
        }

        binding.button2.setOnClickListener {
            binding.materialCard.visibility = View.GONE
        }

        val url: String = "https://example.com/image.jpg"

        Glide.with(this)
            .load("https://example.com/image.jpg")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.image1)

        binding.textLemail.onFocusChangeListener = View.OnFocusChangeListener { view, focused ->
            if (focused) {
                binding.textEemail.setBackgroundColor(Color.GREEN)
            }else {
                binding.textEemail.setBackgroundColor(Color.GRAY)
            }
        }

        binding.textEurl.addTextChangedListener(){
            var errorStr: String? = null
            val url = binding.textEurl.text.toString()
            when {
                url.isEmpty() -> {
                    errorStr = "Required"
                }

                URLUtil.isValidUrl(url) -> {
                    loadImage(url)
                }
                else -> {
                    errorStr = "Url invalida"
                }
            }
            loadImage(url)
        }

    }
        private fun loadImage (url: String) {
            Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.image1)

        }
}