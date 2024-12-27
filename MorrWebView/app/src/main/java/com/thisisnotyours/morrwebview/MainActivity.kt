package com.thisisnotyours.morrwebview

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var myWebView: WebView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        // Initialize WebView and ProgressBar
        myWebView = findViewById(R.id.webview)
        progressBar = findViewById(R.id.progressive_bar)

        // WebView settings
        with(myWebView.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"

            // Additional WebView settings
            useWideViewPort = true
            loadWithOverviewMode = true
        }

        // WebView debugging
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }

        // Cookie settings
        with(CookieManager.getInstance()) {
            setAcceptCookie(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setAcceptThirdPartyCookies(myWebView, true)
            }
        }

        // Set WebViewClient for handling page load and URL loading behaviors
        myWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url.toString()
                val schemes = listOf("whatsapp://", "phonepe://", "googlepay://", "tez://upi", "paytmmp://", "upi://")
                val names = listOf("WhatsApp", "PhonePe", "Google Pay", "Paytm", "BHIM")

                for (i in schemes.indices) {
                    if (url.startsWith(schemes[i])) {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(url)
                            startActivity(intent)
                            return true
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(applicationContext, "${names[i]} not installed.", Toast.LENGTH_SHORT).show()
                            return false
                        }
                    }
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        // Load the website
        myWebView.loadUrl("https://morrnow.com")
    }

    override fun onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}



