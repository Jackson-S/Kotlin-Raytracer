package shapes.impl

import datatypes.*
import materials.Material
import shapes.Shape
import kotlin.math.pow
import kotlin.math.sqrt

class Sphere(
    override val movement: Movement,
    private val radius: Double,
    val material: Material
) : Shape {

    /**
     * Constructor for a motionless sphere
     */
    constructor(
        center: Point3,
        radius: Double,
        material: Material
    ) : this(
        movement = Movement(center),
        radius = radius,
        material = material
    )

    constructor(
        positions: Pair<Point3, Point3>,
        timeRange: ClosedFloatingPointRange<Double>,
        radius: Double,
        material: Material
    ) : this(
        movement = Movement(positions.first, positions.second, timeRange.start, timeRange.endInclusive),
        radius = radius,
        material = material)

    override fun hit(ray: Ray, tRange: ClosedFloatingPointRange<Double>): HitRecord? {
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

    override fun boundingBox(time: ClosedFloatingPointRange<Double>): AxisAlignedBoundingBox {
        val box0 = AxisAlignedBoundingBox(
            center(movement.startTime) - Vec3(radius, radius, radius),
            center(movement.startTime) + Vec3(radius, radius, radius)
        )
        val box1 = AxisAlignedBoundingBox(
            center(movement.endTime) - Vec3(radius, radius, radius),
            center(movement.endTime) + Vec3(radius, radius, radius)
        )
        return AxisAlignedBoundingBox.surroundingBox(box0, box1)
    }

    private fun center(time: Double): Point3 =
        if (movement.hasMovement) {
            movement.startPosition + ((time - movement.startTime) / movement.duration) * (movement.endPosition!! - movement.startPosition)
        } else {
            movement.startPosition
        }


    private operator fun Double.div(other: Vec3) = other * (1 / this)

    private operator fun Double.times(other: Vec3) = other * this
}
