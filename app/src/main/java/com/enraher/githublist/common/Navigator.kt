package com.enraher.githublist.common

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator @Inject constructor() {

    lateinit var navController: NavController

    fun navigate(@IdRes navigationId: Int, bundle: Bundle? = null) {
        navController.navigate(navigationId, bundle)
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}