package com.example.jobsearch

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_company_profile.view.*

class CompanyProfileFragment : Fragment() {

    companion object {
        fun newInstance(): CompanyProfileFragment = CompanyProfileFragment()
    }

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(R.layout.fragment_company_profile, container, false)

        if (auth.currentUser != null){
            view.add_vacancy_button.setOnClickListener {
                val intent = Intent(context!!, CreateVacancyActivity::class.java)
                startActivity(intent)
            }

            view.company_log_out_button.setOnClickListener {
                auth.signOut()
                val intent = Intent(context!!, MainActivity::class.java)
                startActivity(intent)
            }
        }

        return view
    }

}
