package swc.microservice.truck

import io.github.cdimascio.dotenv.dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class TruckMicroservice

const val PORT_KEY = "server.port"
const val DEFAULT_PORT = "3001"

fun main() {
    val dotenv = dotenv {
        ignoreIfMissing = true
    }
    System.setProperty(PORT_KEY, dotenv[PORT_KEY] ?: DEFAULT_PORT)
    runApplication<TruckMicroservice>()
}
