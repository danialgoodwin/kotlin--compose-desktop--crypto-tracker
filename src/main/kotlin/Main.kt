import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {
    val mainViewModel = MainViewModel()

    LaunchedEffect(true) {
        mainViewModel.loadCryptoListings()
    }

    var symbolTextInput by remember { mutableStateOf("BCT") }
    val userSymbols = mutableStateListOf("BCT", "ETH")
//    var symbols by remember { mutableStateListOf("BCT", "ETH") }

    val allSymbols = mutableStateListOf<CmcCryptoResponse.Data>()

    GlobalScope.launch(Dispatchers.IO) {
        mainViewModel.cryptoResponse.collect { response ->
            if (response?.isSuccess() == true) {
                allSymbols.clear()
                allSymbols.addAll(response.data)
            } else {
//                allSymbols.value = null
            }
        }
    }

    MaterialTheme {
        Column {
            Text("Please input a crypto symbol, then click 'Submit'")
            OutlinedTextField(symbolTextInput,
                label = { Text("Symbol") },
                placeholder = { Text("placeholder") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
                onValueChange = { symbolTextInput = it }
            )
            Button(onClick = {
                userSymbols.add(symbolTextInput)
            }) {
                Text("Submit")
            }

            Text("Favorites")
            LazyColumn() {
                itemsIndexed(userSymbols) { i, item ->
                    Text(item)
                }
            }

            Text("")
            Text("All")
            LazyColumn() {
                itemsIndexed(allSymbols) { i, item ->
                    val quoteUsd = item.quote["USD"] ?: return@itemsIndexed
                    Text("${item.cmcRank}: ${item.name} (${item.id}) ${quoteUsd.price} ${quoteUsd.percentChange1h}%1H ${quoteUsd.percentChange24h}%24H ${quoteUsd.percentChange7d}%7D \n")
                }
            }

        }
    }

}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
