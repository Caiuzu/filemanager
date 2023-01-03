package br.com.filemanager.adapter.service

import br.com.filemanager.infrastructure.rest.FileManagerAPIRestClient
import br.com.filemanager.infrastructure.rest.exception.RestServerError
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@Tag(name = "File Manager API")
class FileManagerAPIService(private val apiClient: FileManagerAPIRestClient) {
    @Operation(summary = "Upload file to API")
    @ApiResponses(
            ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            ApiResponse(responseCode = "500", description = "Internal server error")
    )
    fun uploadFile(@Parameter(description = "File to be uploaded") file: File): String {
        val fileBody = file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
        val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.name, fileBody)
                .build()

        val request = Request.Builder()
                .url("https://localhost:8080/upload")
                .post(requestBody)
                .build()

        val response = apiClient.makeRequest(request)

        if (!response.isSuccessful) {
            throw RestServerError("Error uploading file to API")
        }

        //return response.body!!.string()
        return "http://localhost:8080/s3/${file.name}"
    }
}
