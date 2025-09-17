package com.example.viewmodelhw

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel {
    val EMAIL_REGEX = "~ [A-Za-z] (.*) ([@]{1}) (.{1,5})(\\.)(.{1,})"
    private val _isAuthenticated = MutableStateFlow<Boolean?>(null)
    private val _userEmail = MutableStateFlow("")
    // Expose immutable copy

    val isAuthenticated = _isAuthenticated.asStateFlow()
    val userEmail = _userEmail.asStateFlow()

    fun authenticate(email: String, password: String) {
        _isAuthenticated.value = null
        if (email.isBlank() || password.isBlank() || !email.matches(EMAIL_REGEX.toRegex())) {
            _isAuthenticated.value = false
            return
        }
        _isAuthenticated.value = password.lowercase().contains("traxy")
    }

    fun setUserEmail(e: String) {
        _isAuthenticated.value = null
        _userEmail.value = e
    }
}