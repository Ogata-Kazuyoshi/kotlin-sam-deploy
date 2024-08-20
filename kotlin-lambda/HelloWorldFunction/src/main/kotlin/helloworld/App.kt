package helloworld

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL



class App : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    override fun handleRequest(input: APIGatewayProxyRequestEvent, context: Context): APIGatewayProxyResponseEvent {
        val headers = mapOf(
        "Access-Control-Allow-Origin" to "*",
        "Access-Control-Allow-Headers" to "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token",
        "Access-Control-Allow-Methods" to "GET,POST,PUT,DELETE,OPTIONS"
    )

        val response = APIGatewayProxyResponseEvent().withHeaders(headers)
        println("バックエンドのここまでは届いてます")

        return try {
            val pageContents = getPageContents("https://checkip.amazonaws.com")
            val output = "{ \"message\": \"hello world\", \"location\": \"$pageContents\" }"

            response
                .withStatusCode(200)
                .withBody(output)
        } catch (e: IOException) {
            println("エラーが発生しました: ${e.message}")
            response
                .withBody("{\"error\": \"${e.message}\"}")
                .withStatusCode(500)
        }
    }

    @Throws(IOException::class)
    private fun getPageContents(address: String): String {
        val url = URL(address)
        return url.openStream().use { stream ->
            BufferedReader(InputStreamReader(stream)).use { reader ->
                reader.readText()
            }
        }
    }
}

//package helloworld
//
//import org.springframework.boot.autoconfigure.SpringBootApplication
//import org.springframework.boot.runApplication
//import org.springframework.context.annotation.Bean
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.RestController
//import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
//import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
//import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler
//
//@SpringBootApplication
//
//class App {
//    @Bean
//    fun hello() = { input: String -> "Hello, $input!" }
//}
//
//fun main(args: Array<String>) {
//    runApplication<App>(*args)
//}
//
//class LambdaHandler : SpringBootRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>()
//
//@RestController
//class HelloController {
//    @GetMapping("/hello")
//    fun hello(): String {
//        return "Hello, World!"
//    }
//}