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
            "Content-Type" to "application/json",
            "X-Custom-Header" to "application/json"
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