
<p align="center">  
  <img width="100" height = "100" alt="image" src=https://github.com/user-attachments/assets/e92bc299-ba9d-4144-aec5-b1db25380084/>

  <h2 align="center">Conexiones</h2>
</p>

<h3 align="center">A words puzzle game done in <a href="https://kotlinlang.org/docs/multiplatform.html">KMM</a>, with fully native UI in Android and iOS, with shared Kotlin logic.
Fully off-line.</h3>

[<image width="200" height="60" src="https://github.com/user-attachments/assets/1d768415-4f54-47b8-844f-51f2042c422c"></image>](https://play.google.com/store/apps/details?id=com.luisma.conexiones.android)

### How is the app made?

**🤖 Android app:**

The Android app, is mostly UI code in [Jetpack Compose](https://developer.android.com/compose).

**🍎 iOS app:**

The iOS app, is mostly UI code in [SwiftUI](https://developer.apple.com/xcode/swiftui/).

Both UI structures and components, been both declaratives UI frameworks, are quite similars:  
<p>
  <img width="100" height = "200" alt="image" src="https://github.com/user-attachments/assets/f335c95b-9723-45b1-a80a-3cfa98cf70c2" />
  <img width="100" height = "200" alt="image" src="https://github.com/user-attachments/assets/4baf2dd2-1822-4d63-9de0-96727aa71a29" />
</p>

**📐 Shared logic:**

All the app logic, data, contracts and unit tests are in the <code>shared</code> module written in [Kotlin](https://kotlinlang.org/), both UI modules consume it. 

**📚 Tech stack**
- Jetpack Compose
- Swift UI
- Kotlin Multiplatform
- SqlDelight
- Kotlin DateTime
- Kotlin Serialization



