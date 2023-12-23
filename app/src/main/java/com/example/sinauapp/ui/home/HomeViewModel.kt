package com.example.sinauapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.sinauapp.domain.repository.MapelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mapelRepository: MapelRepository
): ViewModel() {


}