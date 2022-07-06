package swc.microservice.truck.drivers.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import swc.microservice.truck.adapters.TruckPresentation.Deserialization.deserialize
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.usecases.CreateTruck
import swc.microservice.truck.usecases.GetAvailableTrucks
import swc.microservice.truck.usecases.GetTruck
import swc.microservice.truck.usecases.TruckCount
import swc.microservice.truck.usecases.TruckManager
import swc.microservice.truck.usecases.TruckNextId

@RestController
@RequestMapping("/trucks")
class TrucksController(val manager: TruckManager = ManagerSupplier.get()) {

    @GetMapping("/{id}")
    fun getTruck(@PathVariable id: String): Truck? = GetTruck(id).execute(manager)

    @GetMapping("/available")
    fun getAvailableTrucks(): List<Truck> = GetAvailableTrucks().execute(manager)

    @GetMapping("/count")
    fun getTruckCount(): Int = TruckCount().execute(manager)

    @PostMapping("/")
    fun createTruck(@RequestBody body: String) = CreateTruck(deserialize(body, TruckNextId().execute(manager))).execute(manager)
}
