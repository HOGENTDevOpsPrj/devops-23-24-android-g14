package com.hogent.svkapp.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.hogent.svkapp.R
import com.auth0.android.callback.Callback

/**
 * The [ViewModel] of the login screen.
 *
 * @param navController the [NavHostController] that is used to navigate to other screens.
 */
class LoginViewModel() : ViewModel() {

    var appJustLaunched by mutableStateOf(true)
    var userIsAuthenticated by mutableStateOf(false)


    private val TAG = "LoginViewModel"



    /**
     * Called when login button is clicked.
     */
    fun onLogin(context: Context, auth: Auth0, onSuccessNavigation: () -> Unit) {

        WebAuthProvider
            .login(auth)
            .withScheme(context.getString(R.string.com_auth0_scheme))
            .start(context, object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    Log.e(TAG, "Error occurred in onLogin(): $error")
                }

                override fun onSuccess(result: Credentials) {
                    val idToken = result.idToken
                    Log.d(TAG, "ID Token: $idToken")
                    userIsAuthenticated = true
                    onSuccessNavigation()
                }

            })


    }

//    companion object {
//
////        private lateinit var capturedNavController: NavHostController
////        fun setNavController(navController: NavHostController) {
////            capturedNavController = navController
////        }
//
//        val Factory: Factory = viewModelFactory { /* navController: NavHostController -> */
//            initializer {
//                val application = (this[APPLICATION_KEY] as Application)
////                val navController = capturedNavController
//                LoginViewModel(application = application)
//            }
//        }
//    }
//
}

//class LoginViewModelFactory(
//    private val navController: NavHostController,
//    private val application: Application
//) : ViewModelProvider.AndroidViewModelFactory(application) {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return LoginViewModel(navController, application) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

