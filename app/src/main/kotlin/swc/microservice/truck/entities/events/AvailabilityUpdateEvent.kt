package swc.microservice.truck.entities.events

class AvailabilityUpdateEvent(override val newValue: Boolean) : TruckEvent<Boolean>