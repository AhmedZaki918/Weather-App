package com.weatherapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ErrorScreen(
    title: String?,
    text: String?,
    confirmButton: String?,
    closeDialog: () -> Boolean
) {

    Box(
        modifier = Modifier
            .background( MaterialTheme.colors.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        var openDialog by remember { mutableStateOf(true) }

        if (openDialog) {
            AlertDialog(
                onDismissRequest = {
                    closeDialog()
                    openDialog = false
                },
                title = {
                    Text(
                        text = title.toString(), color = MaterialTheme.colors.onSecondary,
                        fontSize = MaterialTheme.typography.h5.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = text.toString(),
                        color = MaterialTheme.colors.onSecondary,
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        fontWeight = FontWeight.Normal
                    )
                },

                confirmButton = {
                    Button(
                        onClick = {
                            closeDialog()
                            openDialog = false
                        }, colors = ButtonDefaults.buttonColors( MaterialTheme.colors.background)
                    ) {
                        Text(text = confirmButton.toString(), color =  MaterialTheme.colors.primaryVariant)
                    }
                },
                backgroundColor = MaterialTheme.colors.secondary
            )
        }
    }
}

@Preview
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(
        title = "Error",
        text = "Invalid input from user",
        confirmButton = "Ok",
        closeDialog = ({ false })
    )
}