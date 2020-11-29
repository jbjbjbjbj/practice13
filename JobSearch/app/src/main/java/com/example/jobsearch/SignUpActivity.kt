package com.example.jobsearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    companion object {
        const val USERS_COLLECTION = "users"
    }

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setupViews()
    }

    private fun signUp(
        email: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    addUserData(task.result?.user?.uid!!, sign_up_email_text.text.toString(), sign_up_firstname_text.text.toString(), sign_up_lastname_text.text.toString(), sign_up_password_text.text.toString())

                    Toast.makeText(this, "User successfully created", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                    finish()

                    return@addOnCompleteListener
                }

                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
            }
    }

    private fun addUserData(
        uid: String,
        email: String,
        firstname: String,
        lastname: String,
        password: String
    ) {
        firestore.collection(USERS_COLLECTION).document(uid).set(
            hashMapOf(
                "uid" to uid,
                "email" to email,
                "firstname" to firstname,
                "lastname" to lastname,
                "password" to password
            )
        )
    }

    private fun setupViews(){
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = "Sign Up"

        sign_in_textview.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        sign_up_button.setOnClickListener {
            signUp(email = sign_up_email_text.text.toString(), password = sign_up_password_text.text.toString())
        }
    }
}
