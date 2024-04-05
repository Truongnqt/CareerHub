package com.example.careerhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.careerhub.data.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.widget.Toast
import com.example.careerhub.ui.Account.SignIn
import com.example.careerhub.databinding.FragmentSignInBinding
import com.example.careerhub.model.AuthenResponse
import com.example.careerhub.ui.Account.SignUp.SignUp
import com.example.careerhub.ui.HomePageFragment
import androidx.navigation.fragment.NavHostFragment
import com.example.careerhub.R
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //use navigation component
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.signIn)


    }

}