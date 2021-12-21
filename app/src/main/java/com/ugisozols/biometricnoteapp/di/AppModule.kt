package com.ugisozols.biometricnoteapp.di

import android.content.Context
import androidx.core.content.PermissionChecker
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ugisozols.biometricnoteapp.data.NoteDao
import com.ugisozols.biometricnoteapp.data.entities.Note
import com.ugisozols.biometricnoteapp.data.entities.NoteDatabase
import com.ugisozols.biometricnoteapp.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Singleton
    @Provides
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        DATABASE_NAME
    ).build()

   @Singleton
   @Provides
    fun provideNoteDao(database : NoteDatabase) = database.noteDao()


}