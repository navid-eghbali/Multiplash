package navid.multiplash.feature.topic.di

import navid.multiplash.feature.topic.data.remote.TopicClient
import navid.multiplash.feature.topic.data.remote.TopicClientImpl
import navid.multiplash.feature.topic.ui.TopicScreen
import navid.multiplash.feature.topic.ui.TopicViewModel
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

    bindProvider<GetTopicUseCase> {
        GetTopicUseCaseImpl(
            topicClient = instance(),
        )
    }

    bindFactory { args: TopicScreen ->
        TopicViewModel(
            args = args,
            getTopicUseCase = instance(),
        )
    }
}
