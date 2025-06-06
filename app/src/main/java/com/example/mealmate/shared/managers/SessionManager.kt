package com.example.mealmate.shared.managers

import com.example.mealmate.modules.auth.data.model.MeResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {
    var token: String? = null
    var me: MeResponse? = null
}
