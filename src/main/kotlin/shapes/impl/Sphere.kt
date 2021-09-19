package shapes.impl

import datatypes.HitRecord
import datatypes.Point3
import datatypes.Ray
import datatypes.Vec3
import materials.Material
import shapes.Shape
import kotlin.math.pow
import kotlin.math.sqrt

class Sphere(
    private val centerStart: Point3,
    private val centerEnd: Point3,
    private val duration: Double,
    private val radius: Double,
    override val material: Material,
) : Shape {

    constructor(center: Point3, radius: Double, material: Material) : this(center, center, 0.0, radius, material)

    override fun hit(ray: Ray, tRange: ClosedRange<Double>): HitRecord? {
        val oc = ray.origin - center(ray.time)
        val a = ray.direction.lengthSquared()
        val halfB = oc dot ray.direction
        val c = oc.lengthSquared() - radius.pow(2)
        val discriminant = halfB.pow(2) - a * c

        if (discriminant < 0) return null

        val sqrtD = sqrt(discriminant)
        val root = (-halfB - sqrtD) / a

        if (!tRange.contains(root)) {
            return null
        }

        val p = ray.at(root)

        return HitRecord(
            t = root,
            p = p,
            ray = ray,
            outwardNormal = (p - center(ray.time)) / radius,
            material = material
        )
    }

    private fun center(time: Double): Point3 = centerStart + (duration / time) * (centerEnd - centerStart)

    private operator fun Double.div(other: Vec3) = other * (1 / this)

    private operator fun Double.times(other: Vec3) = other * this
}
