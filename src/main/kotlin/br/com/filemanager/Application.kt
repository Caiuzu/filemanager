@file:JvmName("Application")

package br.com.filemanager

import br.com.filemanager.usecase.FileManager
import java.io.File

fun main(args: Array<String>) {
    try {
        val fileManager = FileManager()
        val file = File("./docker/aws/bucket-file/abc123")

        // Faça o upload do arquivo "abc123" para o S3
        val fileUrl = fileManager.uploadFile(file)
        println("Arquivo enviado para: $fileUrl")

        // Faça o download do arquivo "abc123" do S3
        val downloadedFile = fileManager.downloadFile(file.name)
        println("Arquivo baixado: ${downloadedFile.name}")

    } finally {
        println("sucesso")
    }
}

