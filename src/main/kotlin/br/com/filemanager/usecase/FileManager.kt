package br.com.filemanager.usecase

import br.com.filemanager.adapter.service.FileManagerAPIService
import br.com.filemanager.adapter.service.StorageService
import br.com.filemanager.infrastructure.rest.FileManagerAPIRestClient
import br.com.filemanager.infrastructure.rest.HttpClientFactory
import br.com.filemanager.infrastructure.rest.RestClient
import br.com.filemanager.infrastructure.s3.AmazonS3Client
import java.io.File

class FileManager {
    private val httpClientFactory = HttpClientFactory()
    private val restClient = RestClient(httpClientFactory.create())
    private val apiClient = FileManagerAPIRestClient(restClient)
    private val s3Client = AmazonS3Client()
    private val storageService = StorageService(s3Client)
    private val apiService = FileManagerAPIService(apiClient)

    fun downloadFile(fileKey: String): File {
        return s3Client.downloadFile(s3Client.bucketName, fileKey)
    }

    fun uploadFile(file: File) {
        //val fileUrl = apiService.uploadFile(file)
        //storageService.saveFile(fileUrl)
        storageService.saveFile(file)
    }

}