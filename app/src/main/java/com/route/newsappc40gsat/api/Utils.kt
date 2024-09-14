package com.route.newsappc40gsat.api

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.route.newsappc40gsat.R
import com.route.newsappc40gsat.model.SourcesItem
import okio.IOException
import retrofit2.HttpException
import java.net.UnknownHostException


fun handleError(t: Throwable): Int {
    return when (t) {
        is UnknownHostException, is IOException -> {
            // show Dialog to the user , internet connection error
            Log.e("TAG", "handleError: Unknown or IO Exception : ${t.message} ")
            R.string.there_is_problem_with_your_internet_connection_kindly_check_it
        }

        is HttpException -> {
            // show Dialog to the user , client or server error
            Log.e("TAG", "handleError: HTTP Exception  ${t.code()}")
            R.string.client_or_server_error
        }

        is Exception -> {
            // show Dialog , something went wrong
            Log.e("TAG", "handleError:  ${t.message}")
            R.string.something_went_wrong
        }

        else -> {
            R.string.something_went_wrong
        }
    }

}


fun <T> String.fromJson(clazz: Class<T>): T {
    val gson = Gson()
    return gson.fromJson(this, clazz)
}