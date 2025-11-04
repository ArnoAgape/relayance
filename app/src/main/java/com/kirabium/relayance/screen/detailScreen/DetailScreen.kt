package com.kirabium.relayance.screen.detailScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kirabium.relayance.R
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.extension.DateExt.Companion.toHumanDate
import com.kirabium.relayance.ui.common.Event
import com.kirabium.relayance.ui.theme.RelayanceTheme
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel,
    onBackClick: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.eventsFlow.collect { event ->
            when (event) {
                is Event.ShowToast ->
                    Toast.makeText(
                        context, event.message, Toast.LENGTH_SHORT
                    ).show()

                else -> Unit
            }
        }
    }

    Scaffold(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.detail_screen_title))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClick()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.contentDescription_go_back)
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        when (uiState) {
            is DetailUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is DetailUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding),
                    contentAlignment = Alignment.Center
                ) {
                    val errorState = uiState as DetailUiState.Error
                    val message = when (errorState) {
                        is DetailUiState.Error.Empty -> errorState.message
                        is DetailUiState.Error.Generic -> errorState.message
                    }
                    Text(text = message, color = MaterialTheme.colorScheme.error)
                }
            }

            is DetailUiState.Success -> {
                val customer = (uiState as DetailUiState.Success).customer
                DetailContent(
                    modifier = Modifier.fillMaxWidth(),
                    customer = customer
                )

            }
        }
    }
}

@Composable
private fun DetailContent(
    modifier: Modifier = Modifier,
    customer: Customer
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
        ) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(modifier = Modifier.padding(24.dp)) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = customer.name,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = customer.email,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 16.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(id = R.string.created_at, customer.createdAt.toHumanDate()),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 16.sp
                            )
                        )
                    }
                    if (customer.isNewCustomer())
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = 24.dp, y = (-24).dp)
                                .rotate(45f)
                                .background(Color.Red)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.new_ribbon),
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                }
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun DetailScreenPreview() {
    RelayanceTheme {
        DetailContent(
            modifier = Modifier.fillMaxWidth(),
            customer =
                Customer(
                    id = 0,
                    name = "Nom du Client",
                    email = "email@client.com",
                    createdAt = Date()
                )
        )
    }

}
