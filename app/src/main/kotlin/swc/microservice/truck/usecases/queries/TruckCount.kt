package swc.microservice.truck.usecases.queries

import swc.microservice.truck.usecases.TruckManager

class TruckCount : TruckQuery<Int> {

    override fun execute(manager: TruckManager): Int = manager.getTruckCount()
}
