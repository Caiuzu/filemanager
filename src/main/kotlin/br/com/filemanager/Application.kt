@file:JvmName("Application")

package br.com.filemanager

import br.com.filemanager.usecase.FileManager
import java.io.File

fun main(args: Array<String>) {
    try {
        val fileManager = FileManager()
        val file = File("./abc123")

        // Faça o upload do arquivo "abc123" para o S3
        fileManager.uploadFile(file)

        // Faça o download do arquivo "abc123" do S3
        fileManager.downloadFile("abc123")

    } finally {
        println("sucesso")
    }
}

