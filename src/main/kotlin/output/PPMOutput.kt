package output

import datatypes.Colour
import datatypes.Vec3
import java.nio.file.Path
import kotlin.math.pow

class PPMOutput(
    override val width: Int,
    override val height: Int,
    private val outputPath: Path,
    private val gamma: Double = 2.0,
) : ImageOutput {

    override fun write(image: List<List<Colour>>) {
        val file = outputPath.toFile().writer(charset = Charsets.US_ASCII)

        file.write("P3\n$width $height\n255\n")

        image.forEach { line ->
            line.forEach { pixel ->
                file.write(pixel.pixelColor())
            }
        }

        file.close()
    }

    private fun Vec3.pixelColor(): String {
        val r = x.pow(1 / gamma) * 255.999
        val g = y.pow(1 / gamma) * 255.999
        val b = z.pow(1 / gamma) * 255.999
        return "${(r).toInt()} ${(g).toInt()} ${(b).toInt()}\n"
    }
}
