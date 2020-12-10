package com.besugos.marveluniverse.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.besugos.marveluniverse.MainActivity
import com.besugos.marveluniverse.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class RegisterActivity : AppCompatActivity() {

    private lateinit var tfEmail: TextInputLayout
    private lateinit var tfName: TextInputLayout
    private lateinit var tfRptPass: TextInputLayout
    private lateinit var tfPass: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etName: TextInputEditText
    private lateinit var etRptPass: TextInputEditText
    private lateinit var etPass: TextInputEditText
    private lateinit var chk: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Register)
        setContentView(R.layout.activity_register)

        // set toolbar as support action bar
        val toolbar = this.findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val termsLink = this.findViewById<TextView>(R.id.txtTerms)
        val btnSignUp = this.findViewById<Button>(R.id.btnCreateAccountRegister)

        tfEmail = findViewById(R.id.tfEmailRegister)
        tfPass = findViewById(R.id.tfPassRegister)
        tfName = findViewById(R.id.tfNameRegister)
        tfRptPass = findViewById(R.id.tfRptPassRegister)
        etEmail = findViewById(R.id.etEmailRegister)
        etPass = findViewById(R.id.etPassRegister)
        etName = findViewById(R.id.etNameRegister)
        etRptPass = findViewById(R.id.etRptPassRegister)
        chk = findViewById(R.id.cbTerms)

        supportActionBar?.apply {
            title = "Sign Up"
            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        btnSignUp.setOnClickListener() {
            if (validaCampos()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        termsLink.setOnClickListener() {
            val intent = Intent(this, TermsActivity::class.java)
            startActivity(intent)
        }

        etEmail.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tfEmail.error = ""
            }
        })

        etPass.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tfPass.error = ""
            }
        })

        etName.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tfName.error = ""
            }
        })

        etRptPass.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tfRptPass.error = ""
            }
        })

        etEmail.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tfEmail.error = ""
            }
        })

        chk.setOnCheckedChangeListener { _, _ ->
            btnSignUp.isEnabled = chk.isChecked
        }
    }

    private fun validaCampos(): Boolean {
        var response = true

        if (etEmail.text.isNullOrBlank()) {
            tfEmail.error = "Please type your e-mail"
            response = false
        }

        if (etName.text.isNullOrBlank()) {
            tfName.error = "Please type your Name"
            response = false
        }

        if (etPass.text.isNullOrBlank()) {
            tfPass.error = "Password Required"
            response = false
        } else if (etPass.text!!.length < 8){
            tfPass.error = "Password must be at least 8 characters long"
            response = false
        }

        if (etRptPass.text.isNullOrBlank()) {
            tfRptPass.error = "Please repeat your password"
            response = false
        } else if (etPass.text.toString() != etRptPass.text.toString()){
            tfPass.error = "Passwords do not match"
            response = false
        }

        if (!(chk.isChecked())) {
            chk.error = "Please check to agree with the Terms"
            response = false
        }

        return response
    }
}