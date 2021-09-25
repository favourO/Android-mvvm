package com.example.giro.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.giro.data.network.Resource
import com.example.giro.data.network.UserApi
import com.example.giro.data.repository.UserRepository
import com.example.giro.data.responses.UserX
import com.example.giro.databinding.FragmentHomeBinding
import com.example.giro.ui.base.BaseFragment
import com.example.giro.utils.visible
import com.example.giro.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository>() {

    private var _binding: FragmentHomeBinding? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressbarH.visible(false)

        vm.getUser()

        vm.user.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success -> {
                        updateUI(it.value.user)
                        Log.d("log", it.value.user.phone)
                }
                is Resource.Loading -> {
                    binding.progressbarH.visible(true)
                }
                else -> {

                }
            }
        })

        binding.logout.setOnClickListener {
            logout()
        }

    }

    private fun updateUI(user: UserX) {
        with(binding) {
            text_name.text = user.username
            textPhone.text = user.phone
            textHome.text = user.email

            //text_phone.text = userLoginDetails.status
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = remoteDataSource.buildApi(UserApi::class.java, token)
        return UserRepository(api)
    }
}