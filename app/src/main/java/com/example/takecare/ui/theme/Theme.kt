package com.example.takecare.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val LightCareColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = PrimaryContent,
    secondary = Secondary,
    onSecondary = SecondaryContent,
    tertiary = Accent,
    onTertiary = AccentContent,
    background = Base100,
    onBackground = BaseContent,
    surface = Base200,
    onSurface = BaseContent,
    error = Error,
    onError = ErrorContent,
    surfaceVariant = Base300,
    outline = Neutral
)

val CareShapes = Shapes(
    small = RoundedCornerShape(16.dp),   // radius-selector
    medium = RoundedCornerShape(24.dp),  // radius-field
    large = RoundedCornerShape(16.dp)    // radius-box
)

@Composable
fun TakeCareTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightCareColorScheme,
        typography = Typography,
        content = content,
        shapes = CareShapes,
    )
}