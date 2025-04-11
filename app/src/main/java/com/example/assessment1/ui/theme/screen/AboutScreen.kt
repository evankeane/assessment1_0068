package com.example.assessment1.ui.theme.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.assessment1.R
import com.example.assessment1.ui.theme.theme.Assessment1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavHostController) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.tentang_aplikasi))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = stringResource(id = R.string.penjelasan_app),
                style = MaterialTheme.typography.bodyLarge
            )

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.judul_identitas),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = stringResource(id = R.string.nama))
                    Text(text = stringResource(id = R.string.nim))
                    Text(text = stringResource(id = R.string.kelas))
                }
            }

            Image(
                painter = painterResource(id = R.drawable.ss),
                contentDescription = "Gambar Pertama",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.sss),
                contentDescription = "Gambar Kedua",

                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Button(
                onClick = {
                    openInstagram(context, "evannelwann")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Kunjungi Instagram")
            }
        }
    }
}

fun openInstagram(context: Context, username: String) {
    val instagramIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/$username"))
    instagramIntent.setPackage("com.instagram.android")

    val packageManager = context.packageManager
    val canResolve = instagramIntent.resolveActivity(packageManager) != null

    val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/$username"))

    context.startActivity(if (canResolve) instagramIntent else fallbackIntent)
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AboutScreenPreview() {
    Assessment1Theme {
        AboutScreen(rememberNavController())
    }
}
