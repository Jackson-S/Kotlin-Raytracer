import datatypes.Camera
import datatypes.Point3
import input.FileInput
import output.PPMOutput
import output.SceneOutput
import java.nio.file.Path

fun main() {
    val startTime = System.currentTimeMillis()
    val ratio = 16.0 / 9.0
    val width = 1200
    val height = (width / ratio).toInt()
    val samplesPerPixel = 500
    val maximumDepth = 50

    val scene = FileInput(Path.of("./Scenes", "basic scene dielectric.txt")).load()

    // val origin = Point3(13.0, 2.0, 3.0)
    // val target = Point3()
    // val focalDistance = 10.0

    val origin = Point3(.0, .0, 1.0)
    val target = Point3()
    val fieldOfView = 90.0
    val focalDistance = 1.5

    val camera = Camera(
        aspectRatio = ratio,
        verticalFieldOfView = fieldOfView,
        origin = origin,
        target = target,
        aperture = 0.1,
        focalDistance = focalDistance
    )

    SceneOutput(scene).write(Path.of("./", "scene.txt"))

    val render = camera.render(
        scene = scene,
        imageWidth = width,
        pixelSamples = samplesPerPixel,
        maximumDepth = maximumDepth
    )

    PPMOutput(width, height, Path.of("./", "out.ppm")).write(render)

    print("Time taken ${(System.currentTimeMillis() - startTime).toDouble() / 1000.0} seconds")
}
