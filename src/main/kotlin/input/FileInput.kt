package input

import datatypes.Scene
import datatypes.Vec3
import materials.Material
import materials.impl.Dielectric
import materials.impl.Lambertian
import materials.impl.Metal
import shapes.impl.EmptySphere
import shapes.impl.Sphere
import java.nio.file.Path
import kotlin.io.path.reader

class FileInput(
    private val path: Path
) : Input {
    override fun load(): Scene {
        val file = path.reader(Charsets.UTF_8)

        val scene = Scene()

        // S Shape X Y Z Size
        // M Material R G B [Diffuse]
        var currentMaterial: Material? = null
        file.forEachLine {
            when (it[0]) {
                'S' -> {
                    val line = it.split(" ")
                    val material = currentMaterial ?: throw IllegalArgumentException("Material must precede shapes")

                    val shape = when (line[1]) {
                        "sphere" -> Sphere(getVec3(line, 2), getDouble(line, 5), material)
                        "emptySphere" -> EmptySphere(getVec3(line, 2), getDouble(line, 5), getDouble(line, 6), material)
                        else -> throw IllegalArgumentException(it)
                    }

                    scene.addObject(shape)
                }
                'M' -> {
                    val line = it.split(" ")

                    currentMaterial = when (line[1]) {
                        "lambertian" -> Lambertian(getVec3(line, 2))
                        "metal" -> Metal(getVec3(line, 2), getDouble(line, 2))
                        "dielectric" -> Dielectric(getDouble(line, 2))
                        else -> throw IllegalArgumentException(it)
                    }
                }
            }
        }

        return scene
    }

    private fun getVec3(line: List<String>, index: Int): Vec3 {
        val x = line[index].toDouble()
        val y = line[index + 1].toDouble()
        val z = line[index + 2].toDouble()
        return Vec3(x, y, z)
    }

    private fun getDouble(line: List<String>, index: Int): Double = line[index].toDouble()
}
