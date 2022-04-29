package com.example.vervetaskapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vervetaskapp.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM TASK WHERE PROJECT_ID ==:projectId")
    fun getTasksForProject(projectId: Int): Flow<List<Task>?>

    @Query("SELECT * FROM TASK WHERE ID ==:id")
    fun getTaskDetails(id: Int): Flow<Task?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(task: Task)
}