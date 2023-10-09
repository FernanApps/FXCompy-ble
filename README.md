
<h1 align="center">Fx - Compyble</h1>

<p align="center">
<a href="https://github.com/FernanApps/FXCompy-ble" title="Go"><img src="https://img.shields.io/static/v1?label=FernanApps&message=EcommerceShop&color=blue&logo=github" alt="Github - FerMemoryGame"></a>
<br>
<a href="https://github.com/FernanApps/FXCompy-ble/releases/latest"><img alt="Release" src="https://img.shields.io/github/v/release/FernanApps/FXCompy-ble.svg?include_prereleases=&sort=semver&color=red"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a> 

</p
</br>

<p align="center">
<img src="previews/background.png"/>
</p>

## **Introduction**
<p align="center">
<img src="previews/android.png"/>
</p>

<p align="center">  
🛒 Fx - Compyble: It is based on the compy.pe website with the proposal to continue learning jetpack compose.
</p>

## Architecture

![Architecture](https://miro.medium.com/v2/resize:fit:250/format:webp/1*VhRdBj1kXY3fwXDEDxoykg.png)

### Structure
This architecture allows for a clear separation of responsibilities, makes it easier to test and maintain code, and allows layers to be independent and can be modified or replaced without affecting other layers.

*This architecture is separated in modules*
<p align="center">
  <img src="previews/structure_main.png" alt="">
</p>


**Modules**


- **Presentation** is responsible for the user interface and user interaction. Here are the UI components and presentation logic. <br>  <br>  
  ![Imagen](previews/structure_app.png) <br>
  <br>


-  **Data**   is responsible for data access and persistence. Here the repositories defined in the domain layer are implemented and frameworks and libraries are used to interact with data sources. <br> <br>  
   ![Imagen](previews/structure_data.png) <br>


- **Domain**  is the core of the architecture and contains the main business logic of the application. Here the application-specific use cases and business rules are defined. <br><br>
  ![Imagen](previews/structure_domain.png)

  
## Screens
#### Splash
<div style="display: flex;">
  <img src="previews/splash_1.png" alt="" width="200">
</div>

#### Main
<div style="display: flex;">
  <img src="previews/main_1.png" alt="" width="200">
</div>

#### Category
<div style="display: flex;">
  <img src="previews/category.png" alt="" width="200">
</div>


#### Offers
<div style="display: flex;">
  <img src="previews/offers.png" alt="" width="200">
</div>

#### Favorite ❤️
<div style="display: flex;">
  <img src="previews/favorite.png" alt="" width="200">
</div>

#### Details
<div style="display: flex;">
  <img src="previews/details_1.png" alt="" width="200">
  <img src="previews/details_2.png" alt="" width="200">
</div>


## Tech Stack
The Fx - Compyble App utilizes the following technologies:
- Minimum SDK level 21
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) based for asynchronous.
- Jetpack
    - Lifecycle - Observe Android lifecycles and handle UI states upon the lifecycle changes.
    - ViewModel - Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
    - DataBinding - Binds UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
- Architecture
    - MVVM Architecture (View - DataBinding - ViewModel - Model)
- [Retrofit2](https://github.com/square/retrofit) - Construct the REST APIs.
- [Glide](https://github.com/bumptech/glide) - Loading images from network.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components for building ripple animation, and CardView.

## Download
Go to the [Releases](https://github.com/FernanApps/FernanEcommerceShop/releases) to download the latest APK.

## Preview
<img src="previews/preview.gif" height="320" />

## Tech stack & Open-source libraries
- Minimum SDK level 21
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) based for asynchronous.
- Jetpack
    - Lifecycle - Observe Android lifecycles and handle UI states upon the lifecycle changes.
    - ViewModel - Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
    - DataBinding - Binds UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
- Architecture
    - MVVM Architecture (View - DataBinding - ViewModel - Model)
- [Retrofit2](https://github.com/square/retrofit) - Construct the REST APIs.
- [Glide](https://github.com/bumptech/glide) - Loading images from network.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components for building ripple animation, and CardView.


## Find this repository useful? :heart:
Also, __[follow me](https://github.com/FernanApps)__ on GitHub for my next creations! 🤩

# License
```xml
- The code is: `-
```
