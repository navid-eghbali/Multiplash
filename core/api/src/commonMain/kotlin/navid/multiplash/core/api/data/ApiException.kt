package navid.multiplash.core.api.data

data class ApiException(
    override val message: String
) : Exception()
