package navid.multiplash.feature.topic.di

import navid.multiplash.feature.topic.data.remote.TopicClient
import navid.multiplash.feature.topic.data.remote.TopicClientImpl
import navid.multiplash.feature.topic.data.repository.TopicRepository
import navid.multiplash.feature.topic.data.repository.TopicRepositoryImpl
import navid.multiplash.feature.topic.ui.TopicScreen
import navid.multiplash.feature.topic.ui.TopicViewModel
import navid.multiplash.feature.topic.usecase.GetTopicPhotosUseCase
import navid.multiplash.feature.topic.usecase.GetTopicPhotosUseCaseImpl
import navid.multiplash.feature.topic.usecase.GetTopicUseCase
import navid.multiplash.feature.topic.usecase.GetTopicUseCaseImpl
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val topicModule = DI.Module(name = "TopicModule") {

    bindSingleton<TopicClient> {
        TopicClientImpl(
            httpClient = instance(),
        )
    }

    bindSingleton<TopicRepository> {
        TopicRepositoryImpl(
            topicClient = instance(),
        )
    }

    bindSingleton<GetTopicPhotosUseCase> {
        GetTopicPhotosUseCaseImpl(
            topicRepository = instance(),
        )
    }

    bindProvider<GetTopicUseCase> {
        GetTopicUseCaseImpl(
            topicClient = instance(),
        )
    }

    bindFactory { args: TopicScreen ->
        TopicViewModel(
            args = args,
            getTopicUseCase = instance(),
            getTopicPhotosUseCase = instance(),
        )
    }
}
