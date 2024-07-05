package navid.multiplash.core.api.data

import kotlinx.serialization.Serializable

@Serializable
data class Failure(val errors: List<String>)
