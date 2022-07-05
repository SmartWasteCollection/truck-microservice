package swc.microservice.truck.usecases.queries

import swc.microservice.truck.usecases.TruckManager

class TruckNextId : TruckQuery<String> {
    override fun execute(manager: TruckManager): String = manager.getTruckNextId()
}