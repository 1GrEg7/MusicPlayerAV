package data.db

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var database: AppDatabase? = null

    fun provideDatabase(context: Context): AppDatabase {
        if (database == null) {
            database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "Tracks"
            ).fallbackToDestructiveMigration().build()
        }
        return database!!
    }

    fun provideTrackDao(context: Context): TrackDao {
        return provideDatabase(context).trackDao()
    }
}