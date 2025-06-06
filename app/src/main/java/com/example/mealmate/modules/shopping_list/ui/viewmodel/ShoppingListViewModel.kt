package com.example.mealmate.modules.shopping_list.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mealmate.modules.home.data.repo.HomeRepo
import com.example.mealmate.modules.shopping_list.data.repo.ShoppingListRepo
import com.example.mealmate.shared.managers.SessionManager
import com.example.mealmate.shared.managers.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val repo: ShoppingListRepo,
    private val sessionManager: SessionManager,
    private val tokenManager: TokenManager
) : ViewModel() {
    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> get() = _userName

    init {
        _userName.value = sessionManager.me?.name
    }

    private val _email = MutableStateFlow<String>("")
    val email: StateFlow<String> = _email

    fun setEmail(email: String) {
        _email.value = email
    }
}