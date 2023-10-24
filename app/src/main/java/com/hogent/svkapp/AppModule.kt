package com.hogent.svkapp

import androidx.navigation.NavHostController
import com.hogent.svkapp.data.repositories.CargoTicketRepository
import com.hogent.svkapp.data.repositories.CargoTicketRepositoryImpl
import com.hogent.svkapp.data.sources.LocalCargoTicketDataSource
import com.hogent.svkapp.domain.Validator
import com.hogent.svkapp.presentation.viewmodels.LoginViewModel
import com.hogent.svkapp.presentation.viewmodels.MainScreenViewModel
import com.hogent.svkapp.util.AndroidLogger

interface AppModule {
    fun getMainScreenViewModel(): MainScreenViewModel
    fun getLoginViewModel(): LoginViewModel
    fun getNavController(): NavHostController
}

class AppModuleImpl(private val navHostController: NavHostController) : AppModule {
    private val logger = AndroidLogger()
    private val ticketDataSource = LocalCargoTicketDataSource(logger = logger)
    private val cargoTicketRepository: CargoTicketRepository =
        CargoTicketRepositoryImpl(cargoTicketDataSource = ticketDataSource)
    private val validator = Validator()
    private val mainScreenViewModel = MainScreenViewModel(
        validator = validator,
        cargoTicketRepository = cargoTicketRepository,
        navController = navHostController
    )
    private val loginViewModel = LoginViewModel(
        navController = navHostController
    )

    override fun getMainScreenViewModel(): MainScreenViewModel {
        return mainScreenViewModel
    }

    override fun getLoginViewModel(): LoginViewModel {
        return loginViewModel
    }

    override fun getNavController(): NavHostController {
        return navHostController
    }
}
