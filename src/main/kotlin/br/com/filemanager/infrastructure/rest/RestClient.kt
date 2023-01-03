package br.com.filemanager.infrastructure.rest

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class RestClient(private val httpClient: OkHttpClient) {
    fun makeRequest(request: Request): Response {
        return httpClient.newCall(request).execute()
    }
}