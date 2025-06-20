package nicolas.orsag.inputcomponents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nicolas.orsag.inputcomponents.ui.components.InputView
import nicolas.orsag.inputcomponents.ui.components.PasswordInput
import nicolas.orsag.inputcomponents.ui.theme.Dimens
import nicolas.orsag.inputcomponents.ui.theme.InputComponentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InputComponentsTheme {
                var name by remember { mutableStateOf("") }

                var password by remember { mutableStateOf("") }
                var isPasswordValid by remember { mutableStateOf(false) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(Modifier.padding(innerPadding).padding(Dimens.spacing.m)) {

                        InputView(
                            value = name,
                            onValueChange = { name = it },
                            placeholder = "Enter your name",
                            label = "Name",
                            isOptional = true
                        )

                        Spacer(Modifier.height(Dimens.spacing.m))

                        PasswordInput(
                            value = password,
                            onValueChange = { password = it },
                            label = "Password",
                            onValidationChange = { isValid ->
                                isPasswordValid = isValid
                            }
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InputComponentsTheme {
        Greeting("Android")
    }
}