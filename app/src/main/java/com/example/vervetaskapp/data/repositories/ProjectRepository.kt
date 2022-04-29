package com.example.vervetaskapp.data.repositories

import com.example.vervetaskapp.data.db.ProjectDao
import com.example.vervetaskapp.models.Project
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ProjectRepository(
    private val dao: ProjectDao,
    private val coroutineContext: CoroutineContext
) {
    suspend fun insertOrUpdateProject(project: Project) {
        withContext(coroutineContext) {
            dao.insertOrUpdate(project)
        }
    }

    fun getProjects(): Flow<List<Project>?> = dao.getProjects()
}