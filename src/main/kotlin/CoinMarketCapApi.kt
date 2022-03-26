import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import java.io.FileInputStream
import java.util.Properties

object CoinMarketCapApi {

    private val PRODUCTION_API_KEY: String = Properties().let { prop ->
        FileInputStream("src/main/resources/local.properties").use { prop.load(it) }
//        FileInputStream("local.properties").use { prop.load(it) }
        prop.getProperty("apiKey")
    }
    private const val PRODUCTION_BASE_URL = "https://pro-api.coinmarketcap.com/v1"

    private const val TEST_API_KEY = "b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c"
    private const val TEST_BASE_URL = "https://sandbox-api.coinmarketcap.com/v1"

    private const val IS_TEST = true

    private fun apiKey(): String = if (IS_TEST) TEST_API_KEY else PRODUCTION_API_KEY
    private fun baseUrl(): String = if (IS_TEST) TEST_BASE_URL else PRODUCTION_BASE_URL

    private val httpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun latestListings(): CmcCryptoResponse {
        val response: CmcCryptoResponse = httpClient.get(baseUrl() + "/cryptocurrency/listings/latest") {
            contentType(ContentType.Application.Json)
            header("Accept", "application/json")
            header("X-CMC_PRO_API_KEY", apiKey())
        }
        println("response=$response")
        return response


//        val request = HttpRequest.newBuilder()
//            .uri(URI.create(baseUrl() + "/cryptocurrency/listings/latest"))
//            .header("Accept", "application/json")
//            .header("X-CMC_PRO_API_KEY", apiKey())
//            .build()
//        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
//        return response.body()
    }

}