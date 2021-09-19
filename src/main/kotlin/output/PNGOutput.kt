package output

import com.soywiz.korim.bitmap.Bitmap32
import com.soywiz.korim.bitmap.NativeImage
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.color.RgbaArray
import com.soywiz.korim.format.ImageEncodingProps
import com.soywiz.korim.format.PNG
import datatypes.Colour
import datatypes.Vec3
import kotlin.math.pow

class PNGOutput(
    override val width: Int,
    override val height: Int,
    override val gamma: Double = 2.0
): ImageOutput {
    override val fileExtension: String
        get() = "png"

    override fun output(image: List<List<Colour>>): ByteArray {
        val imageData = NativeImage(width, height)

        image.forEachIndexed { y, line ->
            line.forEachIndexed { x, pixel ->
                imageData.setRgba(x, y, pixel.pixelColor())
            }
        }

        return PNG.encode(imageData.toNonNativeBmp())
    }

    private fun Vec3.pixelColor(): RGBA {
        val r = (x.pow(1 / gamma) * 255.999).toInt()
        val g = (y.pow(1 / gamma) * 255.999).toInt()
        val b = (z.pow(1 / gamma) * 255.999).toInt()
        return RGBA(r, g, b)
    }
}