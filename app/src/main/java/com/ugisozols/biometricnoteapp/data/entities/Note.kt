package com.ugisozols.biometricnoteapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(
    val title : String,
    val content : String,
    @PrimaryKey
    val id : String = UUID.randomUUID().toString()
)
