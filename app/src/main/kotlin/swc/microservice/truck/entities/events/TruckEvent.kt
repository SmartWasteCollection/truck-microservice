package swc.microservice.truck.entities.events

interface TruckEvent<T> {
    val newValue: T
}