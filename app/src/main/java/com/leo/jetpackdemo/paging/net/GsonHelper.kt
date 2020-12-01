package com.leo.jetpackdemo.paging.net

import com.google.gson.Gson


object GsonHelper {
    private object GsonHolder {
        val INSTANCE = Gson()
    }

    fun getInstance(): Gson {
        return GsonHolder.INSTANCE
    }
}