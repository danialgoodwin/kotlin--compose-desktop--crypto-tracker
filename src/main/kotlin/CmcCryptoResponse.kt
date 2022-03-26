import kotlinx.datetime.Instant
import kotlinx.serialization.SerialInfo
import kotlinx.serialization.SerialName

// Reference: https://coinmarketcap.com/api/documentation/v1/#operation/getV1CryptocurrencyListingsLatest
@kotlinx.serialization.Serializable
data class CmcCryptoResponse(
    val status: Status,
    val data: List<Data>
) {

    fun isSuccess() = status.errorCode == 0

    @kotlinx.serialization.Serializable
    data class Status(
        val timestamp: Instant,
        @SerialName("error_code")
        val errorCode: Int,
        @SerialName("error_message")
        val errorMessage: String?,
        val elapsed: Int,
        @SerialName("credit_count")
        val creditCount: Int,
        val notice: String?,
        @SerialName("total_count")
        val totalCount: Int? // Not officially documented, 2022-03-26
    )

    @kotlinx.serialization.Serializable
    data class Data(
        /** The unique CoinMarketCap ID for this cryptocurrency. */
        val id: Int,
        /** The name of this cryptocurrency. */
        val name: String,
        /** The ticker symbol for this cryptocurrency. */
        val symbol: String,
        /** The web URL friendly shorthand version of this cryptocurrency name. */
        val slug: String,
        @SerialName("cmc_rank")
        val cmcRank: Int,
        @SerialName("self_reported_circulating_supply")
        val selfReportedCirculatingSupply: Double?, // Not officially documented, 2022-03-26
        @SerialName("self_reported_market_cap")
        val selfReportedMarketCap: Double?, // Not officially documented, 2022-03-26
        @SerialName("num_market_pairs")
        val numMarketPairs: Int,
        @SerialName("circulating_supply")
        val circulatingSupply: Double,
        @SerialName("total_supply")
        val totalSupply: Double,
        @SerialName("market_cap_by_total_supply")
        val marketCapByTotalSupply: Double? = null,
        @SerialName("max_supply")
        val maxSupply: Double?,
        @SerialName("last_updated")
        val lastUpdated: Instant,
        @SerialName("date_added")
        val dateAdded: Instant,
        val tags: List<String>,
        val platform: Platform?,
        val quote: Map<String, Quote>
    )

    /** Metadata about the parent cryptocurrency platform this cryptocurrency belongs to if it is a token, otherwise null. */
    @kotlinx.serialization.Serializable
    data class Platform(
        val id: Int,
        val name: String,
        val symbol: String,
        /** The web URL friendly shorthand version of the parent platform cryptocurrency name. */
        val slug: String,
        /** The token address on the parent platform cryptocurrency. */
        @SerialName("token_address")
        val tokenAddress: String
    )

    @kotlinx.serialization.Serializable
    data class Quote(
        val price: Double,

        @SerialName("volume_24h")
        val volume24h: Double,
        @SerialName("volume_change_24h")
        val volumeChange24h: Double?,
        /** Rolling 24-hour reported volume in the specified currency. This field is only returned if requested through
         * the 'aux' request parameter. */
        @SerialName("volume_24h_reported")
        val volume24hReported: Double? = null,
        /** Rolling 7-day adjusted volume in the specified currency. This field is only returned if requested through
         * the 'aux' request parameter. */
        @SerialName("volume_7d")
        val volume7d: Double? = null,
        /** Rolling 7-day reported volume in the specified currency. This field is only returned if requested through
         * the 'aux' request parameter. */
        @SerialName("volume_7d_reported")
        val volume7dReported: Double? = null,
        @SerialName("volume_30d")
        val volume30d: Double? = null,
        @SerialName("volume_30d_reported")
        val volume30dReported: Double? = null,

        @SerialName("percent_change_1h")
        val percentChange1h: Double?,
        @SerialName("percent_change_24h")
        val percentChange24h: Double?,
        @SerialName("percent_change_7d")
        val percentChange7d: Double?,
        @SerialName("percent_change_30d")
        val percentChange30d: Double?,
        @SerialName("percent_change_60d")
        val percentChange60d: Double?, // Not officially documented, 2022-03-26
        @SerialName("percent_change_90d")
        val percentChange90d: Double?, // Not officially documented, 2022-03-26

        @SerialName("market_cap")
        val marketCap: Double,
        @SerialName("market_cap_dominance")
        val marketCapDominance: Double,
        @SerialName("fully_diluted_market_cap")
        val fullyDilutedMarketCap: Double,
        @SerialName("last_updated")
        val lastUpdated: Instant
    )

}
