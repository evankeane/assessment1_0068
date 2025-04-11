package com.example.assessment1.ui.theme.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf

import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.assessment1.R
import com.example.assessment1.ui.theme.theme.Assessment1Theme
import com.example.assessment1.navigation.Screen



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.About.route) }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.tentang_aplikasi),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    var amountInput by rememberSaveable { mutableStateOf("") }
    var hasError by rememberSaveable { mutableStateOf(false) }
    var selectedCurrency by rememberSaveable { mutableStateOf("Dollar") }
    var result by rememberSaveable { mutableFloatStateOf(0f) }

    val context = LocalContext.current
    val currencyOptions = listOf("Dollar", "Euro", "Yen", "Poundsterling")

    val formattedResult = stringResource(
        id = R.string.hasil_konversi,
        result,
        selectedCurrency
    )

    val imageResId = when (selectedCurrency) {
        "Dollar" -> R.drawable.dollar
        "Euro" -> R.drawable.euro
        "Yen" -> R.drawable.yen
        "Poundsterling" -> R.drawable.poundsterling
        else -> R.drawable.dollar
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = { Text(text = stringResource(id = R.string.placeholder_input)) },
            isError = hasError,
            supportingText = {
                if (hasError) Text(text = stringResource(id = R.string.input_invalid))
            },
            trailingIcon = {
                if (hasError) Icon(Icons.Default.Warning, contentDescription = null)
                else Text(text = stringResource(id = R.string.rp))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            currencyOptions.forEach { currency ->
                Row(
                    modifier = Modifier
                        .selectable(
                            selected = (currency == selectedCurrency),
                            onClick = { selectedCurrency = currency },
                            role = Role.RadioButton
                        )
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (currency == selectedCurrency),
                        onClick = null
                    )
                    Text(
                        text = currency,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }

        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Currency Image",
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(0.5f)
        )

        Button(
            onClick = {
                hasError = (amountInput.isBlank() || amountInput.toFloatOrNull() == null || amountInput.toFloat() == 0f)
                if (!hasError) {
                    val amount = amountInput.toFloat()
                    result = convertCurrency(amount, selectedCurrency)
                }
            },
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.konversi))
        }

        if (result != 0f) {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = formattedResult,
                style = MaterialTheme.typography.headlineMedium
            )

            Button(
                onClick = {
                    amountInput = ""
                    hasError = false
                    selectedCurrency = "Dollar"
                    result = 0f
                },
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = stringResource(id = R.string.reset))
            }
            Button(
                onClick = {
                    val shareMessage = "Jumlah: Rp $amountInput\nKonversi ke $selectedCurrency = $result $selectedCurrency"
                    shareData(context, shareMessage)
                },
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.bagikan))
            }
        }



    }
}

private fun convertCurrency(amount: Float, currency: String): Float {
    return when (currency) {
        "Dollar" -> amount / 16968f
        "Euro" -> amount / 18936f
        "Yen" -> amount / 115f
        "Poundsterling" -> amount / 21925f
        else -> 0f
    }
}

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null)
        context.startActivity(shareIntent)
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Assessment1Theme {
        MainScreen(rememberNavController())
    }
}




