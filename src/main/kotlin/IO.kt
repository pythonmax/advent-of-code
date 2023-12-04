import java.io.File

fun resourceAsFile(path: String): File = File(ClassLoader.getSystemResource(path).file)
