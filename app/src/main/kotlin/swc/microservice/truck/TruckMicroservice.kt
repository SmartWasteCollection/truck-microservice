package swc.microservice.truck

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class TruckMicroservice

fun main(args: Array<String>) {
    runApplication<TruckMicroservice>(*args)
}
