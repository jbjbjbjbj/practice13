package com.example.jobsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_company.*

class CompanyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)

        openFragment(CompanyProfileFragment.newInstance())
        setupViews()

    }

    private fun setupViews(){

        company_bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_response -> {
                    openFragment(CompanyResponseFragment.newInstance())
                    true
                }
                R.id.menu_profile -> {
                    openFragment(CompanyProfileFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
