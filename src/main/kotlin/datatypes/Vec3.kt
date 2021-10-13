package datatypes

import extensions.times
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sqrt
import kotlin.random.Random

class Vec3(var x: Double = .0, var y: Double = .0, var z: Double = .0) {

    constructor(x: Int, y: Int, z: Int) : this(x.toDouble(), y.toDouble(), z.toDouble())
    constructor(x: Double, y: Int, z: Int) : this(x, y.toDouble(), z.toDouble())
    constructor(x: Int, y: Double, z: Int) : this(x.toDouble(), y, z.toDouble())
    constructor(x: Int, y: Int, z: Double) : this(x.toDouble(), y.toDouble(), z)
    constructor(x: Double, y: Double, z: Int) : this(x, y, z.toDouble())
    constructor(x: Int, y: Double, z: Double) : this(x.toDouble(), y, z)
    constructor(x: Double, y: Int, z: Double) : this(x, y.toDouble(), z)

    companion object {
        fun random() = Vec3(Random.nextDouble(), Random.nextDouble(), Random.nextDouble())

        fun random(min: Double, max: Double) = Vec3(
            Random.nextDouble(min, max),
            Random.nextDouble(min, max),
            Random.nextDouble(min, max)
        )

        fun randomInUnitSphere(): Vec3 {
            while (true) {
                val p = random(-1.0, 1.0)

                if (p.lengthSquared() >= 1) {
                    continue
                }

                return p
            }
        }

        fun randomUnitVector() = randomInUnitSphere().unitVector()

        fun randomInHemisphere(normal: Vec3): Vec3 {
            val inUnitSphere = randomInUnitSphere()
            return when (inUnitSphere dot normal > 0.0) {
                true -> inUnitSphere
                false -> -inUnitSphere
            }
        }

        fun randomInUnitDisk(): Vec3 {
            while (true) {
                val p = Vec3(Random.nextDouble(-1.0, 1.0), Random.nextDouble(-1.0, 1.0), .0)
                if (p.lengthSquared() >= 1) continue
                return p
            }
        }

        fun randomInDefaultSceneView() = Vec3(
            Random.nextDouble(-2.0, 2.0),
            Random.nextDouble(-1.0, 1.0),
            Random.nextDouble(-1.4, -1.0)
        )
    }

    var r
        get() = x
        set(value) { x = value }

    var g
        get() = y
        set(value) { y = value }

    var b
        get() = z
        set(value) { z = value }

    operator fun unaryMinus() = Vec3(-x, -y, -z)

    operator fun get(index: Int) = when (index) {
        0 -> x
        1 -> y
        2 -> z
        else -> throw IndexOutOfBoundsException()
    }

    operator fun plusAssign(other: Vec3) {
        x += other.x
        y += other.y
        z += other.z
    }

    operator fun timesAssign(value: Double) {
        x *= value
        y *= value
        z *= value
    }

    operator fun divAssign(value: Double) {
        this *= 1 / value
    }

    operator fun plus(other: Vec3) = Vec3(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Vec3) = Vec3(x - other.x, y - other.y, z - other.z)

    operator fun times(other: Vec3) = Vec3(x * other.x, y * other.y, z * other.z)

    operator fun times(value: Double) = Vec3(x * value, y * value, z * value)

    operator fun div(value: Double) = (1 / value) * this

    infix fun dot(other: Vec3) = x * other.x + y * other.y + z * other.z

    infix fun cross(other: Vec3) = Vec3(
        y * other.z - z * other.y,
        z * other.x - x * other.z,
        x * other.y - y * other.x
    )

    fun unitVector() = this / this.length()

    fun length() = sqrt(lengthSquared())

    fun lengthSquared() = x * x + y * y + z * z

    fun nearZero(tolerance: Double = 1e-8) = abs(x) < tolerance && abs(y) < tolerance && abs(z) < tolerance

    fun reflect(normal: Vec3) = this - 2 * (this dot normal) * normal

    fun refract(normal: Vec3, etaiOverEtat: Double): Vec3 {
        val cosTheta = min(-this dot normal, 1.0)
        val rOutPerpendicular = etaiOverEtat * (this + cosTheta * normal)
        val rOutParallel = -sqrt(abs(1.0 - rOutPerpendicular.lengthSquared())) * normal
        return rOutPerpendicular + rOutParallel
    }
}
