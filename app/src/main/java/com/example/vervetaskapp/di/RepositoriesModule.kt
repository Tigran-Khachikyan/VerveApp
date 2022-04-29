package com.example.vervetaskapp.di

import com.example.vervetaskapp.data.repositories.ProjectRepository
import com.example.vervetaskapp.data.repositories.TaskRepository
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val repositoriesModule = module {
    single { ProjectRepository(dao = get(), coroutineContext = Dispatchers.IO) }
    single { TaskRepository(dao = get(), coroutineContext = Dispatchers.IO) }
}
