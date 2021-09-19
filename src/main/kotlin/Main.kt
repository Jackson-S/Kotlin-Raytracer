import output.FileIO
import output.PNGOutput
import output.PPMOutput

fun main() {
    val startTime = System.currentTimeMillis()
    val ratio = Book1FinalSceneMoving.camera().aspectRatio
    val width = 400
    val height = (width / ratio).toInt()
    val samplesPerPixel = 100
    val maximumDepth = 50

    val scene = Book1FinalSceneMoving.scene()
    val camera = Book1FinalSceneMoving.camera()

    val render = camera.render(
        scene = scene,
        imageWidth = width,
        pixelSamples = samplesPerPixel,
        maximumDepth = maximumDepth
    )

    PNGOutput(width, height).output(render).let {
        FileIO.write(it, "./out.png")
    }

    PPMOutput(width, height).output(render).let {
        FileIO.write(it, "./out.ppm")
    }

    print("Time taken ${(System.currentTimeMillis() - startTime).toDouble() / 1000.0} seconds")
}
