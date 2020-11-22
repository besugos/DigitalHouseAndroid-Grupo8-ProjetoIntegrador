package com.besugos.marveluniverse.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.besugos.marveluniverse.MainActivity
import com.besugos.marveluniverse.R

class GoogleLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Register)
        setContentView(R.layout.activity_google_login)

        // set toolbar as support action bar
        val toolbar = this.findViewById<Toolbar>(R.id.googleToolbar)
        setSupportActionBar(toolbar)

        val btnLoginGoogle = this.findViewById<Button>(R.id.btnGoogle)

        supportActionBar?.apply {
            title = "Login with Google"

            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        btnLoginGoogle.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
