package datatypes

import materials.Material

class HitRecord(
    val p: Point3,
    val t: Double,
    val material: Material,
    ray: Ray,
    outwardNormal: Vec3
) : Comparable<HitRecord> {
    val frontFace: Boolean
    val normal: Vec3

    init {
        frontFace = ray.direction dot outwardNormal < 0
        normal = when (frontFace) {
            true -> outwardNormal
            false -> -outwardNormal
        }
    }

    override fun compareTo(other: HitRecord): Int {
        return when {
            this.t > other.t -> -1
            this.t < other.t -> 1
            else -> 0
        }
    }
}
