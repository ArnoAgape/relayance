package com.kirabium.relayance.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kirabium.relayance.databinding.ActivityAddCustomerBinding
import com.kirabium.relayance.screen.addScreen.AddUiState
import com.kirabium.relayance.screen.addScreen.AddViewModel
import com.kirabium.relayance.ui.common.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCustomerBinding

    private val viewModel: AddViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupToolbar()
        observeEvents()
        observeUiState()
        setupSave()
    }


    private fun observeUiState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is AddUiState.Idle -> {
                        binding.loading.visibility = View.GONE
                        binding.saveFab.isEnabled = true
                    }

                    is AddUiState.Loading -> {
                        binding.loading.visibility = View.VISIBLE
                        binding.saveFab.isEnabled = false
                    }

                    is AddUiState.Success, is AddUiState.Error -> {
                        binding.loading.visibility = View.GONE
                        binding.saveFab.isEnabled = true
                    }
                }
            }
        }
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.eventsFlow.collect { event ->
                    when (event) {
                        is Event.ShowToast -> {
                            Toast.makeText(this@AddCustomerActivity, event.message, Toast.LENGTH_SHORT).show()
                        }
                        is Event.CustomerAdded -> { finish() }
                    }
                }
            }
        }
    }

    private fun setupSave() {
        binding.saveFab.setOnClickListener {
            val newName = binding.nameEditText.text.toString().trim()
            val newEmail = binding.emailEditText.text.toString().trim()

            viewModel.addCustomer(newName, newEmail)
        }
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupBinding() {
        binding = ActivityAddCustomerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}