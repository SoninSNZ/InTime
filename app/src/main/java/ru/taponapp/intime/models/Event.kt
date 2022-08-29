package ru.taponapp.intime.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "events_table")
data class Event(@PrimaryKey(autoGenerate = true)
                 @ColumnInfo(name = "id")
                 var id: Int = 0,
                 @ColumnInfo(name = "title")
                 var title: String = "",
                 @ColumnInfo(name = "date")
                 var date: String = "",
                 @ColumnInfo(name = "time")
                 var time: String = "",
                 @ColumnInfo(name = "details")
                 var details: String = "",
                 @ColumnInfo(name = "millis_since_epoch")
                 var timeInMillisSinceEpoch: Long = 0) : Item()