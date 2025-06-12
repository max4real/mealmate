package com.example.mealmate.modules.shopping_list.ui.widget

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.example.mealmate.extensions.HeightBox
import com.example.mealmate.extensions.WidthBox
import com.example.mealmate.modules.auth.ui.widget.CustomInputField
import com.example.mealmate.modules.shopping_list.data.model.ShoppingList
import com.example.mealmate.shared.widget.MultilineInputField
import com.example.mealmate.ui.theme.CustomColors

@Composable
fun DelegateDialog(
    info: List<ShoppingList>,
    onDismissRequest: () -> Unit,
) {
    val context = LocalContext.current
    val selectedPhoneNumber = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }

    LaunchedEffect(info) {
        message.value = info
            .flatMap { it.ingredients }
            .joinToString("\n") {
                "${if (it.bought) "✅" else "⬜"} ${it.name} - ${it.qty} (For ${it.recipeName})"
            }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
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
                                selectedPhoneNumber.value = number
                            }
                        }
                    } else {
                        Toast.makeText(context, "No number found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun sendSms(context: android.content.Context, phoneNumber: String) {
        val uri = Uri.parse("smsto:$phoneNumber")

        val intent = Intent(Intent.ACTION_SENDTO, uri).apply {
            putExtra("sms_body", message.value)
        }

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No SMS app found.", Toast.LENGTH_SHORT).show()
        }
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.background,
            tonalElevation = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
//                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .heightIn(min = 100.dp, max = 600.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Send Shopping List",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onDismissRequest) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.Gray
                        )
                    }
                }
                Text(
                    text = "Send your shopping list to a friend via SMS",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                    color = Color.Gray,
                )
                10.HeightBox()
                Row {
                    CustomInputField(
                        modifier = Modifier.weight(5f),
                        label = "Phone",
                        value = selectedPhoneNumber.value,
                        keyboardType = KeyboardType.Phone,
                        border = Color.Black,
                        onValueChange = {
                            selectedPhoneNumber.value = it
                        }
                    )
                    IconButton(onClick = {
                        contactPickerLauncher.launch(null)
                    }, modifier = Modifier.weight(1f)) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Close",
                            tint = Color.Black
                        )
                    }
                }

                Text(
                    text = "✅= Already bought ⬜ = Not Bought",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W500),
                    color = Color.Gray,
                )
                10.HeightBox()
                MultilineInputField(
                    label = "Message",
                    value = message.value,
                    minLines = 13,
                    fontSize = 14.sp,
                    onValueChange = {
                        message.value = it
                    }
                )
                Button(
                    onClick = {
                        sendSms(context, selectedPhoneNumber.value)
                    },
                    enabled = selectedPhoneNumber.value.isNotEmpty(),
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .height(45.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White,
                        disabledContentColor = Color.White.copy(alpha = 0.5f),
                        disabledContainerColor = CustomColors.textSecond
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = null,
                            tint = Color.White
                        )
                        5.WidthBox()
                        Text("Send")
                    }

                }
            }
        }
    }
}

