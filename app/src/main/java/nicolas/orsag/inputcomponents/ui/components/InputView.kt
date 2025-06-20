package nicolas.orsag.inputcomponents.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import nicolas.orsag.inputcomponents.R
import nicolas.orsag.inputcomponents.ui.theme.AppTheme.colors
import nicolas.orsag.inputcomponents.ui.theme.Dimens
import nicolas.orsag.inputcomponents.ui.theme.InputComponentsTheme

@Composable
fun InputView(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String = "",
    isOptional: Boolean = false,
    isError: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: (@Composable () -> Unit)? = null,
    minHeight: Dp = Dimens.spacing.xl5,
    placeholderTextColor: Color = colors.content.onNeutralLow,
    textColor: Color = colors.content.onNeutralXxHigh,
    errorBorderColor: Color = colors.surface.danger,
    borderColor: Color = colors.surface.xHigh,
    errorLabelColor: Color = colors.content.onNeutralDanger,
    labelColor: Color = colors.content.onNeutralXxHigh,
    optionalIndicatorColor: Color = colors.content.onNeutralMedium,
    containerColor: Color = colors.surface.xLow,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = modifier) {
        // Label with optional indicator
        label?.let {
            Row(
                modifier = Modifier.padding(bottom = Dimens.spacing.xs),
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacing.xs),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Label text
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isError) errorLabelColor else labelColor
                )

                // Optional indicator
                if (isOptional) {
                    Text(
                        text = stringResource(R.string.text_view_optional),
                        style = MaterialTheme.typography.labelSmall,
                        color = optionalIndicatorColor
                    )
                }
            }
        }

        val borderColor = when {
            isError -> errorBorderColor
            else -> borderColor
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = minHeight),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = textColor
            ),
            keyboardOptions = keyboardOptions,
            singleLine = singleLine,
            cursorBrush = SolidColor(textColor),
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(Dimens.radius.input))
                        .background(containerColor)
                        .border(
                            width = Dimens.stroke.l,
                            color = borderColor,
                            shape = RoundedCornerShape(Dimens.radius.input)
                        )
                        .padding(vertical = Dimens.spacing.xs, horizontal = Dimens.spacing.m),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = MaterialTheme.typography.bodyMedium,
                                color = placeholderTextColor
                            )
                        }
                        innerTextField()
                    }

                    if (trailingIcon != null) {
                        Spacer(modifier = Modifier.width(Dimens.spacing.xs))
                        trailingIcon()
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun InputViewPreview() {
    InputComponentsTheme {
        Column(
            modifier = Modifier.padding(Dimens.spacing.m)
        ) {
            // Enabled state
            InputView(
                value = "",
                onValueChange = {},
                label = "Input",
                placeholder = "Placeholder",
                isOptional = true,
                modifier = Modifier.padding(bottom = Dimens.spacing.l)
            )

            // Error state
            InputView(
                value = "",
                onValueChange = {},
                label = "Input",
                placeholder = "Placeholder",
                isOptional = false,
                isError = true,
                modifier = Modifier.padding(bottom = Dimens.spacing.l)
            )
        }
    }
}