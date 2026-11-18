package com.example.nutriportfolio.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.room.Room
import com.example.nutriportfolio.data.db.UserDatabase
import com.example.nutriportfolio.data.db.UserProfileDao
import com.example.nutriportfolio.data.prefs.SettingsStore
import com.example.nutriportfolio.data.repo.AuthRepository
import com.example.nutriportfolio.data.repo.AuthRepositoryImpl
import com.example.nutriportfolio.data.repo.ProfileRepository
import com.example.nutriportfolio.data.repo.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "user.db"
        ).build()
    }

    @Provides
    fun provideUserProfileDao(database: UserDatabase): UserProfileDao {
        return database.userProfileDao()
    }

    @Provides
    @Singleton
    fun provideProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository {
        return impl
    }

    @Provides
    @Singleton
    fun provideCredentialManager(@ApplicationContext context: Context): CredentialManager {
        return CredentialManager.create(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository {
        return impl
    }
}
