package com.example.moviecatalogue.ui.account

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.moviecatalogue.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable

@SuppressLint("CheckResult")
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by viewModels()

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailStream = RxTextView.textChanges(binding.username)
            .skipInitialValue()
            .map { email ->
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            emailAlert(it)
        }

        val passwordStream = RxTextView.textChanges(binding.password)
            .skipInitialValue()
            .map { password ->
                password.length > 6
            }
        passwordStream.subscribe {
            passwordAlert(it)
        }

        val fieldStream = Observable.combineLatest(
            emailStream,
            passwordStream,
            { validEmail: Boolean, validPassword: Boolean ->
                validEmail && validPassword
            })
        fieldStream.subscribe { isValid ->
            if (isValid) {
                binding.btnSignIn.isEnabled = true
                binding.btnSignIn.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.holo_blue_light
                    )
                )
            } else {
                binding.btnSignIn.isEnabled = false
                binding.btnSignIn.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.darker_gray
                    )
                )
            }
        }
        additionalAction()
        binding.btnTest.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "username: ${auth.currentUser?.email}--password: ${auth.currentUser?.isEmailVerified}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun additionalAction() {
        binding.toRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionNavigationLoginToNavigationRegister()
            view?.findNavController()?.navigate(action)
        }
        binding.forgotPassword.setOnClickListener {

        }
        binding.btnSignIn.setOnClickListener {
            val editableUsername = binding.username
            val editablePassword = binding.password
            viewModel.login(editableUsername.text.toString(), editablePassword.text.toString())
        }
    }

    private fun emailAlert(isValid: Boolean) {
        binding.username.error = if (!isValid) "Enter Valid Email" else null
    }

    private fun passwordAlert(isValid: Boolean) {
        binding.password.error = if (!isValid) "Enter Valid Password" else null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}