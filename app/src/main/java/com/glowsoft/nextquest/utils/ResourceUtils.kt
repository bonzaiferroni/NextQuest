package com.glowsoft.nextquest.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.bollwerks.eznav.R

object Paddings {
    @Composable
    fun small(): Dp = dimensionResource(R.dimen.padding_small)

    @Composable
    fun medium(): Dp = dimensionResource(R.dimen.padding_medium)

    @Composable
    fun large(): Dp = dimensionResource(R.dimen.padding_large)

    @Composable
    fun indent(): Dp = dimensionResource(R.dimen.padding_indent)
}

object Gaps {
    @Composable
    fun small(): Dp = dimensionResource(R.dimen.gap_small)

    @Composable
    fun medium(): Dp = dimensionResource(R.dimen.gap_medium)

    @Composable
    fun large(): Dp = dimensionResource(R.dimen.gap_large)
}

fun Modifier.paddingSmall(): Modifier = composed { this.padding(Paddings.small()) }
fun Modifier.paddingMedium(): Modifier = composed { this.padding(Paddings.medium()) }
fun Modifier.paddingLarge(): Modifier = composed { this.padding(Paddings.large()) }

fun Modifier.gapSmall(): Modifier = composed { this.padding(Gaps.small()) }
fun Modifier.gapMedium(): Modifier = composed { this.padding(Gaps.medium()) }
fun Modifier.gapLarge(): Modifier = composed { this.padding(Gaps.large()) }

@Composable
fun Arrangement.spacedBySmall(): Arrangement.HorizontalOrVertical = spacedBy(Paddings.small())
@Composable
fun Arrangement.spacedByMedium(): Arrangement.HorizontalOrVertical = spacedBy(Paddings.medium())
@Composable
fun Arrangement.spacedByLarge(): Arrangement.HorizontalOrVertical = spacedBy(Paddings.large())