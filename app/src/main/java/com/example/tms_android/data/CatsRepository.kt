package com.example.tms_android.data

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CatsRepository {
    private val TAG = "CatsRepository"

    suspend fun fetchStrings(): Result<List<String>> = withContext(Dispatchers.IO) {
        var connection: HttpURLConnection? = null

        try {
            val url = URL("https://meowfacts.herokuapp.com/?count=10")
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val reader = InputStreamReader(inputStream, "UTF-8")
                val response = Gson().fromJson(reader, ApiResponse::class.java)
                Result.success(response.data)
            } else {
                val errorMsg = "HTTP ${connection.responseCode}: ${connection.responseMessage}"
                Log.e(TAG, errorMsg)
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Network error: ${e.message}", e)
            Result.failure(e)
        } finally {
            connection?.disconnect()
        }
    }
}