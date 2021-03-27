<H3 align="center">
Github Stargazer.
</H3>

[![codecov](https://codecov.io/gh/c0de-wizard/stargazer/branch/main/graph/badge.svg)](https://codecov.io/gh/c0de-wizard/stargazer)

## Description
Stargazer is a simple android project that consumes Github's Api & loads a list of repositories from Square's organization.

### Screenshots


 <table>
  <td>
    <p align="center">
  <img src="https://github.com/c0de-wizard/github-stargazer/blob/main/art/LightTheme.png?raw=true" alt="Light Theme" width="250"/>
</p>
</td>
<td>
    <p align="center">
      <img src="https://github.com/c0de-wizard/github-stargazer/blob/main/art/DarkTheme.png?raw=true" alt="Dark Theme" width="250"/>
    </p>
  </td>

</tr>
</table>

## Requirements

* [Android Studio Arctic Fox](https://developer.android.com/studio/preview?authuser=1)
* JDK 11
* [Android SDK](https://developer.android.com/studio/index.html)
* Latest Android SDK Tools and build tools.

## Architecture

The architecture of the project follows the principles of Clean Architecture. This project demonstrates two Architecture implementations: **MVVM** & **MVI**. Most of the feature modules implement MVVM for now. **trending-repositories** on the other hand, implements the MVI pattern.

Here's how the sample project implements it:

### Core

This layer contains core classes shared between modules. It includes:
- Application dagger interfaces, Scope annotations
- Generic classes eg Interactor, which enforces domain interactor implementation when extended.
- Common extension classes

### Repository (Data Layer)
This layer is responsible for fetching data. It contains cache and remote implementation.

For this case, I choose to have it as a separate module since the api is not changing that much but
for a bigger application, I would ideally move this to the respective module. It's easier to manage changes that way.

### Features
This layer is where we create different feature modules. Browse Repository & Bookmark Repository. Structure breakdown:

- **Injection**: Each feature has it's own component with that injects the dependencies it needs. This allows each feature to have lean subcomponents
- **Domain**: This layer orchestrates the flow of data from Data Layer to the ui layer. It also has mappers that transform objects to the required type. eg Entity -> ViewModelData
- **UI**: This is responsible displaying data.

### Navigator
This module enables us to navigate between the app, as the name suggests. It contains the main navigation graph file and subgraphs each located in every feature module. The beauty of this is that feature modules don't need to depend on other feature modules

🚧 Jetpack Compose (Under Development) 🚧
I'm currently migrating each module to [Jetpack Compose](https://developer.android.com/jetpack/compose).

### Progress

- [x] Settings
- [x] Trending Repositories
- [ ] Browse (Implement search functionality)
- [ ] Repo Details
- [ ] Migrate Navigation to Navigation Compose
- [ ] Add Pagination (Maybe 😁).

## Code style

To maintain the style and quality of the code, are used the bellow static analysis tools. All of them use properly configuration and you find them in the project root directory `.{toolName}`.

| Tools | Check command | Fix command |
|-------|---------------|-------------|
| [detekt](https://github.com/arturbosch/detekt) | `./gradlew detekt` | - |
| [ktlint](https://github.com/pinterest/ktlint) | `./gradlew ktlint` | `./gradlew ktlintFormat` |
| [spotless](https://github.com/diffplug/spotless) | `./gradlew spotlessCheck` | `./gradlew spotlessApply`
| [lint](https://developer.android.com/studio/write/lint) | `./gradlew lint` | - |


## Libraries Used
*   [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started)
*   [JetPack](https://developer.android.com/jetpack)
    - [Jetpack Compose](https://developer.android.com/jetpack/compose)
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) UI related data holder, lifecycle aware.
    - [Room Persistence]() - construct a database using the abstract layer.
    - LiveData - notify domain layer data to views.
    - Lifecycle - dispose of observing data when lifecycle state changes.
    - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Replacement for shared preference.
*   [Material-Components](https://github.com/material-components/material-components-android) - Material design components.
*   [Dark/Light Theme](https://developer.android.com/guide/topics/ui/look-and-feel/darktheme) - Support dark/light themes
*   [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
*   [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
*   [Moshi](https://github.com/square/moshi) - Moshi is a modern JSON library for Android and Java
*   [Timber](https://github.com/JakeWharton/timber) - logging.
*   [Truth](https://github.com/google/truth) - Unit Testing
*   [Turbine](https://github.com/cashapp/turbine) - Testing coroutines
*   [Dagger Hilt](https://dagger.dev/hilt) - dependency injection.
*   [Kotlin Gradle DSL](https://guides.gradle.org/migrating-build-logic-from-groovy-to-kotlin)
*   [Jacoco](https://github.com/vanniktech/gradle-android-junit-jacoco-plugin) Test coverage

### TODO

- [x] Create base architecture.
- [x] Create base architecture.
- [x] Implement repository logic:
    - [x] Retrofit.
    - [x] Room.
- [x] Load and display repositories.
- [x] Bookmark repo.
- [x] Cleanup: Create common module: Contains shared UI classes.
- [x] Refactor BaseViewModel class implementation: Replace LiveData with flow.
- [x] Materialize app.
- [x] Cleanup Kotlin DSL Implementation.

### Test Tasks
- [x] Switch to Junit5
- [x] Move test dependencies to common-testing
- [x] Add/update missing test cases.
- [x] Fix flaky tests.
- [ ] Add instrumentation tests.
