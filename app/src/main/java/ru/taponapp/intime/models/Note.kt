package ru.taponapp.intime.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(@PrimaryKey(autoGenerate = true)
                @ColumnInfo(name = "id")
                val id: Int = 0,
                @ColumnInfo(name = "title")
                var title: String = "",
                @ColumnInfo(name = "text")
                var text: String = "") : Item()