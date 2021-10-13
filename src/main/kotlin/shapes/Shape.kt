package shapes

import datatypes.AxisAlignedBoundingBox
import datatypes.HitRecord
import datatypes.Ray
import datatypes.Movement

interface Shape {

    val movement: Movement

    fun hit(ray: Ray, tRange: ClosedFloatingPointRange<Double>): HitRecord?

    fun boundingBox(time: ClosedFloatingPointRange<Double>): AxisAlignedBoundingBox?
}
