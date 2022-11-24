# Twakto - Simple App using Modern Android Architecture (Kotlin)

Twakto is a sample project that presents a modern approach to Android app development.

The project tries to combine popular Android tools and to demonstrate best development practices by utilizing up to date tech-stack like Compose, Kotlin Flow and Hilt.

The sample app layers its presentation through MVVM presentation pattern.
## Description

* UI 
   * [Compose](https://developer.android.com/jetpack/compose) declarative UI framework

* Tech/Tools
    * [Kotlin](https://kotlinlang.org/) 100% coverage
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) and [Flow](https://developer.android.com/kotlin/flow) for async operations
    * [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection
    * [Jetpack](https://developer.android.com/jetpack)
        * [Compose](https://developer.android.com/jetpack/compose) 
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that stores, exposes and manages UI state
    * [Retrofit](https://square.github.io/retrofit/) for networking
    * [Coil](https://github.com/coil-kt/coil) for image loading
    
* Modern Architecture
    * MVVM for presentation layer
    * [Android Architecture components](https://developer.android.com/topic/libraries/architecture) ([ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel), 
    * [Android KTX](https://developer.android.com/kotlin/ktx) - Jetpack Kotlin extensions

## Presentation patterns layers
* View - Composable screens that consume state.
* ViewModel - [AAC ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that manages and set the state of the corresponding screen. Additionally, it intercepts UI events as callbacks and produces side-effects. The ViewModel is scoped to the lifetime of the corresponding screen composable in the backstack.
* Model - Data source classes that retrieve content. 
