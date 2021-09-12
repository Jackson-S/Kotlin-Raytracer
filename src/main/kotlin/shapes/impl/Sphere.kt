package shapes.impl

import datatypes.HitRecord
import datatypes.Point3
import datatypes.Ray
import materials.Material
import shapes.Shape
import kotlin.math.pow
import kotlin.math.sqrt

class Sphere(
    private val center: Point3,
    private val radius: Double,
    override val material: Material
) : Shape {
    override fun hit(ray: Ray, tRange: ClosedRange<Double>): HitRecord? {
        val oc = ray.origin - center
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
            outwardNormal = (p - center) / radius,
            material = material
        )
    }

    // Shape X Y Z Size Material R G B [Diffuse]
    override fun output(): String =
        "S sphere ${center.x} ${center.y} ${center.z} $radius"
}
