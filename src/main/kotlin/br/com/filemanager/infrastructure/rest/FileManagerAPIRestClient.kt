package br.com.filemanager.infrastructure.rest

import okhttp3.Request
import okhttp3.Response

class FileManagerAPIRestClient(private val restClient: RestClient) {
    fun makeRequest(request: Request): Response {
        return restClient.makeRequest(request)
    }
}