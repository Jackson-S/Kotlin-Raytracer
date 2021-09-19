package materials.impl

import datatypes.Colour
import datatypes.HitRecord
import datatypes.Ray
import datatypes.Vec3
import materials.Material

class Metal(
    private val albedo: Colour,
    private val fuzz: Double
) : Material {

    override val name: String
        get() = "metal"

    override val output: String
        get() = "M $name ${albedo.x} ${albedo.y} ${albedo.z} $fuzz"

    override fun scatter(rayIn: Ray, hitRecord: HitRecord): Pair<Ray, Colour>? {
        val reflected = rayIn.direction.unitVector().reflect(hitRecord.normal)
        val scattered = Ray(hitRecord.p, reflected + Vec3.randomInUnitSphere() * fuzz, rayIn.time)
        return when (scattered.direction dot hitRecord.normal > 0) {
            true -> Pair(scattered, albedo)
            false -> null
        }
    }
}
