package de.iu.iur1.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Ein schlanker Wrapper für eine OutlinedCard mit Innenabstand.
 * Absichtlich generisch gehalten, damit ihr ihn überall nutzen könnt.
 */
@Composable
fun OutlinedBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    OutlinedCard(modifier = modifier) {
        Column(Modifier.padding(12.dp)) {
            content()
        }
    }
}
