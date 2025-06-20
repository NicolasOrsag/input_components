package nicolas.orsag.inputcomponents.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val lightAppColors = AppColors(
    surface = SurfaceColors(
        xHigh = coreGrey500,
        xLow = coreGrey00,
        brand = o2Blue500,
        danger = coreRed600,
        dangerVariant = coreRed100,
        warning = coreYellow700,
        warningVariant = coreYellow100
    ),
    content = ContentColors(
        onNeutralXxHigh = coreGrey950,
        onNeutralMedium = coreGrey500,
        onNeutralLow = coreGrey300,
        onNeutralDanger = coreRed700,
        onNeutralWarning = coreYellow700
    ),
    state = StateColors(
        defaultHover = coreAlphaDim50,
        defaultFocus = coreAlphaDim800
    )
)

// No dark colors for now
val darkAppColors = lightAppColors

private val materialDarkColorScheme = darkColorScheme(
    background = coreGrey00
)

private val materialLightColorScheme = lightColorScheme(
    background = coreGrey00
)

val localAppColors = staticCompositionLocalOf<AppColors> {
    error("No AppColors provided")
}

object AppTheme {
    val colors: AppColors
        @Composable get() = localAppColors.current
}

@Composable
fun InputComponentsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val appColors = if (darkTheme) darkAppColors else lightAppColors
    val materialColors = if (darkTheme) materialDarkColorScheme else materialLightColorScheme


    CompositionLocalProvider(localAppColors provides appColors) {
        MaterialTheme(
            colorScheme = materialColors,
            typography = typography,
            content = content
        )
    }
}