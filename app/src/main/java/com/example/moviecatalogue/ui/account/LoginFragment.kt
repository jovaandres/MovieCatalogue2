package com.example.moviecatalogue.ui.account

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.data.AuthState
import com.example.moviecatalogue.core.utils.invisible
import com.example.moviecatalogue.core.utils.visible
import com.example.moviecatalogue.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputEditText
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import kotlinx.coroutines.flow.collect

@SuppressLint("CheckResult")
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by viewModels()

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
            .filter { it.isNotEmpty() }
            .map { email ->
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            emailAlert(it)
        }

        val passwordStream = RxTextView.textChanges(binding.password)
            .skipInitialValue()
            .filter { it.isNotEmpty() }
            .map { password ->
                password.length >= 6
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
                binding.btnSignIn.background.setTint(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.holo_blue_light
                    )
                )
            } else {
                binding.btnSignIn.isEnabled = false
                binding.btnSignIn.background.setTint(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.darker_gray
                    )
                )
            }
        }
        additionalAction()
        binding.btnTest.setOnClickListener {
//            viewModel.requestSession()
            Toast.makeText(it.context, viewModel.requestSession(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun additionalAction() {
        binding.toRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionNavigationLoginToNavigationRegister()
            requireView().findNavController().navigate(action)
        }
        binding.forgotPassword.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
                .setTitle("Reset Password")
            val input = TextInputEditText(requireContext())
            input.hint = getString(R.string.enter_email)
            input.setPaddingRelative(24, 16, 24, 16)
            input.inputType = InputType.TYPE_CLASS_TEXT
            alertDialog.setView(input)
            alertDialog.setPositiveButton("Send") { _, _ ->
                viewModel.forgotPassword(input.text.toString())
            }
            alertDialog.setNegativeButton("Cancel") { dialog, _ ->
                dialog?.cancel()
            }
            alertDialog.show()
        }
        binding.btnSignIn.setOnClickListener {
            val editableUsername = binding.username
            val editablePassword = binding.password
            viewModel.login(editableUsername.text.toString(), editablePassword.text.toString())
        }

        lifecycleScope.launchWhenStarted {
            viewModel.loginSuccess.collect {
                loginObserver(it)
            }
            viewModel.forgotPassword.collect {
                forgotPasswordObserver(it)
            }
        }
    }

    private fun loginObserver(state: AuthState<Unit>) {
        when (state) {
            is AuthState.Init -> {
            }
            is AuthState.Loading -> binding.loading.visible()
            is AuthState.Success -> {
                binding.loading.invisible()
                val action =
                    LoginFragmentDirections.actionNavigationLoginToNavigationMovie()
                requireView().findNavController().navigate(action)
            }
            is AuthState.Error -> {
                binding.loading.invisible()
                val alertDialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
                    .setMessage(state.message)
                    .setNeutralButton("OK") { dialog, _ -> dialog.cancel() }
                alertDialog.show()
            }
        }
    }

    private fun forgotPasswordObserver(state: AuthState<Unit>) {
        when (state) {
            is AuthState.Init -> {
            }
            is AuthState.Loading -> binding.loading.visible()
            is AuthState.Success -> {
                binding.loading.invisible()
                Toast.makeText(
                    requireContext(),
                    getString(R.string.email_reset_password),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is AuthState.Error -> {
                binding.loading.invisible()
                val alertDialog = AlertDialog.Builder(requireContext())
                    .setMessage(state.message)
                    .setNeutralButton("OK") { dialog, _ -> dialog.cancel() }
                alertDialog.show()
            }
        }
    }

    private fun emailAlert(isValid: Boolean) {
        binding.username.error = if (!isValid) getString(R.string.valid_email) else null
    }

    private fun passwordAlert(isValid: Boolean) {
        binding.password.error = if (!isValid) getString(R.string.valid_password) else null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}