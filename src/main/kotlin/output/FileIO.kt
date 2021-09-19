package output

import kotlin.io.path.Path
import kotlin.io.path.readBytes
import kotlin.io.path.writeBytes

object FileIO {
    fun write(data: ByteArray, to: String) {
        Path(to).writeBytes(data)
    }

    fun read(from: String): ByteArray {
        return Path(from).readBytes()
    }
}