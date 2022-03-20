package com.kailin.coroutines_arch.ui.login

import android.os.Bundle
import android.text.InputType
import android.view.View
import com.kailin.coroutines_arch.R
import com.kailin.coroutines_arch.app.BaseFragment
import com.kailin.coroutines_arch.databinding.FragmentLoginBinding
import com.kailin.coroutines_arch.widget.navigation
import com.kailin.coroutines_arch.widget.setError

class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {
    override val viewModelClass = LoginViewModel::class.java
    override val viewDataBindingClass = FragmentLoginBinding::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding?.let {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }

        viewModel.usernameError.observe(viewLifecycleOwner) { error ->
            viewDataBinding?.usernameInputLayout?.setError(error)
        }

        viewModel.passwordError.observe(viewLifecycleOwner) { error ->
            viewDataBinding?.passwordInputLayout?.setError(error)
        }

        viewModel.displayedPassword.observe(viewLifecycleOwner) {
            viewDataBinding?:return@observe
            if (it){
                viewDataBinding?.passwordEditText?.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                viewDataBinding?.passwordVisibility?.setImageResource(R.drawable.ic_baseline_visibility_off_24)
            }else{
                viewDataBinding?.passwordEditText?.inputType =InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                viewDataBinding?.passwordVisibility?.setImageResource(R.drawable.ic_baseline_visibility_on_24)
            }
        }

        viewModel.loginFinish.observe(viewLifecycleOwner) {
            navigation(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        }
    }
}