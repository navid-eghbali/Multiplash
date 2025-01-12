package navid.multiplash.feature.user.data.remote

internal interface UserClient {
    suspend fun getUser(username: String): Result<>
}
