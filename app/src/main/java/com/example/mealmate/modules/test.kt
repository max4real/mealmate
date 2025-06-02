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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri


@Composable
fun ShareTextViaSms() {
    val context = LocalContext.current

    Button(onClick = {
        val phoneNumber = "09789786123"
        val message = "Lee pl thar gyi, Mwakkk"

        val uri = "smsto:$phoneNumber".toUri()

        val intent = Intent(Intent.ACTION_SENDTO, uri).apply {
            putExtra("sms_body", message)
        }

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No SMS app found.", Toast.LENGTH_SHORT).show()
        }
    }) {
        Text("Share via SMS")
    }
}

@Composable
fun PickContactPhoneNumber() {
    val context = LocalContext.current
    var phoneNumber = remember { mutableStateOf("") }

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
        contactUri?.let {
            // Now read the phone number from the URI
            val cursor = context.contentResolver.query(
                contactUri, arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER), null, null, null
            )
            cursor?.use {
                if (it.moveToFirst()) {
                    phoneNumber.value = it.getString(
                        it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    )
                }
            }
        }
    }

    // Request permission when Composable is launched
    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
        if (!granted) {
            permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {
            contactPickerLauncher.launch(null)
        }) {
            Text("Pick Contact")
        }

        if (phoneNumber.value.isNotEmpty()) {
            Text("Phone: $phoneNumber", modifier = Modifier.padding(top = 8.dp))
        }
    }
}