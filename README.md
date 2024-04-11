# Paginated Users List Case Android Application

The application contains the following functionalities:

- Fetch user data from [https://randomuser.me/](https://randomuser.me/)
- Display a list of users, with each item containing the user's name, email, and picture
- As the user scrolls, load additional users
- When offline, the user should be able to see the previously loaded users from the last time they were connected
- When there is an error fetching additional users, display an error message to the user
- When the API returns an empty result, display an empty screen state

![Screenshot 2024-04-11 at 15 35 01](https://github.com/cristianomad/paginated-user-list-case-app/assets/772846/f9b490fa-384d-4746-ae04-210d99faa87c)

# Setup

- Download the latest stable [**Android Studio**](https://developer.android.com/studio)
- Import the project
- Run the app

## Unit Tests

To execute the unit tests report run:
```bash
./gradlew test
```
## Unit Test Coverage

To generate the test coverage report run:
```bash
./gradlew koverHtmlReportDebug
```
The report will be generated in `app/build/reports/kover/htmlDebug/index.html`

## Instrumented Tests

To execute the instrumented tests report run:
```bash
./gradlew connectedAndroidTest
```
Use the emulator to test.

The report will be generated in `app/build/reports/androidTests/connected/debug/index.html`

## Architecture Overview
The application architecture has three layers: [data layer](https://developer.android.com/jetpack/guide/data-layer), [domain layer](https://developer.android.com/jetpack/guide/domain-layer), and [UI layer](https://developer.android.com/jetpack/guide/ui-layer).

![architecture-1-overall](https://github.com/cristianomad/paginated-user-list-case-app/assets/772846/c27ee604-8b92-428a-ac39-220d8dedeedb)

The architecture follows a reactive programming model with [unidirectional data flow](https://developer.android.com/topic/architecture#unidirectional-data-flow). With the data layer at the bottom, the key concepts are:

- Higher layers react to changes in lower layers.
- Events flow down.
- Data flows up.

## Design Patterns

The **UI Layer** is structured using the [MVVM (Model-View-ViewModel](https://learn.microsoft.com/en-us/dotnet/architecture/maui/mvvm) architectural design pattern which allows better code separation, testability, reusability and maintainability of UI logic and UI components.

The **data layer** and **domain layer** are structured using the [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) for better readability, maintainability, testability, adaptability, and clear separation of concerns. Making it easier to modify or extend the application without affecting its core functionality.

The application make heavy of use the [S.O.L.I.D principles](https://en.wikipedia.org/wiki/SOLID).
- All architectural layers use dependency inversion
- Classes have a single responsibility
- Classes are focused on extensibility
- Interfaces have a clear purpose and are implemented by its intended classes.

To achieve the offline functionality it was used the [Single source of truth (SSOT)](https://en.wikipedia.org/wiki/Single_source_of_truth) pattern. Where the data is converged and normalized into the local database which is consumed by the application in order to display data reliably.

### Example: Fetch and display paginated user data
When the user opens the app or scrolls the user list, the app will attempt to load the first page of users from the remote server. If successful, the data will be stored into the Room database and displayed to the user. In case the user tries load more data and the request fails or the device doesn't have an internet connection, the application will return the last previously stored user data from the database.

The following diagram shows the events which occur and how data flows from the relevant objects to achieve this:

![QontoTest](https://github.com/cristianomad/paginated-user-list-case-app/assets/772846/d170acad-571c-4680-84c7-42f14ba73bd8)
