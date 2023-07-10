package com.example.projectforworktesting.mock.ui

import android.content.res.Resources.NotFoundException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.projectforworktesting.mock.data.room.SportFactData
import com.example.projectforworktesting.mock.data.room.SportFactsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MockViewViewModel(private val dao: SportFactsDao) : ViewModel(), ViewModelProvider.Factory {

    private val _listState: MutableStateFlow<List<SportFactData>> = MutableStateFlow(emptyList())
    val listState = _listState.asStateFlow()

    init {
        viewModelScope.launch {
            checkDbIsEmpty()
            val list = withContext(Dispatchers.IO){
                dao.getAll()
            }
            _listState.value = list
        }
    }

    private suspend fun checkDbIsEmpty() = coroutineScope {
        withContext(Dispatchers.IO){
            val list = dao.getAll()
            if (list.isEmpty()){
                SportFactData.getExampleData().forEach {
                    dao.insert(it)
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MockViewViewModel::class.java)){
            return MockViewViewModel(dao) as T
        }
        throw NotFoundException("ViewModel MockViewViewModel not found")
    }
}