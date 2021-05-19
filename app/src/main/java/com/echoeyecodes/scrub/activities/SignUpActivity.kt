package com.echoeyecodes.scrub.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.echoeyecodes.scrub.MainActivity
import com.echoeyecodes.scrub.R
import com.echoeyecodes.scrub.api.constants.ApiState
import com.echoeyecodes.scrub.callbacks.CustomEditTextCallBack
import com.echoeyecodes.scrub.viewmodels.SignUpViewModel
import com.echoeyecodes.scrub.customviews.CustomEditText
import com.echoeyecodes.scrub.fragments.LoadingDialogFragment
import com.echoeyecodes.scrub.utils.AndroidUtilities
import com.google.android.material.button.MaterialButton

class SignUpActivity : AppCompatActivity(), CustomEditTextCallBack{

    private lateinit var matricNumber:CustomEditText
    private lateinit var viewModel:SignUpViewModel
    private lateinit var password:CustomEditText
    private lateinit var loadingFragment: LoadingDialogFragment
    private lateinit var loginBtn: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        loginBtn = findViewById(R.id.btn)
        matricNumber = findViewById(R.id.matric_number)
        password = findViewById(R.id.password)
        loadingFragment = supportFragmentManager.findFragmentByTag(LoadingDialogFragment.TAG) as LoadingDialogFragment? ?: LoadingDialogFragment.newInstance()

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        viewModel.errorObserver.observe(this,  {
            if(it.key == ""){
                getView("matricnumber")?.setError(false, "");
                getView("password")?.setError(false, "");
            }else{
                getView(it.key)?.setError(true, it.message);
            }
        })

        viewModel.getStatus().observe(this, {
            when(it){
                is ApiState.Loading ->  {
                    if(!loadingFragment.isAdded){
                        loadingFragment.show(supportFragmentManager, com.echoeyecodes.scrub.fragments.LoadingDialogFragment.TAG)
                    }
                }
                is ApiState.Complete -> {
                    dismissLoadingContainer()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is ApiState.Error ->{
                    dismissLoadingContainer()
                    AndroidUtilities.showSnackBar(loginBtn, it.data)
                }
            }
        })

        loginBtn.setOnClickListener { viewModel.submitForm() }
    }

    private fun dismissLoadingContainer(){
        if(loadingFragment.isVisible){
            loadingFragment.dismiss()
        }
    }


    override fun onTextInput(type: String, value: String) {
        if(this::viewModel.isInitialized){
            viewModel.setFormField(type, value)
            viewModel.validateField(type)
        }
    }

    private fun getView(key: String): CustomEditText? {
        return when (key) {
            "matricnumber" -> matricNumber
            "password" -> password
            else -> null
        }
    }

    override fun onFocused(key: String, isFocused: Boolean) {
        if (!isFocused) {
            viewModel.validateField(key)
        }
    }
}