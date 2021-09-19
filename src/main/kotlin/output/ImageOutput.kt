package output

import datatypes.Colour

interface ImageOutput {
    val width: Int
    val height: Int
    val gamma: Double
    val fileExtension: String

    fun output(image: List<List<Colour>>): ByteArray
}
