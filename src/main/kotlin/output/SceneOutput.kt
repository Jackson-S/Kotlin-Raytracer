package output

import datatypes.Scene
import java.nio.file.Path
import kotlin.io.path.writer

class SceneOutput(
    private val scene: Scene
) {
    fun write(to: Path) {
        val writer = to.writer(Charsets.UTF_8)

        writer.write(scene.output())

        writer.close()
    }
}
