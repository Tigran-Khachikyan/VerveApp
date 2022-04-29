package com.example.vervetaskapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "TASK")
data class Task(
    @ColumnInfo(name = "TITLE") var title: String,
    @ColumnInfo(name = "DESCRIPTION") var description: String?,
    @ColumnInfo(name = "CREATED") var creationDate: Date,
    @ColumnInfo(name = "COMMENTS") var comments: List<String>?,
    @ColumnInfo(name = "IN_REVIEW") var inReview: Boolean,
    @ColumnInfo(name = "PROJECT_ID") var projectId: Int
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id: Int = 0
}
