package com.walmart.app.ui.countrylist

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walmart.app.R
import com.walmart.app.di.NetworkRepository
import com.walmart.app.model.Country
import com.walmart.app.network.CountryRepository
import com.walmart.app.network.RequestResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UIState(
    val countryList: List<CountryUIModel> = emptyList(),
    val loading: Boolean = false,
    @StringRes val errorMessageId:  Int? = null
)

data class CountryUIModel(
    val title: String,
    val code: String,
    val subtitle: String,
    val imageUrl: String
)

@HiltViewModel
class CountryListViewModel @Inject constructor(
    @NetworkRepository
    private val countryRepository: CountryRepository
): ViewModel() {

    private val _uiStateFlow = MutableStateFlow(UIState())
    val uiState = _uiStateFlow.asStateFlow()

    fun getCountryList() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = countryRepository.getCountries()) {
                is RequestResponse.Success -> handleSuccess(response.data)
                is RequestResponse.Error -> handleError()
            }
        }
    }

    private fun handleSuccess(countryList: List<Country>) {
        countryList.map {
            val title = if (it.region.isBlank()) it.name else "${it.name}, ${it.region}"

            CountryUIModel(
                title = title,
                code = it.code,
                subtitle = it.capital,
                imageUrl = it.flag
            )
        }.also {
            _uiStateFlow.value = _uiStateFlow.value.copy(
                loading = false,
                countryList = it
            )
        }
    }

    private fun handleError() {
        _uiStateFlow.value = _uiStateFlow.value.copy(
            loading = false,
            errorMessageId = R.string.generic_network_error
        )
    }
}
