package com.example.jobsearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_cv.*

class CreateCVActivity : AppCompatActivity() {

    lateinit var radioButtonGender: RadioButton
    lateinit var radioButtonExperience: RadioButton

    companion object {
        const val CV_COLLECTION = "cvs"
    }

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_cv)

        setupViews()
    }

    private fun setupViews(){
        val userID = auth.currentUser?.uid!!
        val cities = resources.getStringArray(R.array.cities)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cities)
        city_edit_text.setAdapter(adapter)
        val degrees = resources.getStringArray(R.array.degree)
        degree_spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, degrees)
        val languages = resources.getStringArray(R.array.languages)
        native_lang_spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        foreign_lang_spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        var employment = ""
        var schedule = ""
        var driver_license = ""

        save_cv_button.setOnClickListener {
            val selectedOptionGender: Int = gender_radio_group!!.checkedRadioButtonId
            radioButtonGender = findViewById(selectedOptionGender)
            val selectedOptionExp: Int = experience_radio_group!!.checkedRadioButtonId
            radioButtonExperience = findViewById(selectedOptionExp)

            if(cb_fulltime.isChecked){
                employment += cb_fulltime.text.toString() + " "
            }
            if (cb_halftime.isChecked) {
                employment += cb_halftime.text.toString() + " "
            }
            if (cb_internship.isChecked) {
                employment += cb_internship.text.toString() + " "
            }
            if (cb_fullday.isChecked) {
                schedule += cb_fullday.text.toString() + " "
            }
            if (cb_flexible.isChecked) {
                schedule += cb_flexible.text.toString() + " "
            }
            if (cb_remote.isChecked) {
                schedule += cb_remote.text.toString() + " "
            }
            if (cb_no.isChecked) {
                driver_license += cb_no.text.toString() + " "
            }
            if (cb_a.isChecked) {
                driver_license += cb_a.text.toString() + " "
            }
            if (cb_b.isChecked) {
                driver_license += cb_b.text.toString() + " "
            }
            if (cb_c.isChecked) {
                driver_license += cb_c.text.toString() + " "
            }
            if (cb_d.isChecked) {
                driver_license += cb_d.text.toString() + " "
            }
            if (cb_e.isChecked) {
                driver_license += cb_e.text.toString() + " "
            }

            addCVData(userID, name_edit_text.text.toString(), surname_edit_text.text.toString(), phone_edit_text.text.toString(),
                city_edit_text.text.toString(), day_of_birth_edit_text.text.toString() +" "+ month_of_birth_edit_text.text.toString() +" "+
                        year_of_birth_edit_text.text.toString(), radioButtonGender.text.toString(), citizenship_edit_text.text.toString(),
                        specialty_edit_text.text.toString(), salary_edit_text.text.toString(), radioButtonExperience.text.toString(),
                        degree_spinner.selectedItem.toString(), native_lang_spinner.selectedItem.toString(),
                        foreign_lang_spinner.selectedItem.toString(), employment, schedule, driver_license)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun addCVData(
        uid: String,
        name: String,
        surname: String,
        phone: String,
        city: String,
        dof: String,
        gender: String,
        citizenship: String,
        specialty: String,
        salary: String,
        experience: String,
        degree: String,
        native_lang: String,
        foreign_lang: String,
        employment: String,
        schedule: String,
        driver_license: String
    ) {
        firestore.collection(CV_COLLECTION).document(uid).set(
            hashMapOf(
                "uid" to uid,
                "name" to name,
                "surname" to surname,
                "phone" to phone,
                "city" to city,
                "dof" to dof,
                "gender" to gender,
                "citizenship" to citizenship,
                "specialty" to specialty,
                "salary" to salary,
                "experience" to experience,
                "degree" to degree,
                "native_lang" to native_lang,
                "foreign_lang" to foreign_lang,
                "employment" to employment,
                "schedule" to schedule,
                "driver_license" to driver_license
            )
        )
    }
}
