package br.com.filemanager.infrastructure.rest

import okhttp3.OkHttpClient

class HttpClientFactory {
    fun create(): OkHttpClient {
        return OkHttpClient()
    }
}