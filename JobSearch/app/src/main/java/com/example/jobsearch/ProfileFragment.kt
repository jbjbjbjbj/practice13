package com.example.jobsearch

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        if (auth.currentUser != null){
            view.profile_sign_in_text.visibility = View.GONE
            view.add_cv_button.visibility = View.VISIBLE
            view.log_out_button.visibility = View.VISIBLE
            view.add_cv_button.setOnClickListener {
                val intent = Intent(context!!, CreateCVActivity::class.java)
                startActivity(intent)
            }

            view.log_out_button.setOnClickListener {
                auth.signOut()
                val intent = Intent(context!!, MainActivity::class.java)
                startActivity(intent)
            }
        } else {
            view.profile_sign_in_text.visibility = View.VISIBLE
            view.add_cv_button.visibility = View.GONE
            view.log_out_button.visibility = View.GONE
            view.profile_sign_in_text.setOnClickListener {
                val intent = Intent(context!!, SignInActivity::class.java)
                startActivity(intent)
            }
        }

        return view
    }
}