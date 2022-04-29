package com.example.vervetaskapp.ui.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vervetaskapp.data.repositories.TaskRepository
import com.example.vervetaskapp.models.Task
import com.example.vervetaskapp.ui.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    private val _savedTask: MutableLiveData<Task> = MutableLiveData()
    val savedTask: LiveData<Task> get() = _savedTask

    private val _comments: MutableLiveData<List<String>> = MutableLiveData()
    val comments: LiveData<List<String>> get() = _comments

    private val _isSubmitActivated: MutableLiveData<Boolean> = MutableLiveData()
    val isSubmitActivated: LiveData<Boolean> get() = _isSubmitActivated

    private val _message: SingleLiveEvent<String> = SingleLiveEvent()
    val message: SingleLiveEvent<String> = _message

    private var edit: Task? = null
        set(value) {
            field = value
            _comments.value = field?.comments ?: listOf()
            _isSubmitActivated.value = field != savedTask.value
        }

    fun loadTaskDetails(id: Int) {
        if (id != savedTask.value?.id) {
            viewModelScope.launch {
                taskRepository.getTaskDetails(id).collect { result ->
                    withContext(Dispatchers.Main) {
                        result?.let {
                            _savedTask.value = it
                            edit = it
                        }
                    }
                }
            }
        }
    }

    fun addComment(comment: String) {
        arrayListOf(comment).run {
            edit?.comments?.let { addAll(it) }
            edit = edit?.copy(comments = this)
        }
    }

    fun onTitleChanged(title: String) {
        edit = edit?.copy(title = title)
    }

    fun onDescriptionChanged(desc: String) {
        edit = edit?.copy(description = desc)
    }

    fun onReviewStateChanged(inReview: Boolean) {
        edit = edit?.copy(inReview = inReview)
    }

    fun onSubmit() {
        edit?.let { edit ->
            savedTask.value?.apply {
                title = edit.title
                description = edit.description
                inReview = edit.inReview
                comments = edit.comments
                creationDate = Calendar.getInstance().time
                viewModelScope.launch {
                    taskRepository.insertOrUpdateTask(this@apply)
                    _message.value = "Saved"
                }
            }
        }
    }
}