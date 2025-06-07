package com.example.itix

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.itix.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var userId: Int = -1
    private var username: String = ""
    private var email: String = ""
    private lateinit var sliderAdapter: SliderAdapter
    private val sliderImages = listOf(
        R.drawable.posterlilo,
        R.drawable.posterlilo,
        R.drawable.posterlilo
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            binding = ActivityHomeBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // Get user data from Intent
            userId = intent.getIntExtra("user_id", -1)
            username = intent.getStringExtra("username") ?: ""
            email = intent.getStringExtra("email") ?: ""

            if (userId == -1) {
                Toast.makeText(this, "Error: User data not found", Toast.LENGTH_SHORT).show()
                finish()
                return
            }

            // Set up image slider
            setupImageSlider()

            // Set up navigation buttons
            binding.btnProfile.setOnClickListener {
                try {
                    val intent = Intent(this, ProfileActivity::class.java).apply {
                        putExtra("user_id", userId)
                        putExtra("username", username)
                        putExtra("email", email)
                    }
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e("HomeActivity", "Error navigating to Profile: ${e.message}")
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            binding.btnMovieList.setOnClickListener {
                try {
                    val intent = Intent(this, MovieListActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e("HomeActivity", "Error navigating to MovieList: ${e.message}")
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            binding.btnFavourite.setOnClickListener {
                try {
                    val intent = Intent(this, FavouriteActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e("HomeActivity", "Error navigating to Favourite: ${e.message}")
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            // Set up movie recommendations click
            binding.layoutRecommendations.setOnClickListener {
                try {
                    val intent = Intent(this, MovieListActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e("HomeActivity", "Error navigating to MovieList from recommendations: ${e.message}")
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Log.e("HomeActivity", "Error in onCreate: ${e.message}")
            Toast.makeText(this, "Error initializing app: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun setupImageSlider() {
        sliderAdapter = SliderAdapter(sliderImages)
        binding.viewPager.adapter = sliderAdapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // Set up indicator dots
        setupIndicatorDots()
        
        // Update indicator dots when page changes
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateIndicatorDots(position)
            }
        })
    }

    private fun setupIndicatorDots() {
        val dots = arrayOfNulls<ImageView>(sliderImages.size)
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(8, 0, 8, 0)

        for (i in sliderImages.indices) {
            dots[i] = ImageView(this)
            dots[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.indicator_dot_inactive
                )
            )
            dots[i]?.layoutParams = params
            binding.indicatorLayout.addView(dots[i])
        }
        updateIndicatorDots(0)
    }

    private fun updateIndicatorDots(position: Int) {
        val childCount = binding.indicatorLayout.childCount
        for (i in 0 until childCount) {
            val dot = binding.indicatorLayout.getChildAt(i) as ImageView
            dot.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    if (i == position) R.drawable.indicator_dot_active else R.drawable.indicator_dot_inactive
                )
            )
        }
    }
}
