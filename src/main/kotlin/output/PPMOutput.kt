package output

import datatypes.Colour
import datatypes.Vec3
import kotlin.math.pow

class PPMOutput(
    override val width: Int,
    override val height: Int,
    override val gamma: Double = 2.0,
) : ImageOutput {
    override val fileExtension: String
        get() = "ppm"

    override fun output(image: List<List<Colour>>): ByteArray {
        return "P3\n$width $height\n255\n${image.flatMap { line ->
            line.map { pixel ->
                pixel.pixelColor()
            }
        }}".toByteArray()
    }

    private fun Vec3.pixelColor(): String {
        val r = x.pow(1 / gamma) * 255.999
        val g = y.pow(1 / gamma) * 255.999
        val b = z.pow(1 / gamma) * 255.999
        return "${(r).toInt()} ${(g).toInt()} ${(b).toInt()}\n"
    }
}
