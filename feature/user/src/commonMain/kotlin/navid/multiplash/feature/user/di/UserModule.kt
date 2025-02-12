package navid.multiplash.feature.user.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import navid.multiplash.core.async.CoroutineDispatchers
import navid.multiplash.core.db.DatabaseBuilderFactory
import navid.multiplash.core.db.KeyValueDatabase
import navid.multiplash.feature.user.data.remote.UserClient
import navid.multiplash.feature.user.data.remote.UserClientImpl
import navid.multiplash.feature.user.data.repository.UserRepository
import navid.multiplash.feature.user.data.repository.UserRepositoryImpl
import navid.multiplash.feature.user.ui.UserScreen
import navid.multiplash.feature.user.ui.UserViewModel
import navid.multiplash.feature.user.usecase.FavoriteUserUseCase
import navid.multiplash.feature.user.usecase.FavoriteUserUseCaseImpl
import navid.multiplash.feature.user.usecase.GetUserPhotosUseCase
import navid.multiplash.feature.user.usecase.GetUserPhotosUseCaseImpl
import navid.multiplash.feature.user.usecase.GetUserStatisticsUseCase
import navid.multiplash.feature.user.usecase.GetUserStatisticsUseCaseImpl
import navid.multiplash.feature.user.usecase.GetUserUseCase
import navid.multiplash.feature.user.usecase.GetUserUseCaseImpl
import navid.multiplash.feature.user.usecase.IsUserFavoritedUseCase
import navid.multiplash.feature.user.usecase.IsUserFavoritedUseCaseImpl
import navid.multiplash.feature.user.usecase.UnfavoriteUserUseCase
import navid.multiplash.feature.user.usecase.UnfavoriteUserUseCaseImpl
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

private const val FAVORITES_DB_TAG = "FavoritesDb"

val userModule = DI.Module(name = "UserModule") {

    bindSingleton<KeyValueDatabase>(tag = FAVORITES_DB_TAG) {
        val dispatchers: CoroutineDispatchers = instance()
        val builder: RoomDatabase.Builder<KeyValueDatabase> = instance<DatabaseBuilderFactory>().create("favorites.db")
        builder
            .fallbackToDestructiveMigrationOnDowngrade(true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(dispatchers.io)
            .build()
    }

    bindSingleton<UserClient> {
        UserClientImpl(
            httpClient = instance(),
        )
    }

    bindSingleton<UserRepository> {
        UserRepositoryImpl(
            userClient = instance(),
            keyValueDao = instance<KeyValueDatabase>(tag = FAVORITES_DB_TAG).keyValueDao(),
        )
    }

    bindProvider<FavoriteUserUseCase> {
        FavoriteUserUseCaseImpl(
            repository = instance(),
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

    bindProvider<IsUserFavoritedUseCase> {
        IsUserFavoritedUseCaseImpl(
            repository = instance(),
        )
    }

    bindProvider<UnfavoriteUserUseCase> {
        UnfavoriteUserUseCaseImpl(
            repository = instance(),
        )
    }

    bindFactory { args: UserScreen ->
        UserViewModel(
            args = args,
            isUserFavoritedUseCase = instance(),
            getUserPhotosUseCase = instance(),
            getUserUseCase = instance(),
            getUserStatisticsUseCase = instance(),
            favoriteUserUseCase = instance(),
            unfavoriteUserUseCase = instance(),
        )
    }

}
