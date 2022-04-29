package com.example.vervetaskapp.di

import com.example.vervetaskapp.ui.dashboard.DashboardViewModel
import com.example.vervetaskapp.ui.project.ProjectViewModel
import com.example.vervetaskapp.ui.task.TaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { DashboardViewModel(projectRepository = get()) }
    viewModel { ProjectViewModel(taskRepository = get()) }
    viewModel { TaskViewModel(taskRepository = get()) }
}
