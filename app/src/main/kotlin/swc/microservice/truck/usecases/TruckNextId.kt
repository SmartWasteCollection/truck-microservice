package swc.microservice.truck.usecases

class TruckNextId : TruckUseCase<String> {
    override fun execute(manager: TruckManager): String = "Truck${manager.getTruckCount()}"
}
