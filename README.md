Multiplash is a **work-in-progress** Kotlin Multiplatform app, which uses Unsplash API to show high-quality photos.
Multiplash is supposed to have an Android, iOS, and Desktop version.
Multiplash attempts to use the latest libraries and tools. As a summary:

 * Entirely written in [Kotlin](https://kotlinlang.org/).
 * UI completely written in [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/).
 * Uses [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html).
 * Uses many of the [Architecture Components](https://developer.android.com/topic/libraries/architecture/).

## Development setup

### API keys

You need to provide a client key for the Unsplash API service:

- [Unsplash](https://unsplash.com/developers)

You can easily create a demo app via the link above and copy the `Access Key`.
Once you obtain the key, set it in your `~/api.properties`:
```
UNSPLASH_ACCESS_KEY=<insert>
```
