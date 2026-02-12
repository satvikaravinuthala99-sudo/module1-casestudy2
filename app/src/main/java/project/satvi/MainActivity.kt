package project.satvi

import android.os.Bundle
import android.text.util.Linkify
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView

    private val eiffelTowerImageUrl = "https://www.mystreal.com/_next/image/?url=%2Ftemple%2Farunachaleswarar-temple-tiruvannamali%2Fhero-Arunachalam-temple-conclusion.jpg&w=1920&q=75"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        imageView = findViewById(R.id.parisImageView)
        progressBar = findViewById(R.id.imageProgressBar)
        errorText = findViewById(R.id.imageErrorText)

        // Load the image from URL
        loadEiffelTowerImage()

        // Setup links
        setupLinks()
    }

    private fun loadEiffelTowerImage() {
        // Show loading state
        progressBar.visibility = View.VISIBLE
        errorText.visibility = View.GONE
        errorText.text = "Loading image..."

        Picasso.get()
            .load(eiffelTowerImageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    // Hide loading when successful
                    progressBar.visibility = View.GONE
                    errorText.visibility = View.GONE

                    Toast.makeText(
                        this@MainActivity,
                        "Image loaded successfully!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onError(e: Exception?) {
                    // Show error state
                    progressBar.visibility = View.GONE
                    errorText.visibility = View.VISIBLE
                    errorText.text = "Failed to load image. Tap to retry."

                    // Make error text clickable to retry
                    errorText.setOnClickListener {
                        loadEiffelTowerImage()
                    }

                    Toast.makeText(
                        this@MainActivity,
                        "Failed to load image. Check internet connection.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun setupLinks() {
        // Make TextViews with URLs clickable
        val officialWebsite = findViewById<TextView>(R.id.officialWebsite)
        val wikipediaLink = findViewById<TextView>(R.id.wikipediaLink)
        val tourismLink = findViewById<TextView>(R.id.tourismLink)

        // List of text views containing links
        val textViewsWithLinks = listOf(officialWebsite, wikipediaLink, tourismLink)

        textViewsWithLinks.forEach { textView ->
            // Make web URLs clickable
            Linkify.addLinks(textView, Linkify.WEB_URLS)

            // Add click feedback
            textView.setOnClickListener {
                // Visual feedback when link is clicked
                textView.alpha = 0.7f
                textView.postDelayed({ textView.alpha = 1.0f }, 200)
            }
        }
    }
}