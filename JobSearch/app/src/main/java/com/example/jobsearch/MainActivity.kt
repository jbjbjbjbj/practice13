package com.example.jobsearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFragment(SearchFragment.newInstance())
        setupViews()

    }

    private fun setupViews(){

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_search -> {
                    openFragment(SearchFragment.newInstance())
                    true
                }
                R.id.menu_favorites -> {
                    openFragment(FavoritesFragment.newInstance())
                    true
                }
                R.id.menu_response -> {
                    openFragment(ResponseFragment.newInstance())
                    true
                }
                R.id.menu_profile -> {
                    openFragment(ProfileFragment.newInstance())
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
