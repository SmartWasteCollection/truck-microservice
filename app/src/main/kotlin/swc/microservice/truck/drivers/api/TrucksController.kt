package swc.microservice.truck.drivers.api

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import swc.microservice.truck.adapters.TruckPresentation.Deserialization.deserialize
import swc.microservice.truck.adapters.TruckPresentation.Deserialization.getAvailability
import swc.microservice.truck.adapters.TruckPresentation.Deserialization.getOccupiedVolume
import swc.microservice.truck.adapters.TruckPresentation.Deserialization.getPosition
import swc.microservice.truck.adapters.TruckPresentation.Deserialization.getTruckId
import swc.microservice.truck.adapters.TruckPresentation.Deserialization.toJsonObject
import swc.microservice.truck.entities.Truck
import swc.microservice.truck.entities.events.AvailabilityUpdateEvent
import swc.microservice.truck.entities.events.OccupiedVolumeUpdateEvent
import swc.microservice.truck.entities.events.PositionUpdateEvent
import swc.microservice.truck.usecases.CreateTruck
import swc.microservice.truck.usecases.DeleteTruck
import swc.microservice.truck.usecases.GetAllTrucks
import swc.microservice.truck.usecases.GetAvailableTrucks
import swc.microservice.truck.usecases.GetInMissionTrucks
import swc.microservice.truck.usecases.GetTruck
import swc.microservice.truck.usecases.TruckManager
import swc.microservice.truck.usecases.UpdateTruck
import java.util.UUID

@RestController
@CrossOrigin
@RequestMapping("/trucks")
class TrucksController(val manager: TruckManager = ManagerSupplier.get()) {

    @GetMapping("/{id}")
    fun getTruck(@PathVariable id: String): Truck? = GetTruck(id).execute(manager)

    @GetMapping("/")
    fun getAllTrucks(): List<Truck> = GetAllTrucks().execute(manager)

    @GetMapping("/available")
    fun getAvailableTrucks(): List<Truck> = GetAvailableTrucks().execute(manager)

    @GetMapping("/inMission")
    fun getInMissionTrucks(): List<Truck> = GetInMissionTrucks().execute(manager)

    @PostMapping("/")
    fun createTruck(@RequestBody body: String): String = CreateTruck(deserialize(body, "Truck-${UUID.randomUUID()}")).execute(manager)

    @PutMapping("/position")
    fun updatePosition(@RequestBody body: String): Truck? = UpdateTruck(body.toJsonObject().getTruckId(), PositionUpdateEvent(body.toJsonObject().getPosition())).execute(manager)

    @PutMapping("/occupiedVolume")
    fun updateOccupiedVolume(@RequestBody body: String): Truck? = UpdateTruck(body.toJsonObject().getTruckId(), OccupiedVolumeUpdateEvent(body.toJsonObject().getOccupiedVolume())).execute(manager)

    @PutMapping("/availability")
    fun updateAvailability(@RequestBody body: String): Truck? = UpdateTruck(body.toJsonObject().getTruckId(), AvailabilityUpdateEvent(body.toJsonObject().getAvailability())).execute(manager)

    @DeleteMapping("/{id}")
    fun deleteTruck(@PathVariable id: String): Truck? = DeleteTruck(id).execute(manager)
}
