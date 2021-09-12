package materials.impl

import datatypes.Colour
import datatypes.HitRecord
import datatypes.Ray
import datatypes.Vec3
import materials.Material

class Lambertian(
    val albedo: Colour
) : Material {

    override val name: String
        get() = "lambertian"

    override val output: String
        get() = "M $name ${albedo.x} ${albedo.y} ${albedo.z}"

    override fun scatter(rayIn: Ray, hitRecord: HitRecord): Pair<Ray, Colour> {
        var scatterDirection = hitRecord.normal + Vec3.randomUnitVector()

        // Catch degenerate scatter direction
        if (scatterDirection.nearZero()) {
            scatterDirection = hitRecord.normal
        }

        return Pair(
            Ray(hitRecord.p, scatterDirection),
            albedo
        )
    }
}
