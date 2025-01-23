package navid.multiplash.feature.user.di

import navid.multiplash.feature.user.data.remote.UserClient
import navid.multiplash.feature.user.data.remote.UserClientImpl
import navid.multiplash.feature.user.data.repository.UserRepository
import navid.multiplash.feature.user.data.repository.UserRepositoryImpl
import navid.multiplash.feature.user.ui.UserScreen
import navid.multiplash.feature.user.ui.UserViewModel
import navid.multiplash.feature.user.usecase.GetUserPhotosUseCase
import navid.multiplash.feature.user.usecase.GetUserPhotosUseCaseImpl
import navid.multiplash.feature.user.usecase.GetUserStatisticsUseCase
import navid.multiplash.feature.user.usecase.GetUserStatisticsUseCaseImpl
import navid.multiplash.feature.user.usecase.GetUserUseCase
import navid.multiplash.feature.user.usecase.GetUserUseCaseImpl
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val userModule = DI.Module(name = "UserModule") {

    bindSingleton<UserClient> {
        UserClientImpl(
            httpClient = instance(),
        )
    }

    bindSingleton<UserRepository> {
        UserRepositoryImpl(
            userClient = instance(),
        )
    }

    bindSingleton<GetUserPhotosUseCase> {
        GetUserPhotosUseCaseImpl(
            userRepository = instance(),
        )
    }

    bindProvider<GetUserStatisticsUseCase> {
        GetUserStatisticsUseCaseImpl(
            userClient = instance(),
        )
    }

    bindProvider<GetUserUseCase> {
        GetUserUseCaseImpl(
            userClient = instance(),
        )
    }

    bindFactory { args: UserScreen ->
        UserViewModel(
            args = args,
            getUserPhotosUseCase = instance(),
            getUserUseCase = instance(),
            getUserStatisticsUseCase = instance(),
        )
    }

}
