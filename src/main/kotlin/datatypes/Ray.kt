package datatypes

import extensions.times

class Ray(
    val origin: Point3,
    val direction: Vec3
) {
    fun at(t: Double) = origin + t * direction

    fun colour(scene: Scene, depth: Int): Vec3 {
        if (depth <= 0)
            return Colour()

        scene.hit(this, 0.001..Double.POSITIVE_INFINITY)?.let { hitRecord ->
            hitRecord.material.scatter(this, hitRecord)?.let { (ray, colour) ->
                return colour * ray.colour(scene, depth - 1)
            }

            return Colour()
        }

        val unitDirection = this.direction.unitVector()
        val t = 0.5 * (unitDirection.y + 1.0)
        return (1.0 - t) * Colour(1.0, 1.0, 1.0) + t * Colour(0.5, 0.7, 1.0)
    }
}
