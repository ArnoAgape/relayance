package com.kirabium.relayance.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.kirabium.relayance.screen.detailScreen.DetailScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CUSTOMER_ID = "customer_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        val customerId = intent.getIntExtra(EXTRA_CUSTOMER_ID, -1)
        FakeCustomers.customers.find { it.id == customerId }?.let {
            setContent {
                DetailScreen(customer = it) {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        }
    }
}


