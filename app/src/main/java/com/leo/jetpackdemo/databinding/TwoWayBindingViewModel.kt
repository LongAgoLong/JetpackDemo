package com.leo.jetpackdemo.databinding

import android.util.Log
import androidx.databinding.ObservableField

/**
 * 使用ObservableField完成双向绑定
 */
class TwoWayBindingViewModel {
    val loginModelObservableField: ObservableField<LoginModel>

    init {
        val loginModel = LoginModel("Mike")
        loginModelObservableField = ObservableField()
        loginModelObservableField.set(loginModel)
    }

    fun getUserName(): String {
        return loginModelObservableField.get()!!.userName
    }

    fun setUserName(name: String) {
        loginModelObservableField.get()!!.userName = name
        Log.i("LEOTEST", "文本编辑变更：$name")
    }
}
