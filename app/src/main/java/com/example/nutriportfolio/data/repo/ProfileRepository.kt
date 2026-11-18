package com.example.nutriportfolio.data.repo

import com.example.nutriportfolio.data.db.UserProfileDao
import com.example.nutriportfolio.data.db.toDomain
import com.example.nutriportfolio.data.db.toEntity
import com.example.nutriportfolio.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface ProfileRepository {
    val profileFlow: Flow<UserProfile?>
    suspend fun getProfile(): UserProfile?
    suspend fun saveProfile(profile: UserProfile)
    suspend fun clearProfile()
}

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao
) : ProfileRepository {

    override val profileFlow: Flow<UserProfile?> =
        userProfileDao.getProfileFlow().map { it?.toDomain() }

    override suspend fun getProfile(): UserProfile? {
        return userProfileDao.getProfile()?.toDomain()
    }

    override suspend fun saveProfile(profile: UserProfile) {
        userProfileDao.insertOrUpdateProfile(profile.toEntity())
    }

    override suspend fun clearProfile() {
        userProfileDao.deleteProfile()
    }
}
