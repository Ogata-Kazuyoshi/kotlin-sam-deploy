package helloworld

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock

class AppTest {
    @Test
    fun successfulResponse() {
        val app = App()
        val input = APIGatewayProxyRequestEvent()
        val context = mock(Context::class.java)
        val result = app.handleRequest(input, context)
        assertEquals(200, result.statusCode)
        assertEquals("application/json", result.headers["Content-Type"])
        val content = result.body
        assertNotNull(content)
        assertTrue(content.contains("\"message\""))
        assertTrue(content.contains("\"hello world\""))
        assertTrue(content.contains("\"location\""))
    }
}