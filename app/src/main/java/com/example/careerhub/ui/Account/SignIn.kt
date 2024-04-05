package com.example.careerhub.ui.Account

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.careerhub.R
import com.example.careerhub.databinding.FragmentSignInBinding
import com.example.careerhub.viewmodel.SignInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import android.view.MotionEvent

class SignIn : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var signInViewModel: SignInViewModel
    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.btnSignIn.setOnClickListener {
            signInViewModel = ViewModelProvider(this)[SignInViewModel::class.java]
            Log.d("InputFields", binding.etEmail.text.toString() + " " + binding.etPassword.text.toString())
            if (checkEmpty()) {
                signInViewModel.SignIn(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
            } else {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
            signInViewModel.signIn.observe(viewLifecycleOwner, Observer {
                Log.d("SignIn", it.toString())
                if (it.status == 200) {
                    Toast.makeText(context, "Sign in successfully", Toast.LENGTH_SHORT).show()
                    navigateToSecondActivity()
                } else {
                    Toast.makeText(context, "Sign in failed", Toast.LENGTH_SHORT).show()
                }
            })

        }
        // transition fragment Singin to SignUp
        binding.txtConfirmation.setOnClickListener{
            val navController = NavHostFragment.findNavController(this)
            navController.navigate(R.id.signUp)
        }

        binding.etPassword.setOnTouchListener(OnTouchListener { _, event ->
            val DRAWABLE_RIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= binding.etPassword.right - binding.etPassword.compoundDrawables[DRAWABLE_RIGHT].bounds.width()
                ) {
                     togglePasswordVisibility()
                    return@OnTouchListener true
                }
            }
            false
        })

        // Configure Google Sign In
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        binding.btnGoogle.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, 1000)
        }

        return binding.root
    }

    private fun togglePasswordVisibility() {
        binding.etPassword.inputType = if (binding.etPassword.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        } else {
            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        }
        val drawable = if (binding.etPassword.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            R.drawable.img_icon_viewpassword
        } else {
            R.drawable.imd_hide
        }
        binding.etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)
    }

    private fun checkEmpty(): Boolean {
        return binding.etEmail.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)
                navigateToSecondActivity()
            } catch (e: ApiException) {
              Toast.makeText(context, "Sign in failed", Toast.LENGTH_SHORT).show()
                Log.e("GoogleSignIn", "Exception occurred: " + e.message);
            }
        }
    }

    private fun navigateToSecondActivity() {
        val navController = NavHostFragment.findNavController(this)
        navController.navigate(R.id.homePageFragment)
    }


}
