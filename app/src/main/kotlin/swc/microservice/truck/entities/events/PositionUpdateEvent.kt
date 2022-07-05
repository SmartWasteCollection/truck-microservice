package swc.microservice.truck.entities.events

import swc.microservice.truck.entities.Position

class PositionUpdateEvent(override val newValue: Position) : TruckEvent<Position>