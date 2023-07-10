package com.example.projectforworktesting.mock.ui

import android.content.res.Resources.NotFoundException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.projectforworktesting.mock.data.room.SportFactData
import com.example.projectforworktesting.mock.data.room.SportFactsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MockInfoViewViewModel(private val dao: SportFactsDao) : ViewModel(), ViewModelProvider.Factory {

    private val _factState = MutableStateFlow(SportFactData())
    val factState = _factState.asStateFlow()


    fun initWithId(id: Int){
        viewModelScope.launch {
            val fact = withContext(Dispatchers.IO){ dao.getById(id)}
            _factState.value = fact
        }
    }

    fun changeLike(){
        val value = !_factState.value.like
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val fact = _factState.value.copy(like = value)
                dao.insert(fact)
            }
            _factState.value = _factState.value.copy(like = value)
        }

    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MockInfoViewViewModel::class.java)){
            return MockInfoViewViewModel(dao) as T
        }

        throw NotFoundException("ViewModel not found")
    }

}