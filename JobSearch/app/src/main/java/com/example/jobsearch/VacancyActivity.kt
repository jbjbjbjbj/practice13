package com.example.jobsearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_vacancy.*

class VacancyActivity : AppCompatActivity() {

    companion object {
        const val FAVS_COLLECTION = "favorites"
    }

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vacancy)

        setupViews()
    }

    private fun setupViews(){
        val vacancy = intent.getParcelableExtra<Vacancy>(SearchFragment.EXTRA_VACANCY)!!

        position_text.text = "Job position: " + vacancy.job_position
        company_name_text.text = "Company name: " + vacancy.company_name
        salary_text.text = "Salary: " + vacancy.salary + " KZT"
        city_text.text = "City: " + vacancy.city
        description_text.text = "Description: " + vacancy.description
        skills_text.text = "Necessary skills: " + vacancy.skills

        var isMarked = false

        mark_favs_button.setOnClickListener {
            if (isMarked){
                deleteFavs(vacancy.id, auth.currentUser?.uid!!)
                mark_favs_button.setBackgroundResource(R.drawable.button)
                isMarked = false
            } else {
                markAsFavs(vacancy.id, auth.currentUser?.uid!!)
                mark_favs_button.setBackgroundResource(R.drawable.button_favs)
                isMarked = true
            }
        }
    }

    private fun markAsFavs(vacancy_id: String, uid: String){
        val doc_id = listOf(uid, vacancy_id).toString()
        firestore.collection(FAVS_COLLECTION).document(doc_id).set(
            hashMapOf(
                "vacancy_id" to vacancy_id,
                "uid" to uid
            )
        )
        Toast.makeText(this, "Marked as favorites", Toast.LENGTH_SHORT).show()
    }

    private fun deleteFavs(vacancy_id: String, uid: String){
        val doc_id = listOf(uid, vacancy_id).toString()
        firestore.collection(FAVS_COLLECTION)
            .document(doc_id).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Deleted from favorites", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Log.w("taaaaag", "Error deleting document ", exception)
            }
    }
}
