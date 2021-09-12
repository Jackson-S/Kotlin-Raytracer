package shapes.impl

import datatypes.HitRecord
import datatypes.Point3
import datatypes.Ray
import datatypes.Scene
import materials.Material
import shapes.Shape

class EmptySphere(
    private val center: Point3,
    private val radius: Double,
    private val thickness: Double,
    override val material: Material
) : Shape {
    private val scene = Scene(
        mutableListOf(
            Sphere(center, radius, material),
            Sphere(center, -(radius - thickness), material)
        )
    )

    override fun hit(ray: Ray, tRange: ClosedRange<Double>): HitRecord? = scene.hit(ray, tRange)

    override fun output() = "S emptySphere ${center.x} ${center.y} ${center.z} $radius $thickness"
}
