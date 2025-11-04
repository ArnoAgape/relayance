package com.kirabium.relayance.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.kirabium.relayance.screen.detailScreen.DetailScreen
import com.kirabium.relayance.screen.detailScreen.DetailViewModel
import com.kirabium.relayance.ui.theme.RelayanceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()

    companion object {
        const val EXTRA_CUSTOMER_ID = "customer_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val customerId = intent.getIntExtra(EXTRA_CUSTOMER_ID, -1)
        if (customerId != -1) {
            viewModel.observeCustomer(customerId)
        }
        setContent {
            RelayanceTheme {
                DetailScreen(
                    viewModel = hiltViewModel<DetailViewModel>(),
                    onBackClick = { onBackPressedDispatcher.onBackPressed() }
                )
            }
        }
    }
}


