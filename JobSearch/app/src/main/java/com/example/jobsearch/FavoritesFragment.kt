package com.example.jobsearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_favorites.view.*
import kotlinx.android.synthetic.main.fragment_favorites.view.favs_sign_in_text

class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance(): FavoritesFragment = FavoritesFragment()
    }

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        view.fav_vacancies_view.layoutManager = LinearLayoutManager(context!!)

        if(auth.currentUser != null){
            view.favs_sign_in_text.visibility = View.GONE
            view.fav_vacancies_text.visibility = View.VISIBLE
            view.fav_vacancies_view.visibility = View.VISIBLE
            loadFavorites(auth.currentUser?.uid!!)
        } else {
            view.favs_sign_in_text.visibility = View.VISIBLE
            view.fav_vacancies_text.visibility = View.GONE
            view.fav_vacancies_view.visibility = View.GONE
            view.favs_sign_in_text.setOnClickListener {
                val intent = Intent(context!!, SignInActivity::class.java)
                startActivity(intent)
            }
        }

        return view
    }

    private fun loadFavorites(id: String){

        var fav_vacancies= mutableListOf<Vacancy>()
        var vacancy_id: String
        firestore.collection(VacancyActivity.FAVS_COLLECTION)
            .whereEqualTo("uid", id)
            .get()
            .addOnSuccessListener {documents ->
                for (document in documents) {
                    vacancy_id = document.toObject(Favorites::class.java).vacancy_id
                    firestore.collection(CreateVacancyActivity.VACANCY_COLLECTION)
                        .document(vacancy_id)
                        .get().addOnSuccessListener { documents1 ->
                            val v = documents1.toObject(Vacancy::class.java)!!
                            fav_vacancies.add(v)
                            view!!.fav_vacancies_view.adapter = VacancyAdapter(fav_vacancies, onVacancyClick = {
                                val intent = Intent(context!!, VacancyActivity::class.java)
                                intent.putExtra(SearchFragment.EXTRA_VACANCY, it)
                                startActivity(intent)
                            })
                        }
                        .addOnFailureListener { exception ->
                            Log.w("taaaaag", "Error getting documents: ", exception)
                        }
                }

            }
            .addOnFailureListener { exception ->
                Log.w("taaaaag", "Error getting documents: ", exception)
            }
    }
}