package com.example.giro.utils

import android.app.Activity
import android.content.Intent
import android.hardware.camera2.CaptureFailure
import android.view.View
import androidx.fragment.app.Fragment
import com.example.giro.data.network.Resource
import com.example.giro.data.repository.BaseRepository
import com.example.giro.ui.auth.LoginFragment
import com.example.giro.ui.auth.RegisterFragment
import com.example.giro.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar

fun<A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}
fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when{
        failure.isNetworkError -> requireView().snackbar("Please check your internet connect", retry)
        failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().snackbar("You've entered incorrect email or password")
            }
            else if (this is RegisterFragment){
                requireView()
                    .snackbar(failure.errorBody?.string().toString())
            }
            else {
                //@todo perform logout operation here
                (this as BaseFragment<*, *, *>).logout()
            }
        }
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbar(error)
        }
    }
}