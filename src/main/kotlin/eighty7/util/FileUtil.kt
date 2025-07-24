package eighty7.util

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

class FileUtil {

    companion object {

        fun path(textPath: String): Path = Paths.get(textPath)

        fun fileExists(textPath: String): Boolean = try {

            File(textPath).exists()

        } catch (exception: Exception) {

            false
        }

        fun folderExists(path: Path): Boolean = path.toFile().exists()

        fun createFolder(path: Path) {

            if (!folderExists(path)) path.toFile().mkdirs()
        }

        fun createFile(path: Path) {

            try {

                if (path.toFile().createNewFile())

                    println("File has been created!")
                else
                    println("File cannot be created!")

            } catch (exception: Exception) {

                exception.printStackTrace()
            }
        }

        fun deleteFile(path: Path) {

            try {

                if (path.toFile().delete())

                    println("File has been deleted!")
                else
                    println("File not found!")

            } catch (exception: Exception) {

                exception.printStackTrace()
            }
        }
    }
}