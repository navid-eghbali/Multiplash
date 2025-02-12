package navid.multiplash.feature.details.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import navid.multiplash.core.async.CoroutineDispatchers
import navid.multiplash.core.db.DatabaseBuilderFactory
import navid.multiplash.core.db.KeyValueDatabase
import navid.multiplash.feature.details.data.remote.DetailsClient
import navid.multiplash.feature.details.data.remote.DetailsClientImpl
import navid.multiplash.feature.details.data.repository.DetailsRepository
import navid.multiplash.feature.details.data.repository.DetailsRepositoryImpl
import navid.multiplash.feature.details.ui.DetailsScreen
import navid.multiplash.feature.details.ui.DetailsViewModel
import navid.multiplash.feature.details.usecase.BookmarkPhotoUseCase
import navid.multiplash.feature.details.usecase.BookmarkPhotoUseCaseImpl
import navid.multiplash.feature.details.usecase.DownloadPhotoUseCase
import navid.multiplash.feature.details.usecase.DownloadPhotoUseCaseImpl
import navid.multiplash.feature.details.usecase.GetPhotoUseCase
import navid.multiplash.feature.details.usecase.GetPhotoUseCaseImpl
import navid.multiplash.feature.details.usecase.IsPhotoBookmarkedUseCase
import navid.multiplash.feature.details.usecase.IsPhotoBookmarkedUseCaseImpl
import navid.multiplash.feature.details.usecase.UnbookmarkPhotoUseCase
import navid.multiplash.feature.details.usecase.UnbookmarkPhotoUseCaseImpl
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

private const val BOOKMARKS_DB_TAG = "BookmarksDb"

val detailsModule = DI.Module(name = "DetailsModule") {

    bindSingleton<KeyValueDatabase>(tag = BOOKMARKS_DB_TAG) {
        val dispatchers: CoroutineDispatchers = instance()
        val builder: RoomDatabase.Builder<KeyValueDatabase> = instance<DatabaseBuilderFactory>().create("bookmarks.db")
        builder
            .fallbackToDestructiveMigrationOnDowngrade(true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(dispatchers.io)
            .build()
    }

    bindSingleton<DetailsClient> {
        DetailsClientImpl(
            httpClient = instance(),
        )
    }

    bindSingleton<DetailsRepository> {
        DetailsRepositoryImpl(
            keyValueDao = instance<KeyValueDatabase>(tag = BOOKMARKS_DB_TAG).keyValueDao(),
        )
    }

    bindProvider<BookmarkPhotoUseCase> {
        BookmarkPhotoUseCaseImpl(
            repository = instance(),
        )
    }

    bindProvider<DownloadPhotoUseCase> {
        DownloadPhotoUseCaseImpl(
            detailsClient = instance(),
            saveToFileUseCase = instance(),
        )
    }

    bindProvider<GetPhotoUseCase> {
        GetPhotoUseCaseImpl(
            detailsClient = instance(),
        )
    }

    bindProvider<IsPhotoBookmarkedUseCase> {
        IsPhotoBookmarkedUseCaseImpl(
            repository = instance(),
        )
    }

    bindProvider<UnbookmarkPhotoUseCase> {
        UnbookmarkPhotoUseCaseImpl(
            repository = instance(),
        )
    }

    bindFactory { args: DetailsScreen ->
        DetailsViewModel(
            args = args,
            isPhotoBookmarkedUseCase = instance(),
            getPhotoUseCase = instance(),
            downloadPhotoUseCase = instance(),
            bookmarkPhotoUseCase = instance(),
            unbookmarkPhotoUseCase = instance(),
        )
    }
}
