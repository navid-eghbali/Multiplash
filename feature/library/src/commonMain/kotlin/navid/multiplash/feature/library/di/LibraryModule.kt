package navid.multiplash.feature.library.di

import navid.multiplash.core.db.KeyValueDatabase
import navid.multiplash.core.di.Tags
import navid.multiplash.feature.library.data.repository.LibraryRepository
import navid.multiplash.feature.library.data.repository.LibraryRepositoryImpl
import navid.multiplash.feature.library.ui.LibraryViewModel
import navid.multiplash.feature.library.usecase.GetBookmarksUseCase
import navid.multiplash.feature.library.usecase.GetBookmarksUseCaseImpl
import navid.multiplash.feature.library.usecase.GetFavoriteUsersUseCase
import navid.multiplash.feature.library.usecase.GetFavoriteUsersUseCaseImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val libraryModule = DI.Module(name = "LibraryModule") {

    bindSingleton<LibraryRepository> {
        LibraryRepositoryImpl(
            bookmarksDao = instance<KeyValueDatabase>(tag = Tags.BOOKMARKS_DB).keyValueDao(),
            favoritesDao = instance<KeyValueDatabase>(tag = Tags.FAVORITES_DB).keyValueDao(),
        )
    }

    bindProvider<GetBookmarksUseCase> {
        GetBookmarksUseCaseImpl(
            repository = instance(),
        )
    }

    bindProvider<GetFavoriteUsersUseCase> {
        GetFavoriteUsersUseCaseImpl(
            repository = instance(),
        )
    }

    bindProvider {
        LibraryViewModel(
            getBookmarksUseCase = instance(),
            getFavoriteUsersUseCase = instance(),
        )
    }
}
