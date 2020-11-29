package com.example.jobsearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_vacancy.view.*

class VacancyAdapter(
    private val vacancies: List<Vacancy> = emptyList(),
    private val onVacancyClick: (Vacancy) -> Unit
) : RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VacancyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_vacancy, parent, false))

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bindVacancy(vacancy = vacancies[position])
    }

    override fun getItemCount(): Int = vacancies.size

    inner class VacancyViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bindVacancy(vacancy: Vacancy) {
            view.position_text.text = "Job position: " + vacancy.job_position
            view.company_name_text.text = "Company name: " + vacancy.company_name
            view.salary_text.text = "Salary: " + vacancy.salary + " KZT"
            view.city_text.text = "City: " + vacancy.city
            view.description_text.text = "Description: " + vacancy.description
            view.skills_text.text = "Necessary skills: " + vacancy.skills

            view.setOnClickListener {
                onVacancyClick(vacancy)
            }

        }
    }
}