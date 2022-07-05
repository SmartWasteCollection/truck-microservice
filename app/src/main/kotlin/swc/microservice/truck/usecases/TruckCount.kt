package swc.microservice.truck.usecases

class TruckCount : TruckUseCase<Int> {

    override fun execute(manager: TruckManager): Int = manager.getTruckCount()
}
