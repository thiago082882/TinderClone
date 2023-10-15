package com.thiago.tinderclone

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

// Módulo Dagger Hilt que fornece instâncias do Firebase para injeção de dependência
@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    // Método que fornece uma instância do FirebaseAuth
    @Provides
    fun provideAuthentication(): FirebaseAuth = Firebase.auth

    // Método que fornece uma instância do FirebaseFirestore
    @Provides
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore

    // Método que fornece uma instância do FirebaseStorage
    @Provides
    fun provideStorage(): FirebaseStorage = Firebase.storage
}
