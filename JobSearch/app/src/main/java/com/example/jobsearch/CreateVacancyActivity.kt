package com.example.jobsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_vacancy.*

class CreateVacancyActivity : AppCompatActivity() {

    companion object {
        const val VACANCY_COLLECTION = "vacancies"
    }

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_vacancy)

        setupViews()
    }

    private fun setupViews(){
        val cities = resources.getStringArray(R.array.cities)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cities)
        vacancy_city_edit_text.setAdapter(adapter)

        save_vacancy_button.setOnClickListener {

            addVacancyData("1", job_position_edit_text.text.toString(), description_edit_text.text.toString(),
                skills_edit_text.text.toString(), vacancy_city_edit_text.text.toString(), vacancy_salary_edit_text.text.toString(),"a", "1")
        }
    }

    private fun addVacancyData(
        id: String,
        job_position: String,
        description: String,
        skills: String,
        city: String,
        salary: String,
        company_name: String,
        company_id: String
    ) {
        firestore.collection(VACANCY_COLLECTION).document(id).set(
            hashMapOf(
                "id" to id,
                "job_position" to job_position,
                "description" to description,
                "skills" to skills,
                "city" to city,
                "salary" to salary,
                "company_name" to company_name,
                "company_id" to company_id
            )
        )
    }
}
