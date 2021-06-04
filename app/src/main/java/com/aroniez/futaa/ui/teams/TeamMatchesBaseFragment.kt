package com.aroniez.futaa.ui.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aroniez.futaa.models.fixture.Fixture
import com.aroniez.futaa.utils.displayFixtures
import kotlinx.android.synthetic.main.fragment_recyclerview_no_loading.*


abstract class TeamMatchesBaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.aroniez.futaa.R.layout.fragment_recyclerview_no_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadCompetitions()
    }

    abstract fun getMatches(): ArrayList<Fixture>


    private fun loadCompetitions() {
        if (getMatches().size > 0) {
            displayFixtures(getMatches(), recycler_view, context!!, true)
        } else {
            errorTextView.text = "No matches at the moment"
        }
    }

}