package swc.microservice.truck.drivers.api

import swc.microservice.truck.drivers.digitaltwins.TruckDigitalTwinManager
import swc.microservice.truck.usecases.TruckManager

object ManagerSupplier {

    fun get(): TruckManager = TruckDigitalTwinManager()
}
