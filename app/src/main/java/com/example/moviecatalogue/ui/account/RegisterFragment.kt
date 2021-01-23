package com.example.moviecatalogue.ui.account

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
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
import com.example.moviecatalogue.databinding.FragmentRegisterBinding
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import kotlinx.coroutines.flow.collect

@SuppressLint("CheckResult")
@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher
            ?.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    toLogin()
                }

            })
    }

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
                password.length >= 8
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
                binding.btnSignUp.background.setTint(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.holo_blue_light
                    )
                )
            } else {
                binding.btnSignUp.isEnabled = false
                binding.btnSignUp.background.setTint(
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
            toLogin()
        }
        binding.btnSignUp.setOnClickListener {
            val editableUsername = binding.username
            val editablePassword = binding.password
            viewModel.register(editableUsername.text.toString(), editablePassword.text.toString())
        }
        lifecycleScope.launchWhenStarted {
            viewModel.registrationSuccess.collect {
                registerObserver(it)
            }
        }
    }

    private fun registerObserver(state: AuthState<Unit>) {
        when (state) {
            is AuthState.Init -> {
            }
            is AuthState.Loading -> binding.loading.visible()
            is AuthState.Success -> {
                binding.loading.invisible()
                val action = RegisterFragmentDirections.actionNavigationRegisterToNavigationMovie()
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

    private fun toLogin() {
        val action = RegisterFragmentDirections.actionNavigationRegisterToNavigationLogin()
        requireView().findNavController().navigate(action)
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