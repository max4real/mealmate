package com.example.mealmate.shared.managers

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {
    var token: String? = null
}
