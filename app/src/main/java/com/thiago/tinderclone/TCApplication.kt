package com.thiago.tinderclone

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Classe de aplicação que estende Application e é anotada com @HiltAndroidApp
@HiltAndroidApp
class TCApplication : Application() {
}
