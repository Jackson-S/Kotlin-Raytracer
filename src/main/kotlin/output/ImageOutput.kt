package output

import datatypes.Colour

interface ImageOutput {
    val width: Int
    val height: Int

    fun write(image: List<List<Colour>>)
}
