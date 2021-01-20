package com.example.moviecatalogue.ui.account

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.moviecatalogue.databinding.FragmentRegisterBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable

@SuppressLint("CheckResult")
@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
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
                binding.btnSignUp.isEnabled = true
                binding.btnSignUp.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.holo_blue_light
                    )
                )
            } else {
                binding.btnSignUp.isEnabled = false
                binding.btnSignUp.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.darker_gray
                    )
                )
            }
        }
        additionalAction()
    }

    private fun additionalAction() {
        binding.toLogin.setOnClickListener {
            view?.findNavController()?.popBackStack()
        }
        binding.btnSignUp.setOnClickListener {
            val editableUsername = binding.username
            val editablePassword = binding.password
            viewModel.register(editableUsername.text.toString(), editablePassword.text.toString())
            val action = RegisterFragmentDirections.actionNavigationRegisterToNavigationLogin()
            view?.findNavController()?.navigate(action)
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