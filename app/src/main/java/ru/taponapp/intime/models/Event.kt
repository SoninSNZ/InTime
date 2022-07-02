package ru.taponapp.intime.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Event(@PrimaryKey val id: UUID = UUID.randomUUID(),
                 var title: String,
                 var time: String = "123") : Item()