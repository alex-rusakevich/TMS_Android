package by.alerus.tms_android

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf

class TaskViewModel : ViewModel() {
    private val _tasks = mutableStateListOf(
        Task(1, "Купить молоко"),
        Task(2, "Заправить тарантас"),
        Task(3, "Сделать д/з по курсам")
    )
    val tasks: List<Task> = _tasks

    fun addTask(title: String) {
        val newId = (_tasks.maxOfOrNull { it.id } ?: 0) + 1
        _tasks.add(Task(newId, title))
    }

    fun deleteTask(id: Int) {
        _tasks.removeAll { it.id == id }
    }

    fun updateTask(id: Int, newTitle: String) {
        val index = _tasks.indexOfFirst { it.id == id }
        if (index != -1) {
            _tasks[index] = _tasks[index].copy(title = newTitle)
        }
    }
}