package swc.microservice.truck.entities.events

import swc.microservice.truck.entities.Volume

class VolumeUpdateEvent(override val newValue: Volume) : TruckEvent<Volume>