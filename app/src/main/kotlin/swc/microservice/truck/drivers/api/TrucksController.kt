package swc.microservice.truck.drivers.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.usecases.GetTruck
import swc.microservice.truck.usecases.TruckManager

@RestController
@RequestMapping("/trucks")
class TrucksController(val manager: TruckManager = ManagerSupplier.get()) {

    @GetMapping("/{id}")
    fun getTruck(@PathVariable id: String): Truck? = GetTruck(id).execute(manager)
}
