package com.aroniez.futaa.ui.season.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aroniez.futaa.R
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.utils.displayFixtures
import kotlinx.android.synthetic.main.include_base_recyclerview_layout.*


class SeasonResultMatchesFragment(private val results: ArrayList<Fixture>) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.include_recyclerview_progressbar_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayFixtures(results, baseRecyclerView, context!!, false)
    }
}