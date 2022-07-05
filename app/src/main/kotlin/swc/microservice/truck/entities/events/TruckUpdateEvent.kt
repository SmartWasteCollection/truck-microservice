package swc.microservice.truck.entities.events

/**
 * An update event in the domain of trucks.
 */
open class TruckUpdateEvent<T>(open val newValue: T)