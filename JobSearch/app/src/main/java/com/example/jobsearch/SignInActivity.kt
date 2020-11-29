package com.example.jobsearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        setupViews()
    }

    private fun signIn(
        email: String,
        password: String
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, "Welcome, ${task.result?.user?.email}", Toast.LENGTH_LONG).show()

                    return@addOnCompleteListener
                }

                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
            }
    }

    private fun setupViews(){
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = "Sign In"

        sign_up_textview.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        sign_in_button.setOnClickListener {
            signIn(email = email_text.text.toString(), password = password_text.text.toString())
        }
    }
}
