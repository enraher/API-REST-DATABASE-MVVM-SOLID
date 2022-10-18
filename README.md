# API-REST-DATABASE-MVVM-SOLID by Enrique Ramirez Hernandez
This is an Android app I created with SOLID, Clean Code and best practice in mind.


With this challenge I tried to show my coding skills. I used SOLID principles and clean code. I used Kotlin, of course, and I did not focus on the design of the UI.

## What does the app do?

* After a splash screen app loads a list of Google GitHub repositories.
* The list is paged.
* The app has proper navigation.
* Forked repositories have a light green layout.
* The owner's repository avatar is shown and cached.
* If you click on a repository the app will show you some details.
* The repositories are cached to be available offline.

## How does the app do?
* I tried to follow the package by feature approach for the project structure.
* I used :
	* Jetpack components as LifeCycle, Paging, ViewModel, LiveData, SwipeRefreshLayout, Navigation and many more.
	* Kotlin Coroutines.
	* Hilt for Dependency Injection.
	* Retrofit for api calls.
	* Room database for caching the repositories.
	* Glide for images caching.
	* I used Gson for JSON parsing instead of moshi but I am comfortable with both.
	* For testing I used Junit, Google Truth and also Hilt

## What does the app need to improve on?

* The main core of the app is complete, I would like to migrate the paging library to Paging 3.
* The very important improvements are in the tests. The app was developed really thinking about being easy to test and I could create many more good tests for it.


I hope you like my code.



