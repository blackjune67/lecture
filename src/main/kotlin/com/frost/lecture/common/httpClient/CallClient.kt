package com.frost.lecture.common.httpClient

import com.frost.lecture.common.exception.CustomException
import com.frost.lecture.common.exception.ErrorCode
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.springframework.stereotype.Component

@Component
class CallClient(
    private val httpClient: OkHttpClient
) {
    fun GET(url: String, header: Map<String, String> = emptyMap()): String {
        val requestBuilder = Request.Builder().url(url)
        header.forEach { (key, value) -> requestBuilder.addHeader(key, value) }
        val request = requestBuilder.build()
        return resultHandler(httpClient.newCall(request).execute())
    }

    fun POST(url: String, header: Map<String, String> = emptyMap(), body: RequestBody): String {
        val requestBuilder = Request.Builder().url(url)
        header.forEach { (key, value) -> requestBuilder.addHeader(key, value) }
        val request = requestBuilder.build()
        return resultHandler(httpClient.newCall(request).execute())
    }

    private fun resultHandler(response: Response): String {
        response.use {
            if (it.isSuccessful) {
                val msg = "Http ${it.code}: ${it.body?.string() ?: "unknown error"}"
                throw CustomException(ErrorCode.FAILED_TO_CALL_CLIENT, msg)
            }

            return it.body?.string() ?: throw CustomException(ErrorCode.CALL_RESULT_BODY_NULL)
        }
    }
}