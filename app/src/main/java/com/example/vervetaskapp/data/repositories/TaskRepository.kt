package com.example.vervetaskapp.data.repositories

import com.example.vervetaskapp.data.db.TaskDao
import com.example.vervetaskapp.models.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class TaskRepository(
    private val dao: TaskDao,
    private val coroutineContext: CoroutineContext
) {
    suspend fun insertOrUpdateTask(task: Task) {
        withContext(coroutineContext) {
            dao.insertOrUpdate(task)
        }
    }

    fun getTasksForProject(projectId: Int): Flow<List<Task>?> = dao.getTasksForProject(projectId)

    fun getTaskDetails(id: Int): Flow<Task?> = dao.getTaskDetails(id)
}