package datatypes

import extensions.times
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.math.tan
import kotlin.random.Random

class Camera(
    val verticalFieldOfView: Double,
    val origin: Point3 = Point3(),
    val target: Point3 = Point3(),
    val rotation: Vec3 = Vec3(y = 1.0),
    val aspectRatio: Double,
    val aperture: Double,
    val focalDistance: Double,
    val shutterLength: Double = 0.0,
) {
    val w = (origin - target).unitVector()
    val u = (rotation cross w).unitVector()
    val v = w cross u

    private val viewportHeight = 2.0 * tan(Math.toRadians(verticalFieldOfView) / 2.0)
    private val viewportWidth = aspectRatio * viewportHeight
    private val horizontal = focalDistance * viewportWidth * u
    private val vertical = focalDistance * viewportHeight * v
    private val lowerLeftCorner = origin - horizontal / 2.0 - vertical / 2.0 - focalDistance * w
    private val lensRadius = aperture / 2

    fun render(
        scene: Scene,
        imageWidth: Int,
        pixelSamples: Int,
        maximumDepth: Int
    ): List<List<Colour>> {
        val imageHeight = (imageWidth / aspectRatio).toInt()
        var linesRemaining = imageHeight

        return (0 until imageHeight).reversed().parallelMap { j ->
            linesRemaining -= 1
            printProgress(linesRemaining)
            (0 until imageWidth).map { i ->
                val result = Colour()
                repeat(pixelSamples) {
                    val u = (i.toDouble() + Random.nextDouble()) / (imageWidth - 1)
                    val v = (j.toDouble() + Random.nextDouble()) / (imageHeight - 1)
                    val ray = getRay(u, v)
                    result += ray.colour(scene, maximumDepth)
                }
                result / pixelSamples.toDouble()
            }
        }
    }

    private fun getRay(s: Double, t: Double): Ray {
        val rd = lensRadius * Vec3.randomInUnitDisk()
        val offset = u * rd.x + v * rd.y

        return Ray(
            origin = origin + offset,
            direction = lowerLeftCorner + s * horizontal + t * vertical - origin - offset,
            time = if (shutterLength == 0.0) 0.0 else Random.nextDouble(0.0, shutterLength))
    }

    private fun printProgress(linesRemaining: Int) {
        print("\rLines remaining: $linesRemaining ")
        System.out.flush()
        if (linesRemaining == 0) {
            println()
        }
    }

    private fun <A> IntProgression.parallelMap(f: suspend (Int) -> A): List<A> = runBlocking(Dispatchers.Default) {
        map { async { f(it) } }.map { it.await() }
    }
}
