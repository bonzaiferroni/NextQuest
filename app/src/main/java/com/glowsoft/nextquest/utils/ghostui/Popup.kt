package com.glowsoft.nextquest.utils.ghostui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.glowsoft.nextquest.utils.Gaps
import com.glowsoft.nextquest.utils.Paddings

@Composable
fun Popup(
    showDialog: Boolean,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    if (!showDialog) return

    Dialog(
        onDismissRequest = { onDismiss.invoke() },
    ) {
        Card(
            modifier = modifier
                .padding(Paddings.small()),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column (
                modifier = Modifier.padding(Paddings.small()),
                verticalArrangement = Arrangement.spacedBy(Gaps.medium()),
            ) {
                content()
            }
        }
    }
}

@Composable
fun PopupButtons (
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onAccept: () -> Unit,
    onCancel: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Gaps.large()),
        modifier = modifier
    ) {
        Button(
            onClick = onCancel,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier.weight(1f),
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                contentDescription = "cancel"
            )
        }
        Button(
            onClick = onAccept,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
            ),
            modifier = Modifier.weight(1f),
            enabled = enabled,
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                tint = MaterialTheme.colorScheme.onSecondary,
                contentDescription = "accept"
            )
        }
    }
}