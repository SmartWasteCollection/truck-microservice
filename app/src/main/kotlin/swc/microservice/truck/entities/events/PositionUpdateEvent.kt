package swc.microservice.truck.entities.events

import swc.microservice.truck.entities.Position

/**
 * [TruckUpdateEvent] generated when a truck's [Position] is updated.
 */
class PositionUpdateEvent(override val newValue: Position) : TruckUpdateEvent<Position>(newValue)