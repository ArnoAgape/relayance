package com.kirabium.relayance.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
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
                        is Event.ShowMessage -> showSnackbar(event.message)
                        is Event.CustomerAdded -> {
                            val intent = Intent()
                            intent.putExtra("ADD_RESULT", "success")
                            setResult(RESULT_OK, intent)
                            finish()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun showSnackbar(messageRes: Int) {
        Snackbar.make(binding.root,
            getString(messageRes),
            Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun setupSave() {
        binding.saveFab.setOnClickListener {
            val newName = binding.nameEditText.text.toString().trim()
            val newEmail = binding.emailEditText.text.toString().trim()

            lifecycleScope.launch {
                viewModel.addCustomer(newName, newEmail)
            }
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