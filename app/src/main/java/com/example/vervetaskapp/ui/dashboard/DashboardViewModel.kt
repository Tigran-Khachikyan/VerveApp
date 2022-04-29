package com.example.vervetaskapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vervetaskapp.data.repositories.ProjectRepository
import com.example.vervetaskapp.models.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel(private val projectRepository: ProjectRepository) : ViewModel() {

    private val _projects: MutableLiveData<List<Project>> = MutableLiveData()
    val projects: LiveData<List<Project>> get() = _projects

    init {
        loadProjects()
    }

    private fun loadProjects() {
        viewModelScope.launch {
            projectRepository.getProjects().collect { result ->
                withContext(Dispatchers.Main) {
                    _projects.value = result ?: listOf()
                }
            }
        }
    }

    fun addProject(project: Project) {
        viewModelScope.launch {
            projectRepository.insertOrUpdateProject(project)
        }
    }
}