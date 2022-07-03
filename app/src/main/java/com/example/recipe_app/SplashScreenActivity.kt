package com.example.recipe_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val button: Button = findViewById(R.id.splash_btn)
        button.setOnClickListener(View.OnClickListener {
            var i = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        })

    }
}