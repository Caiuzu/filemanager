package br.com.filemanager.adapter.service

import br.com.filemanager.adapter.exception.S3OperationException
import br.com.filemanager.infrastructure.s3.AmazonS3Client
import java.io.File

class StorageService(private val s3Client: AmazonS3Client) {
    fun saveFile(file: File) {
        try {
            s3Client.uploadFile(s3Client.bucketName, file.name, file)
        } catch (e: Exception) {
            throw S3OperationException("Error saving file to S3 bucket")
        }
    }
}
