package com.example.giro.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.giro.R
import com.example.giro.data.network.AuthApi
import com.example.giro.data.network.Resource
import com.example.giro.data.repository.AuthRepository
import com.example.giro.databinding.FragmentRegisterBinding
import com.example.giro.ui.base.BaseFragment
import com.example.giro.utils.enable
import com.example.giro.utils.handleApiError
import com.example.giro.utils.startNewActivity
import com.example.giro.utils.visible
import com.example.giro.viewModel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<AuthViewModel, FragmentRegisterBinding, AuthRepository>() {

    private val spinner = binding.interest1
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressBar?.visible(false)
        binding.signUp1.enable(false)

        binding.signUpString1.setOnClickListener { view
            view?.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment)
        }

        vm.registerResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when(it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()
                        requireActivity().startNewActivity(VerifyActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it) {}
            }
        })

        binding.pasword1.addTextChangedListener {
            val email = binding.pasword1.text.toString().trim()
            binding.signUp1.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }


        context?.let {
            ArrayAdapter.createFromResource(it, R.array.role, android.R.layout.simple_spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }

        fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
//        fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long): String {
//            val text: String = parent?.getItemAtPosition(position).toString()
//            spinner = text
//        }
    }

    private fun register() {
        val username = binding.username1.text.toString().trim()
        val email = binding.email1.text.toString().trim()
        val password = binding.pasword1.text.toString().trim()
        val role = spinner
        val phone = binding.phone1.text.toString().trim()
        vm.register(username, email, phone, role.toString(), password)
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegisterBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)

}