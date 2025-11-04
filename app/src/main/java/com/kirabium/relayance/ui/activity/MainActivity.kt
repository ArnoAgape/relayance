package com.kirabium.relayance.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kirabium.relayance.databinding.ActivityMainBinding
import com.kirabium.relayance.screen.homeScreen.HomeUiState
import com.kirabium.relayance.screen.homeScreen.HomeViewModel
import com.kirabium.relayance.ui.adapter.CustomerAdapter
import com.kirabium.relayance.ui.common.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var customerAdapter: CustomerAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupCustomerRecyclerView()
        setupFab()
        observeCustomers()
        observeEvents()
    }

    private fun observeCustomers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is HomeUiState.Loading -> {
                            binding.loading.visibility = View.VISIBLE
                            binding.customerRecyclerView.visibility = View.GONE
                        }

                        is HomeUiState.Success -> {
                            binding.loading.visibility = View.GONE
                            binding.customerRecyclerView.visibility = View.VISIBLE
                            customerAdapter.updateData(state.customers)
                        }

                        is HomeUiState.Error.Empty -> {
                            binding.loading.visibility = View.GONE
                            binding.customerRecyclerView.visibility = View.GONE
                        }

                        is HomeUiState.Error.Generic -> {
                            binding.loading.visibility = View.GONE
                            binding.customerRecyclerView.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            viewModel.eventsFlow.collect { event ->
                when (event) {
                    is Event.ShowToast -> {
                        Toast.makeText(this@MainActivity, event.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> null
                }
            }
        }
    }


    private fun setupFab() {
        binding.addCustomerFab.setOnClickListener {
            val intent = Intent(this, AddCustomerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupCustomerRecyclerView() {
        binding.customerRecyclerView.layoutManager = LinearLayoutManager(this)
        customerAdapter = CustomerAdapter(emptyList()) { customer ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_CUSTOMER_ID, customer.id)
            }
            startActivity(intent)
        }
        binding.customerRecyclerView.adapter = customerAdapter
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
