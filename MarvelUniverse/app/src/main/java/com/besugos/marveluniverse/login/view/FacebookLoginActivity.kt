package com.besugos.marveluniverse.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.besugos.marveluniverse.MainActivity
import com.besugos.marveluniverse.R

class FacebookLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Register)
        setContentView(R.layout.activity_facebook_login)

        // set toolbar as support action bar
        val toolbar = this.findViewById<Toolbar>(R.id.facebookToolbar)
        setSupportActionBar(toolbar)

        val btnLoginFacebook = this.findViewById<Button>(R.id.btnFacebook)

        supportActionBar?.apply {
            title = "Login with Facebook"

            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        btnLoginFacebook.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
