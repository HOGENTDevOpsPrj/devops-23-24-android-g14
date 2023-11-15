package com.hogent.svkapp.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.hogent.svkapp.R
import com.hogent.svkapp.Route
import com.auth0.android.callback.Callback

/**
 * The [ViewModel] of the login screen.
 *
 * @param navController the [NavHostController] that is used to navigate to other screens.
 */
class LoginViewModel(private val navController: NavHostController) : ViewModel() {
//    private var _userEmail = mutableStateOf(value = "")
//
//    /**
//     * The email of the user.
//     */
//    val userEmail: State<String> get() = _userEmail
//
//    private var _password = mutableStateOf(value = "")
//
//    /**
//     * The password of the user.
//     */
//    val password: State<String> get() = _password

//    private val TAG = "LoginViewModel"
//    private lateinit var account: Auth0
//    private lateinit var context: Context

//    init {
//        private val context = getApplication<Application>().applicationContext
//        setContext(context)
//    }

//    fun setContext(activityContext: Context) {
//        context = activityContext
//        account = Auth0(
//            context.getString(R.string.com_auth0_client_id),
//            context.getString(R.string.com_auth0_domain)
//        )
//    }

    /**
     * Called when login button is clicked.
     */
    fun onLogin() {

//        WebAuthProvider
//            .login(account)
//            .withScheme(context.getString(R.string.com_auth0_scheme))
//            .start(context, object : Callback<Credentials, AuthenticationException> {
//                override fun onFailure(error: AuthenticationException) {
//                    Log.e(TAG, "Error occurred in onLogin(): $error")
//                }
//
//                override fun onSuccess(result: Credentials) {
//                    val idToken = result.idToken
//                    Log.d(TAG, "ID Token: $idToken")
//
//                    navController.navigate(Route.Main.name)
//                }
//
//            })

        navController.navigate(Route.Main.name)
    }
}
