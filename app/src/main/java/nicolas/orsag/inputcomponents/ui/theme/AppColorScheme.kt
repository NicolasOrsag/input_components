package nicolas.orsag.inputcomponents.ui.theme

import androidx.compose.ui.graphics.Color

data class AppColors(
    val surface: SurfaceColors,
    val content: ContentColors,
    val state: StateColors
)

data class SurfaceColors(
    val xHigh: Color,
    val xLow: Color,
    val brand: Color,
    val danger: Color,
    val dangerVariant: Color,
    val warning: Color,
    val warningVariant: Color
)

data class ContentColors(
    val onNeutralXxHigh: Color,
    val onNeutralMedium: Color,
    val onNeutralLow: Color,
    val onNeutralDanger: Color,
    val onNeutralWarning: Color
)

data class StateColors(
    val defaultHover: Color,
    val defaultFocus: Color
)