package graphene.chain

import graphene.protocol.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class K204_AssetBitassetDataObject(
    @SerialName("id")
    override val id: K204_AssetBitassetDataIdType,
    // The asset this object belong to
    @SerialName("asset_id")
    val assetId: K103_AssetIdType,
    // The tunable options for BitAssets are stored in this field.
    @SerialName("options")
    val options: BitassetOptions,
    // Feeds published for this asset.
    // The keys in this map are the feed publishing accounts.
    // The timestamp on each feed is the time it was published.
    @SerialName("feeds")
    val feeds: FlatMap<K102_AccountIdType, PairArray<ChainTimePoint, PriceFeedWithIcr>>,
    // This is the median of values from the currently active feeds.
    @SerialName("median_feed")
    val medianFeed: PriceFeedWithIcr,
    // This is the currently active price feed, calculated from @ref median_feed and other parameters.
    @SerialName("current_feed")
    val currentFeed: PriceFeedWithIcr,
    // This is the publication time of the oldest feed which was factored into current_feed.
    @SerialName("current_feed_publication_time")
    val currentFeedPublicationTime: ChainTimePoint,
    // Call orders with collateralization (aka collateral/debt) not greater than this value are in margin
    // call territory.
    // This value is derived from @ref current_feed for better performance and should be kept consistent.
    @SerialName("current_maintenance_collateralization")
    val currentMaintenanceCollateralization: PriceType,
    // After BSIP77, when creating a new debt position or updating an existing position, the position
    // will be checked against the `initial_collateral_ratio` (ICR) parameter in the bitasset options.
    // This value is derived from @ref current_feed (which includes `ICR`) for better performance and
    // should be kept consistent.
    @SerialName("current_initial_collateralization")
    val currentInitialCollateralization: PriceType,
    // True if this asset implements a @ref prediction_market
    @SerialName("is_prediction_market")
    val isPredictionMarket: Boolean = false,
    // This is the volume of this asset which has been force-settled this maintanence interval
    @SerialName("force_settled_volume")
    val forceSettledVolume: share_type,
    // Price at which force settlements of a globally settled asset will occur
    @SerialName("settlement_price")
    val settlementPrice: PriceType,
    // Amount of collateral which is available for force settlement due to global settlement
    @SerialName("settlement_fund")
    val settlementFund: share_type,
    // The individual settlement pool.
    // In the event of individual settlements to fund, debt and collateral of the margin positions which got
    // settled are moved here.

    // Amount of debt due to individual settlements
    @SerialName("individual_settlement_debt")
    val individualSettlementDebt: share_type,
    // Amount of collateral which is available for force settlement due to individual settlements
    @SerialName("individual_settlement_fund")
    val individualSettlementFund: share_type,
    @SerialName("asset_cer_updated")
    // Track whether core_exchange_rate in corresponding @ref asset_object has updated
    val asset_cer_updated: Boolean = false,
    @SerialName("feed_cer_updated")
    // Track whether core exchange rate in current feed has updated
    val feed_cer_updated: Boolean = false,
) : AbstractObject(), K204_AssetBitassetDataType {
//    // @return whether @ref current_feed is different from @ref median_feed
////    bool is_current_feed_price_capped()const
////    { return ( median_feed.settlement_price != current_feed.settlement_price ); }
//
//
//    // Calculate the maximum force settlement volume per maintenance interval, given the current share supply
//    share_type max_force_settlement_volume(share_type current_supply)const;
//
//    // @return true if the bitasset has been globally settled, false otherwise
//    bool has_settlement()const { return !settlement_price.is_null(); }
//
//    /**
//     *  In the event of global settlement, all margin positions
//     *  are settled with the siezed collateral being moved into the settlement fund. From this
//     *  point on forced settlement occurs immediately when requested, using the settlement price and fund.
//     */
//
//
//    // @return true if the individual settlement pool is not empty, false otherwise
//    bool has_individual_settlement()const { return ( individual_settlement_debt != 0 ); }
//
//    // Get the price of the individual settlement pool
//    price get_individual_settlement_price() const
//    {
//        return asset( individual_settlement_debt, asset_id )
//        / asset( individual_settlement_fund, options.short_backing_asset );
//    }
//
//    // Get the effective black swan response method of this bitasset
//    bitasset_options::black_swan_response_type get_black_swan_response_method() const
//    {
//        return options.get_black_swan_response_method();
//    }
//
//    // Get margin call order price (MCOP) of this bitasset
//    price get_margin_call_order_price() const
//    {
//        return current_feed.margin_call_order_price( options.extensions.value.margin_call_fee_ratio );
//    }
//
//    // Get margin call order ratio (MCOR) of this bitasset
//    ratio_type get_margin_call_order_ratio() const
//    {
//        return current_feed.margin_call_order_ratio( options.extensions.value.margin_call_fee_ratio );
//    }
//
//    // Get margin call pays ratio (MCPR) of this bitasset
//    ratio_type get_margin_call_pays_ratio() const
//    {
//        return current_feed.margin_call_pays_ratio( options.extensions.value.margin_call_fee_ratio );
//    }
//
//    // Whether need to update core_exchange_rate in @ref asset_object
//    bool need_to_update_cer() const
//    {
//        return ( ( feed_cer_updated || asset_cer_updated ) && !current_feed.core_exchange_rate.is_null() );
//    }
//
//    // The time when @ref current_feed would expire
//    time_point_sec feed_expiration_time()const
//    {
//        uint32_t current_feed_seconds = current_feed_publication_time.sec_since_epoch();
//        if( (std::numeric_limits<uint32_t>::max() - current_feed_seconds) <= options.feed_lifetime_sec )
//            return time_point_sec::maximum();
//        else
//            return current_feed_publication_time + options.feed_lifetime_sec;
//    }
//    // The old and buggy implementation of @ref feed_is_expired before the No. 615 hardfork.
//    // See https://github.com/cryptonomex/graphene/issues/615
//    bool feed_is_expired_before_hf_615(time_point_sec current_time)const
//    { return feed_expiration_time() >= current_time; }
//    // @return whether @ref current_feed has expired
//    bool feed_is_expired(time_point_sec current_time)const
//    { return feed_expiration_time() <= current_time; }
//
//    /******
//     * @brief calculate the median feed
//     *
//     * This calculates the median feed from @ref feeds, feed_lifetime_sec
//     * in @ref options, and the given parameters.
//     * It may update the @ref median_feed, @ref current_feed_publication_time,
//     * @ref current_initial_collateralization and
//     * @ref current_maintenance_collateralization member variables.
//     *
//     * @param current_time the current time to use in the calculations
//     * @param next_maintenance_time the next chain maintenance time
//     *
//     * @note Called by @ref database::update_bitasset_current_feed() which updates @ref current_feed afterwards.
//     */
//    void update_median_feeds(time_point_sec current_time, time_point_sec next_maintenance_time);

}
