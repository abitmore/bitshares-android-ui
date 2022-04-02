package graphene.protocol

import bitshareskit.extensions.EMPTY_SPACE
import graphene.chain.K100_NullObject
import graphene.chain.K101_BaseObject
import graphene.chain.K102_AccountObject

val INVALID_NULL_OBJECT = K100_NullObject(
    emptyIdType()
)

val INVALID_BASE_OBJECT = K101_BaseObject(
    emptyIdType()
)

val INVALID_ACCOUNT_OBJECT = K102_AccountObject(
    emptyIdType(), //    val id: K102_AccountIdType,
    ChainTimePoint.DEFAULT_EXPIRATION_TIME, //    val membershipExpirationDate: ChainTimePoint,
    //
    emptyIdType(), //    val registrar: K102_AccountType,
    emptyIdType(), //    val referrer: K102_AccountType,
    emptyIdType(), //    val lifetimeReferrer: K102_AccountType,
    //
    uint16_t.MAX_VALUE, //    val networkFeePercentage: UInt16,
    uint16_t.MAX_VALUE, //    val lifetimeReferrerFeePercentage: UInt16,
    uint16_t.MAX_VALUE, //    val referrerRewardsFeePercentage: UInt16,
    //
    EMPTY_SPACE, //    val name: String,
    //
    Authority.INVALID, //    val owner: Authority,
    Authority.INVALID, //    val active: Authority,
    AccountOptions.INVALID, //    val options: AccountOptions,
    //
    uint16_t.MAX_VALUE, //    val numCommitteeVoted: UInt16,
    emptyIdType(), //    val statistics: K206_AccountStatisticsType,
    //
    sortedSetOf(), //    val whiteListingAccounts: TypeSet<K102_AccountType>,
    sortedSetOf(), //    val blackListingAccounts: TypeSet<K102_AccountType>,
    emptySet(), //    val whiteListedAccounts: Set<K102_AccountType>,
    emptySet(), //    val blackListedAccounts: Set<K102_AccountType>,
    //
    emptyIdType(), //    val ack_vb: K113_VestingBalanceIdType = emptyIdType(),
    //
    INVALID_TYPED_SPECIAL_AUTHORITY, //    val ownerSpecialAuthority: SpecialAuthority,
    INVALID_TYPED_SPECIAL_AUTHORITY, //    val activeSpecialAuthority: SpecialAuthority,
    uint8_t.MAX_VALUE, //    val topNControlFlags: UInt8,
)



//val INVALID_ASSET_OBJECT = K103_AssetObject(
//
//    emptyIdType(), // val id: K103_AssetIdType,
//    EMPTY_SPACE, // val symbol: String,
//    emptyIdType(), // val issuer: K102_AccountType,
//    0U, // val precision: UInt8 = 0U,
//// val options: AssetOptions,
//
//// val dynamicData: K203_AssetDynamicType,
//// val bitassetData: K204_AssetBitassetType = emptyIdType(),
//// val buybackAccount: K102_AccountType = emptyIdType(),
//// val liquidityPool: K119_LiquidityPoolType = emptyIdType(),
//)