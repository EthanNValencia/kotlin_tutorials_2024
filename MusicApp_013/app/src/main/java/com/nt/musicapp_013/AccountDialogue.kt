package com.nt.musicapp_013

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

// Had to pull this out of the MainActivity because of clashing imports.
// Kotlin forces me to use more files due to having so many imports with
// the same name. 
@Composable
fun AccountDialogue(dialogOpen: MutableState<Boolean>) {
    if(dialogOpen.value) {
        AlertDialog(
            onDismissRequest = { dialogOpen.value = false },
            confirmButton = {
                TextButton(onClick = { dialogOpen.value = false }) {
                    Text(text = "Confirm")
                }
        }, dismissButton = {
                TextButton(onClick = { dialogOpen.value = false }) {
                    Text(text = "Dismiss")
                }
        }, title = {
                Text(text = "Add Account")
        }, text = {
                Column(modifier = Modifier
                    .wrapContentSize()
                    .padding(0.dp), verticalArrangement = Arrangement.Center) {
                    TextField(value = "", onValueChange = {}, modifier = Modifier.padding(top = 16.dp), label = { Text(
                        text = "Email"
                    )})
                    TextField(value = "", onValueChange = {}, modifier = Modifier.padding(top = 8.dp), label = { Text(
                        text = "Password"
                    )})
                }
            },
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.primarySurface).padding(8.dp),
            shape = RoundedCornerShape(5.dp),
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
        )
    }
}