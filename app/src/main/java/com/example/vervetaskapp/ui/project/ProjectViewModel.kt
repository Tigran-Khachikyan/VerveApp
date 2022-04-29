package com.example.vervetaskapp.ui.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vervetaskapp.data.repositories.TaskRepository
import com.example.vervetaskapp.models.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProjectViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    private val _tasks: MutableLiveData<List<Task>> = MutableLiveData()
    val tasks: LiveData<List<Task>> get() = _tasks

    private var projectId: Int? = null
        set(value) {
            if (field != value) {
                field = value
                field?.let {
                    viewModelScope.launch {
                        taskRepository.getTasksForProject(it).collect { result ->
                            withContext(Dispatchers.Main) {
                                _tasks.value = result ?: listOf()
                            }
                        }
                    }
                }
            }
        }

    fun loadTasks(projectId: Int) {
        this.projectId = projectId
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            taskRepository.insertOrUpdateTask(task)
        }
    }
}