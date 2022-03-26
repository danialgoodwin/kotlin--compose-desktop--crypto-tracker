import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking

class MainViewModel {

    val cryptoResponse = MutableStateFlow<CmcCryptoResponse?>(null)

    fun loadCryptoListings() {
        runBlocking(Dispatchers.IO) {
            val listings = CoinMarketCapApi.latestListings()
            cryptoResponse.value = listings
        }
    }

}
