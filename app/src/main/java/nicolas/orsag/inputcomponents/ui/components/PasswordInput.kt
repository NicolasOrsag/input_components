package nicolas.orsag.inputcomponents.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import nicolas.orsag.inputcomponents.R
import nicolas.orsag.inputcomponents.ui.theme.AppTheme.colors
import nicolas.orsag.inputcomponents.ui.theme.Dimens
import nicolas.orsag.inputcomponents.ui.theme.InputComponentsTheme

data class PasswordRequirement(
    val label: String,
    val validator: (String) -> Boolean
)

@Composable
fun PasswordInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String = stringResource(R.string.password_placeholder),
    isOptional: Boolean = false,
    enabled: Boolean = true,
    showRequirements: Boolean = true,
    requirements: List<PasswordRequirement> = defaultPasswordRequirements(),
    requirementMetIndicator: (@Composable () -> Unit) = {DefaultRequirementMetIndicator()},
    requirementNotMetIndicator: (@Composable () -> Unit) = {DefaultRequirementNotMetIndicator()},
    requirementMetTextColor: Color = colors.content.onNeutralXxHigh,
    requirementNotMetTextColor: Color = colors.content.onNeutralMedium,
    onValidationChange: ((Boolean) -> Unit)? = null,
    minHeight: Dp = Dimens.spacing.xl5,
    placeholderTextColor: Color = colors.content.onNeutralLow,
    textColor: Color = colors.content.onNeutralXxHigh,
    errorBorderColor: Color = colors.surface.danger,
    borderColor: Color = colors.surface.xHigh,
    errorLabelColor: Color = colors.content.onNeutralDanger,
    labelColor: Color = colors.content.onNeutralXxHigh,
    optionalIndicatorColor: Color = colors.content.onNeutralMedium,
    containerColor: Color = colors.surface.xLow
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    // Validate password against all requirements
    val requirementStates = remember(value) {
        requirements.map { requirement ->
            requirement to requirement.validator(value)
        }
    }

    val isValid by remember(requirementStates) {
        derivedStateOf { requirementStates.all { it.second } }
    }

    // Notify about validation state change
    LaunchedEffect(isValid) {
        onValidationChange?.invoke(isValid)
    }

    Column(modifier = modifier) {
        Box {
            InputView(
                value = value,
                onValueChange = onValueChange,
                label = label,
                placeholder = placeholder,
                isOptional = isOptional,
                enabled = enabled,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                isError = !isValid && value.isNotEmpty(),
                visualTransformation = if (isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    Icon(
                        imageVector =
                            if (isPasswordVisible) {
                                ImageVector.vectorResource(
                                    R.drawable.ic_visibility
                                )
                            } else {
                                ImageVector.vectorResource(
                                    R.drawable.ic_visibility_off
                                )
                            },
                        contentDescription = if (isPasswordVisible) {
                            stringResource(R.string.hide_password)
                        } else {
                            stringResource(R.string.show_password)
                        },
                        modifier = Modifier
                            .size(Dimens.icon.l)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                isPasswordVisible = !isPasswordVisible
                            },
                        tint = colors.content.onNeutralXxHigh
                    )
                },
                minHeight = minHeight,
                placeholderTextColor = placeholderTextColor,
                textColor = textColor,
                errorBorderColor = errorBorderColor,
                borderColor = borderColor,
                errorLabelColor = errorLabelColor,
                labelColor = labelColor,
                optionalIndicatorColor = optionalIndicatorColor,
                containerColor = containerColor
            )
        }

        // Password requirements
        AnimatedVisibility(
            visible = showRequirements && value.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.spacing.s),
                verticalArrangement = Arrangement.spacedBy(Dimens.spacing.xs)
            ) {
                requirementStates.forEach { (requirement, isMet) ->
                    PasswordRequirementItem(
                        text = requirement.label,
                        isMet = isMet,
                        requirementMetIndicator = requirementMetIndicator,
                        requirementNotMetIndicator = requirementNotMetIndicator,
                        requirementMetTextColor = requirementMetTextColor,
                        requirementNotMetTextColor = requirementNotMetTextColor
                    )
                }
            }
        }
    }
}

@Composable
private fun PasswordRequirementItem(
    text: String,
    isMet: Boolean,
    modifier: Modifier = Modifier,
    requirementMetIndicator: (@Composable () -> Unit),
    requirementNotMetIndicator: (@Composable () -> Unit),
    requirementMetTextColor: Color,
    requirementNotMetTextColor: Color
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing.xs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isMet){
            requirementMetIndicator()
        }
        else{
            requirementNotMetIndicator()
        }

        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = if (isMet) requirementMetTextColor else requirementNotMetTextColor
        )
    }
}

@Composable
fun DefaultRequirementMetIndicator() {
    Box(
        modifier = Modifier
            .size(Dimens.icon.m)
            .clip(CircleShape)
            .background(colors.surface.brand),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = stringResource(R.string.requirement_met),
            modifier = Modifier.size(Dimens.icon.s),
            tint = colors.surface.xLow
        )
    }
}

@Composable
fun DefaultRequirementNotMetIndicator() {
    Box(
        modifier = Modifier
            .size(Dimens.icon.m)
            .clip(CircleShape)
            .background(colors.surface.danger),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(R.string.requirement_not_met),
            modifier = Modifier.size(Dimens.icon.s),
            tint = colors.surface.xLow
        )
    }
}

@Composable
fun defaultPasswordRequirements(): List<PasswordRequirement> = listOf(
    PasswordRequirement(
        label = stringResource(R.string.password_requirement_length),
        validator = { it.length >= 8 }
    ),
    PasswordRequirement(
        label = stringResource(R.string.password_requirement_uppercase),
        validator = { it.any { char -> char.isUpperCase() } }
    ),
    PasswordRequirement(
        label = stringResource(R.string.password_requirement_number),
        validator = { it.any { char -> char.isDigit() } }
    ),
    PasswordRequirement(
        label = stringResource(R.string.password_requirement_special),
        validator = { password ->
            password.any { !it.isLetterOrDigit() }
        }
    )
)

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PasswordInputPreview() {
    InputComponentsTheme {
        var password by remember { mutableStateOf("") }

        Column(
            modifier = Modifier.padding(Dimens.spacing.m),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing.l)
        ) {
            // Empty state
            PasswordInput(
                value = "",
                onValueChange = {},
                label = "Password",
                placeholder = "Enter your password"
            )

            // Partially filled
            PasswordInput(
                value = "Test123",
                onValueChange = {},
                label = "Password"
            )

            // Valid password
            PasswordInput(
                value = "Test123#",
                onValueChange = {},
                label = "Password",
                onValidationChange = { isValid ->
                    // Handle validation state
                }
            )

            // Interactive example
            PasswordInput(
                value = password,
                onValueChange = { password = it },
                label = "Create Password",
                isOptional = false
            )
        }
    }
}