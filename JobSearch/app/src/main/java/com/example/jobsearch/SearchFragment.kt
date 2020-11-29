package com.example.jobsearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    companion object {
        fun newInstance(): SearchFragment = SearchFragment()
        const val EXTRA_VACANCY = "vacancy"
    }

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        view.vacancies_view.layoutManager = LinearLayoutManager(context!!)

        view.search_field.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                var vacancies = mutableListOf<Vacancy>()

                firestore.collection(CreateVacancyActivity.VACANCY_COLLECTION)
                    .whereEqualTo("city", query)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            vacancies.add(document?.toObject(Vacancy::class.java)!!)
                        }

                        view!!.vacancies_view.adapter = VacancyAdapter(vacancies, onVacancyClick = {
                            val intent = Intent(context!!, VacancyActivity::class.java)
                            intent.putExtra(EXTRA_VACANCY, it)
                            startActivity(intent)
                        })

                    }
                    .addOnFailureListener { exception ->
                        Log.w("taaaaag", "Error getting documents: ", exception)
                    }
                firestore.collection(CreateVacancyActivity.VACANCY_COLLECTION)
                    .whereEqualTo("company_name", query)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            vacancies.add(document?.toObject(Vacancy::class.java)!!)
                        }

                        view!!.vacancies_view.adapter = VacancyAdapter(vacancies, onVacancyClick = {
                            val intent = Intent(context!!, VacancyActivity::class.java)
                            intent.putExtra(EXTRA_VACANCY, it)
                            startActivity(intent)
                        })
                    }
                    .addOnFailureListener { exception ->
                        Log.w("taaaaag", "Error getting documents: ", exception)
                    }
//                firestore.collection(CreateVacancyActivity.VACANCY_COLLECTION)
//                    .whereArrayContains("job_position", query)
//                    .get()
//                    .addOnSuccessListener { documents ->
//                        for (document in documents) {
//                            vacancies.add(document?.toObject(Vacancy::class.java)!!)
//                        }
//
//                        view!!.vacancies_view.adapter = VacancyAdapter(vacancies, onVacancyClick = {})
//                    }
//                    .addOnFailureListener { exception ->
//                        Log.w("taaaaag", "Error getting documents: ", exception)
//                    }
                return true
            }

        })

        return view
    }

    private fun loadVacanciesByPosition(position: String){

        var vacancies = emptyList<Vacancy>()

        firestore.collection(CreateVacancyActivity.VACANCY_COLLECTION)
            .whereArrayContains("job_position", position)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    vacancies = listOf(document?.toObject(Vacancy::class.java)!!)
                }

                view!!.vacancies_view.adapter = VacancyAdapter(vacancies, onVacancyClick = {})
            }
            .addOnFailureListener { exception ->
                Log.w("taaaaag", "Error getting documents: ", exception)
            }
    }

}