package com.example.jobsearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class CompanyResponseFragment : Fragment() {

    companion object {
        fun newInstance(): ResponseFragment = ResponseFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_company_response, container, false)


}
