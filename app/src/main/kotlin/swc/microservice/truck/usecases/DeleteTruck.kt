package swc.microservice.truck.usecases

class DeleteTruck(private val truckId: String) : TruckUseCase<Unit> {
    override fun execute(manager: TruckManager) {
        manager.deleteTruck(truckId)
    }
}
