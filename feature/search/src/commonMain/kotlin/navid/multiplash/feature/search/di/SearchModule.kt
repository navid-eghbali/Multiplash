package navid.multiplash.feature.search.di

import navid.multiplash.feature.search.data.remote.SearchClient
import navid.multiplash.feature.search.data.remote.SearchClientImpl
import navid.multiplash.feature.search.data.repository.SearchRepository
import navid.multiplash.feature.search.data.repository.SearchRepositoryImpl
import navid.multiplash.feature.search.ui.SearchViewModel
import navid.multiplash.feature.search.usecase.GetTopicsUseCase
import navid.multiplash.feature.search.usecase.GetTopicsUseCaseImpl
import navid.multiplash.feature.search.usecase.SearchPhotosUseCase
import navid.multiplash.feature.search.usecase.SearchPhotosUseCaseImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val searchModule = DI.Module(name = "SearchModule") {

    bindSingleton<SearchClient> {
        SearchClientImpl(
            httpClient = instance(),
        )
    }

    bindSingleton<SearchRepository> {
        SearchRepositoryImpl(
            searchClient = instance(),
        )
    }

    bindProvider<GetTopicsUseCase> {
        GetTopicsUseCaseImpl(
            searchClient = instance(),
        )
    }

    bindSingleton<SearchPhotosUseCase> {
        SearchPhotosUseCaseImpl(
            searchRepository = instance(),
        )
    }

    bindProvider {
        SearchViewModel(
            getTopicsUseCase = instance(),
            searchPhotosUseCase = instance(),
        )
    }
}
