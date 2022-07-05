package swc.microservice.truck.entities.events

/**
 * [TruckUpdateEvent] generated when a truck's availability is updated.
 */
class AvailabilityUpdateEvent(override val newValue: Boolean) : TruckUpdateEvent<Boolean>(newValue)
