package swc.microservice.truck.usecases

interface TruckUseCase<T> {
    fun execute(manager: TruckManager): T
}
