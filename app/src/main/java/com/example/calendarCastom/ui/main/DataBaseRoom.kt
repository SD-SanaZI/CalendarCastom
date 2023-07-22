package com.example.calendarCastom.ui.main

import androidx.room.*
import android.content.Context

@Entity(tableName = "events")
data class EntityDataBase(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int? = null,
    @ColumnInfo(name = "year")
    val year:Int,
    @ColumnInfo(name = "month")
    val month:Int,
    @ColumnInfo(name = "day")
    val day:Int,
    @ColumnInfo(name = "hour")
    val hour:Int,
    @ColumnInfo(name = "minute")
    val minute:Int,
    @ColumnInfo(name = "text")
    val text:String
)

@Dao
interface DaoDataBase{
    @Query("SELECT * FROM events")
    fun getAll():List<EntityDataBase>

    @Query("SELECT * FROM events WHERE year = :year AND month = :month AND day = :day")
    fun getDayEvents(year: Int, month: Int, day: Int):List<EntityDataBase>

    @Insert
    fun insert(event:EntityDataBase):Long

    @Query("DELETE FROM events WHERE id = :id")
    fun delete(id:Int?)

    @Query("DELETE FROM events")
    fun deleteAll()
}

@Database(entities = [EntityDataBase::class], version = 1)
abstract class DataBaseRoom: RoomDatabase(){
    abstract fun dao():DaoDataBase

    companion object{
        private const val DATABASE_NAME = "Events.db"

        fun getInstance(appContext: Context): DataBaseRoom {
            return Room.databaseBuilder(appContext, DataBaseRoom::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
