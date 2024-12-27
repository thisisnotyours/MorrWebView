package com.thisisnotyours.morrwebview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView)
        lottieAnimationView.setAnimation("morr_splash_screen.json")
        lottieAnimationView.setImageAssetsFolder("images/")
//        lottieAnimationView.setImageAssetsFolder("assets/")


        lottieAnimationView.playAnimation()

        // 일정 시간 후에 메인 액티비티로 이동
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4500) // 3초 동안 스플래시 화면 표시 (원하는 시간으로 변경 가능)
    }
}