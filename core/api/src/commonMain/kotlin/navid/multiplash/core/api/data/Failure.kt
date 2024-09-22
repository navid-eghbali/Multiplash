package navid.multiplash.core.api.data

import kotlinx.serialization.Serializable

@Serializable
data class Failure(val errors: List<String>) {

    override fun toString(): String = errors.joinToString("\n")
}
