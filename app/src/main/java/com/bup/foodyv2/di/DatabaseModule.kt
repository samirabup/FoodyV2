package com.bup.foodyv2.di

import android.content.Context
import androidx.room.Room
import com.bup.foodyv2.data.database.RecipesDatabase
import com.bup.foodyv2.util.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideDao(database: RecipesDatabase) = database.recipesDao()

}