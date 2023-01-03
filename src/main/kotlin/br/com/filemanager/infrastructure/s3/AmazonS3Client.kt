package br.com.filemanager.infrastructure.s3


import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.S3Object
import com.typesafe.config.ConfigFactory
import java.io.File
import java.io.FileOutputStream

class AmazonS3Client {

    private val s3: AmazonS3
    val bucketName: String

    init {
        val config = ConfigFactory.load()
        val accessKey = config.getString("aws.accessKey")
        val secretKey = config.getString("aws.secretKey")
        val region = config.getString("aws.region")
        val endpoint = config.getString("aws.endpoint")
        bucketName = config.getString("aws.s3.bucketName")

        val credentials = BasicAWSCredentials(accessKey, secretKey)
        s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .withCredentials(AWSStaticCredentialsProvider(credentials))
                .build()
    }

    fun downloadFile(bucketName: String, fileKey: String): File {
        val s3Object = s3.getObject(GetObjectRequest(bucketName, fileKey))
        return convertS3ObjectToFile(s3Object)
    }

    fun uploadFile(bucketName: String, fileKey: String, file: File) {
        val request = PutObjectRequest(bucketName, fileKey, file)
        s3.putObject(request)
    }

    private fun convertS3ObjectToFile(s3Object: S3Object): File {
        val file = File.createTempFile("s3-object", "tmp")
        file.deleteOnExit()

        FileOutputStream(file).use { outputStream ->
            s3Object.objectContent.use { inputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        return file
    }
}

/*

import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

// Crie um DataFrame com alguns dados de exemplo
val df = spark.createDataFrame(Seq((1, "Alice"), (2, "Bob"), (3, "Charlie")))

// Defina o nome do bucket e o prefixo da chave do arquivo no S3
val bucketName = "my-bucket"
val fileKeyPrefix = "dados/exemplo"

// Salve o DataFrame como um arquivo de texto no S3
df.write.text("s3a://$bucketName/$fileKeyPrefix")

 */