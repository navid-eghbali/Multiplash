package navid.multiplash.shared.di

import navid.multiplash.core.api.di.CoreApiComponent
import navid.multiplash.core.async.di.CoreAsyncComponent

expect interface SharedPlatformApplicationComponent

interface SharedApplicationComponent :
    SharedPlatformApplicationComponent,
    CoreAsyncComponent,
    CoreApiComponent
