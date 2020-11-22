package com.besugos.marveluniverse.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.besugos.marveluniverse.MainActivity
import com.besugos.marveluniverse.R


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Register)
        setContentView(R.layout.activity_register)

        // set toolbar as support action bar
        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val termsLink = this.findViewById<TextView>(R.id.txtTerms)
        val btnSignUp = this.findViewById<Button>(R.id.btnCreateAccountRegister)

        supportActionBar?.apply {
            title = "Sign Up"

            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        btnSignUp.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        termsLink.setOnClickListener() {
            val intent = Intent(this, TermsActivity::class.java)
            startActivity(intent)
        }
    }
}