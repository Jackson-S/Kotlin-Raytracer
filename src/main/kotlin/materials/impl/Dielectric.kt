package materials.impl

import datatypes.Colour
import datatypes.HitRecord
import datatypes.Ray
import datatypes.Vec3
import materials.Material
import java.lang.Double.min
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class Dielectric(
    private val indexOfRefraction: Double
): Material {
    override val name: String
        get() = "dielectric"

    override val output: String
        get() = "M $name $indexOfRefraction"

    override fun scatter(rayIn: Ray, hitRecord: HitRecord): Pair<Ray, Colour>? {
        val refractionRatio = when (hitRecord.frontFace) {
            true -> 1.0 / indexOfRefraction
            false -> indexOfRefraction
        }
        val unitDirection = rayIn.direction.unitVector()

        val cosTheta = min(-unitDirection dot hitRecord.normal, 1.0)
        val sinTheta = sqrt(1.0 - cosTheta.pow(2))

        val direction = when (refractionRatio * sinTheta > 1.0 || reflectance(cosTheta, refractionRatio) > Random.nextDouble()) {
            true -> unitDirection.reflect(hitRecord.normal) // Cannot refract or reflect
            false -> unitDirection.refract(hitRecord.normal, refractionRatio)
        }

        return Pair(
            Ray(hitRecord.p, direction),
            Colour(1.0, 1.0, 1.0)
        )
    }

    private fun reflectance(cosine: Double, reflectiveIndex: Double): Double {
        // Use Schlick's approximation for reflectance.
        val r0 = ((1 - reflectiveIndex) / (1 + reflectiveIndex)).pow(2)
        return r0 + (1 - r0) * (1 - cosine).pow(5)
    }
}