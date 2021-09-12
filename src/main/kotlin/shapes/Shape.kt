package shapes

import datatypes.HitRecord
import datatypes.Ray
import materials.Material

interface Shape {
    val material: Material?

    fun hit(ray: Ray, tRange: ClosedRange<Double>): HitRecord?

    fun output(): String
}
