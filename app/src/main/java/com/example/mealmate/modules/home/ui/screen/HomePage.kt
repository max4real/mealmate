package com.example.mealmate.modules.home.ui.screen

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mealmate.modules.home.ui.viewmodel.HomeViewModel


@Composable
fun HomePage(appNavi: NavHostController = rememberNavController()) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = homeViewModel.isLoginLoading.collectAsState().toString()
        )
        Spacer(modifier = Modifier.height(16.dp))
        PickContactAndSendSms()
    }
}

@Composable
fun PickContactAndSendSms() {
    val context = LocalContext.current
    var selectedPhone = remember { mutableStateOf("") }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    // Contact picker launcher
    val contactPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact()
    ) { contactUri: Uri? ->
        contactUri?.let { uri ->
            val cursor = context.contentResolver.query(
                uri,
                null,  // Get all columns for now
                null,
                null,
                null
            )

            cursor?.use {
                if (it.moveToFirst()) {
                    val idIndex = it.getColumnIndex(ContactsContract.Contacts._ID)
                    val hasPhoneNumberIndex =
                        it.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)

                    val contactId = it.getString(idIndex)
                    val hasPhoneNumber = it.getInt(hasPhoneNumberIndex) > 0

                    if (hasPhoneNumber) {
                        val phoneCursor = context.contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                            arrayOf(contactId),
                            null
                        )

                        phoneCursor?.use { pc ->
                            if (pc.moveToFirst()) {
                                val numberIndex =
                                    pc.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                val number = pc.getString(numberIndex)
                                selectedPhone.value = number
                                sendSms(context, selectedPhone.value)
                            }
                        }
                    } else {
                        Toast.makeText(context, "No number found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
        if (!granted) {
            permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    Button(onClick = {
        contactPickerLauncher.launch(null)
    }) {
        Text("Pick Contact & Send SMS")
    }

    if (selectedPhone.value.isNotEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        Text("Selected: ${selectedPhone.value}")
    }
}

private fun sendSms(context: android.content.Context, phoneNumber: String) {
    val message = "Hey, What up broooooo!"
    val uri = Uri.parse("smsto:$phoneNumber")

    val intent = Intent(Intent.ACTION_SENDTO, uri).apply {
        putExtra("sms_body", message)
    }

    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No SMS app found.", Toast.LENGTH_SHORT).show()
    }
}


