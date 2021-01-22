package com.example.moviecatalogue.ui.account

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.utils.UserPreferences
import com.example.moviecatalogue.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by viewModels()

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = userPreferences.username
        binding.username.text = username
        binding.btnLogin.text = if (username != "Guest") "LOGOUT" else "LOGIN"

        binding.btnLogin.setOnClickListener {
            if (username != "Guest") {
                val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.AlertDialogTheme)
                    .setMessage("Are you sure to logout?")
                    .setPositiveButton("Yes") { _, _ ->
                        viewModel.logout()
                        val action =
                            ProfileFragmentDirections.actionNavigationProfileToNavigationMovie()
                        view.findNavController().navigate(action)
                    }
                    .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
                dialogBuilder.show()
            } else {
                val action = ProfileFragmentDirections.actionProfileFragmentToNavigationLogin()
                view.findNavController().navigate(action)
            }
        }

        binding.modeSwitch.isChecked = userPreferences.darkMode
        binding.modeSwitch.setOnCheckedChangeListener { _, checked ->
            userPreferences.darkMode = checked
            AppCompatDelegate.setDefaultNightMode(
                if (userPreferences.darkMode) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        binding.btnExit.setOnClickListener {
            this.activity?.finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}