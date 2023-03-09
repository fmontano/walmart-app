package com.walmart.app.ui.countrylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.walmart.app.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountryListFragment: Fragment() {

    private val viewModel by viewModels<CountryListViewModel>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: View
    private lateinit var errorMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getCountryList()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::handleUiState)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        inflater.inflate(R.layout.fragment_country_list, container, false).run {
            recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = CountryListAdapter()
                addItemDecoration(DividerItemDecoration(this@CountryListFragment.context, LinearLayoutManager.VERTICAL))
            }

            progressBar = findViewById(R.id.progressBar)
            errorMessage = findViewById(R.id.errorMessage)

            return this
        }
    }

    private fun handleUiState(uiState: UIState) {
        when {
            uiState.loading -> showLoadingView()
            uiState.errorMessageId != null -> showError(uiState.errorMessageId)
            else -> showCountryList(uiState.countryList)
        }
    }

    private fun showError(@StringRes message: Int) {
        errorMessage.isVisible = true
        errorMessage.text = resources.getString(message)
    }

    private fun showLoadingView() {
        progressBar.isVisible = true
    }

    private fun showCountryList(countryList: List<CountryUIModel>) {
        progressBar.isVisible = false
        errorMessage.isVisible = false
        (recyclerView.adapter as CountryListAdapter).addItems(countryList)
    }
}
