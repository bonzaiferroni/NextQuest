package com.glowsoft.nextquest.utils.ghostui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun Toasty(
    text: String?,
    length: Int = Toast.LENGTH_SHORT,
) {
    val context = LocalContext.current
    LaunchedEffect(text) {
        if (text != null) {
            Toast.makeText(context, text, length).show()
        }
    }
}