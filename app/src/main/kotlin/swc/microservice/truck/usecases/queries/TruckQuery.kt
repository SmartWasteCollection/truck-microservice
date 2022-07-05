package swc.microservice.truck.usecases.queries

import swc.microservice.truck.usecases.TruckManager

interface TruckQuery<T> {
    fun execute(manager: TruckManager): T
}
