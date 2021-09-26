package com.asif.mvvmcleanarchitecture.di

import android.app.Application
import androidx.room.Room
import com.asif.mvvmcleanarchitecture.feature_note.data.data_source.NoteDatabase
import com.asif.mvvmcleanarchitecture.feature_note.data.repository.NoteRepositoryImpl
import com.asif.mvvmcleanarchitecture.feature_note.domain.repository.NoteRepository
import com.asif.mvvmcleanarchitecture.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }

    @Singleton
    @Provides
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }
}