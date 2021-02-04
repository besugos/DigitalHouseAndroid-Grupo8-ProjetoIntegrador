package com.besugos.marveluniverse.login.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.besugos.marveluniverse.MainActivity
import com.besugos.marveluniverse.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.*


class LoginActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private lateinit var callbackManager: CallbackManager

    private lateinit var tfEmail: TextInputLayout
    private lateinit var tfPass: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPass: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Register)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        callbackManager = CallbackManager.Factory.create()

        auth = FirebaseAuth.getInstance()

        val btnLogin = this.findViewById<Button>(R.id.btnLoginLogin)
        val btnRegister = this.findViewById<Button>(R.id.btnCreateAccount)
        val btnGoogle = this.findViewById<Button>(R.id.btnGoogleLogin)
        val btnFacebook = this.findViewById<Button>(R.id.btnFacebookLogin)
        val toolbar = this.findViewById<androidx.appcompat.widget.Toolbar>(R.id.loginToolbar)

        tfEmail = findViewById(R.id.tfEmailLogin)
        tfPass = findViewById(R.id.tfPassLogin)
        etEmail = findViewById(R.id.etEmailLogin)
        etPass = findViewById(R.id.etPassLogin)


        setSupportActionBar(toolbar).apply {
            title = "Marvel Universe"
        }

        btnLogin.setOnClickListener() {

            if (validaCampos()) {
                auth.signInWithEmailAndPassword(etEmail.text.toString(), etPass.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Login Fail",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        btnRegister.setOnClickListener() {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnGoogle.setOnClickListener() {
            signIn()
        }

        btnFacebook.setOnClickListener() {
            loginFacebook()
        }

        setSupportActionBar(toolbar).apply {
            title = "Sign Up"
        }

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tfEmail.error = ""
            }
        })

        etPass.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tfEmail.error = ""
            }
        })

    }

    private fun validaCampos(): Boolean {
        var response = true

        if (etEmail.text.isNullOrBlank()) {
            tfEmail.error = "Please type your e-mail"
            response = false
        }

        if (etPass.text.isNullOrBlank()) {
            tfPass.error = "Please type your password"
            response = false
        } else if (etPass.text!!.length < 8){
            tfPass.error = "Password must be at least 8 characters long"
            response = false
        }

        return response
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e)
                // ...
            }
        } else if (requestCode == FB_SIGN_IN) {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Valide seu email", Toast.LENGTH_LONG).show()
                auth.signOut()
            }
        }
    }

    private fun irParaHome(uiid: String) {
        //AppUtil.salvarIdUsuario(application.applicationContext, uiid)
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    // ...
                }
            }
    }

    private fun loginFacebook() {
        val instanceFirebase = LoginManager.getInstance()

        instanceFirebase.logInWithReadPermissions(this, listOf("email", "public_profile"))
        instanceFirebase.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult) {
                val credential: AuthCredential = FacebookAuthProvider.getCredential(loginResult.accessToken.token)
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener { irParaHome(loginResult.accessToken.userId) }
            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException) {
                Toast.makeText(this@LoginActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        private const val RC_SIGN_IN = 1
        private const val FB_SIGN_IN = 64206
    }
}