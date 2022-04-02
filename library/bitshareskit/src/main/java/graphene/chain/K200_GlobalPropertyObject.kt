package graphene.chain

import graphene.protocol.*
import graphene.protocol.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class K200_GlobalPropertyObject(
    @SerialName("id")
    override val id: K200_GlobalPropertyIdType,
    @SerialName("parameters")
    val parameters: ChainParameters,
    @SerialName("pending_parameters")
    val pendingParameters: Optional<ChainParameters> = optional(),
    @SerialName("next_available_vote_id")
    val nextAvailableVoteId: uint32_t, // = 0
    @SerialName("active_committee_members")
    val activeCommitteeMembers: List<K105_CommitteeMemberType>, // updated once per maintenance interval
    @SerialName("active_witnesses")
    val activeWitnesses: FlatSet<K106_WitnessType>, // updated once per maintenance interval // TODO
    // n.b. witness scheduling is done by witness_schedule object
) : AbstractObject(), K200_GlobalPropertyType

