package com.example.giro.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.giro.R
import com.example.giro.databinding.FragmentLoginBinding
import com.example.giro.data.network.AuthApi
import com.example.giro.data.network.Resource
import com.example.giro.data.repository.AuthRepository
import com.example.giro.ui.base.BaseFragment
import com.example.giro.ui.home.HomeActivity
import com.example.giro.utils.enable
import com.example.giro.utils.handleApiError
import com.example.giro.utils.startNewActivity
import com.example.giro.utils.visible
import com.example.giro.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressbar.visible(false)
        binding.login.enable(false)

        binding.promptCreate.setOnClickListener { view
            view?.findNavController()?.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        vm.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when(it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                        vm.saveAuthToken(it.value.token)
                        //it.value.userLoginDetails.token?.let { }
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it) {}
            }
        })

        binding.pasword2.addTextChangedListener {
            val email = binding.pasword2.text.toString().trim()
            binding.login.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.login.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.email2.text.toString().trim()
        val password = binding.pasword2.text.toString().trim()
        vm.login(email, password)
    }
    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)

}