package com.sportnow.bra.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.sportnow.bra.repository.FixturesRepository

class FixturesViewModel(private val sourceRepository: FixturesRepository) : ViewModel() {
    fun getFavoritesMatches() = sourceRepository.favorites()
}