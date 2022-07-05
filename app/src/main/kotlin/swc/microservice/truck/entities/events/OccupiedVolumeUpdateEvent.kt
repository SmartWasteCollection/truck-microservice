package swc.microservice.truck.entities.events

import swc.microservice.truck.entities.Volume

/**
 * [TruckUpdateEvent] generated when a truck's occupied[Volume] is updated.
 */
class OccupiedVolumeUpdateEvent(override val newValue: Volume) : TruckUpdateEvent<Volume>(newValue)