package com.example.mealmate.modules.auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.mealmate.modules.auth.data.model.OTPRequest
import com.example.mealmate.modules.auth.data.model.ResendOtpRequest
import com.example.mealmate.modules.auth.data.model.VerifyOtpRequest
import com.example.mealmate.modules.auth.data.repo.RegisterRepo
import com.example.mealmate.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import to
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepo: RegisterRepo
) : ViewModel() {

    //Register Form
    private val _isRegisterLoading = MutableStateFlow<Boolean>(false)
    val isRegisterLoading: StateFlow<Boolean> = _isRegisterLoading

    private val _registerErrorMessage = MutableStateFlow<String>("")
    val registerErrorMessage: StateFlow<String> = _registerErrorMessage

    private val _name = MutableStateFlow<String>("")
    val name: StateFlow<String> = _name

    fun setName(name: String) {
        _name.value = name
    }

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    fun setEmail(email: String) {
        _email.value = email
    }

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    fun setPassword(password: String) {
        _password.value = password
    }

    private val _cpassword = MutableStateFlow("")
    val cpassword: StateFlow<String> = _cpassword

    fun setcPassword(cpassword: String) {
        _cpassword.value = cpassword
    }

    fun onClickSignUp(appNavi: NavHostController) {
        _isRegisterLoading.value = true
        viewModelScope.launch {
            registerRequestOtp(
                otpRequest = OTPRequest(
                    email = email.value,
                    name = name.value,
                    password = password.value
                ),
                onSuccess = { code ->
                    _isRegisterLoading.value = false
                    startOtpCountdown()
                    appNavi.to(Screen.OtpScreen.route)
                },
                onError = { errorMsg ->
                    _isRegisterLoading.value = false
                    _registerErrorMessage.value = errorMsg
                }
            )
        }
    }

    private suspend fun registerRequestOtp(
        otpRequest: OTPRequest,
        onSuccess: (code: String) -> Unit,
        onError: (errMsg: String) -> Unit
    ) {
        val result = registerRepo.registerRequestOtp(otpRequest)
        result.fold(
            onLeft = { failure -> onError(failure.errorMessage) },
            onRight = { code -> onSuccess(code) }
        )
    }


    //OTP page
    val currentOtp = MutableStateFlow("")
    val showErrorMessage = MutableStateFlow(false)
    val errorMessage = MutableStateFlow("")
    val isEnable = MutableStateFlow(true)
    val isOtpBtnEnable = MutableStateFlow(true)
    val canResend = MutableStateFlow(false)
    val resending = MutableStateFlow(false)
    val otpBtnLoading = MutableStateFlow(false)
    val remainingSeconds = MutableStateFlow(30)
    fun updateCurrentOtp(value: String) {
        currentOtp.value = value
    }

    fun setShowErrorMessage(show: Boolean) {
        showErrorMessage.value = show
    }

    fun setErrorMessage(msg: String) {
        errorMessage.value = msg
    }

    fun setIsEnable(enable: Boolean) {
        isEnable.value = enable
    }

    fun setCanResend(value: Boolean) {
        canResend.value = value
    }

    fun setResending(value: Boolean) {
        resending.value = value
    }


    private fun startOtpCountdown(duration: Int = 30) {
        viewModelScope.launch {
            remainingSeconds.value = duration
            canResend.value = false

            while (remainingSeconds.value > 0) {
                delay(1000)
                remainingSeconds.value -= 1
            }
            canResend.value = true
        }
    }

    fun resendOtp() {
        setResending(true)
        startOtpCountdown()
        // Trigger resend logic here
        // Once done:
        viewModelScope.launch {
            resendOtp(
                otpRequest = ResendOtpRequest(
                    email = email.value,
                ),
                onSuccess = { code ->
                    setResending(false)
                    // register success, go to login
                },
                onError = { errorMsg ->
                    setResending(false)
                    errorMessage.value = errorMsg
                }
            )
        }

    }

    private suspend fun resendOtp(
        otpRequest: ResendOtpRequest,
        onSuccess: (code: String) -> Unit,
        onError: (errMsg: String) -> Unit
    ) {
        val result = registerRepo.resendOtpRequest(otpRequest)
        result.fold(
            onLeft = { failure -> onError(failure.errorMessage) },
            onRight = { code -> onSuccess(code) }
        )
    }

    fun onOtpValueChange(value: String) {
        currentOtp.value = value
        isOtpBtnEnable.value = value.length == 6
    }

    fun onOtpBtnClick() {
        otpBtnLoading.value = true
        viewModelScope.launch {
            verifyOtp(
                otpRequest = VerifyOtpRequest(
                    email = email.value,
                    code = currentOtp.value.toString(),
                ),
                onSuccess = { code ->
                    otpBtnLoading.value = false
                    // register success, go to login
                },
                onError = { errorMsg ->
                    otpBtnLoading.value = false
                    setShowErrorMessage(true)
                    setErrorMessage(errorMsg)
                }
            )
        }
    }

    private suspend fun verifyOtp(
        otpRequest: VerifyOtpRequest,
        onSuccess: (code: String) -> Unit,
        onError: (errMsg: String) -> Unit
    ) {
        val result = registerRepo.verifyOtpRequest(otpRequest)
        result.fold(
            onLeft = { failure -> onError(failure.errorMessage) },
            onRight = { code -> onSuccess(code) }
        )
    }
}