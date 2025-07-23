package luabox

import net.minecraft.client.MinecraftClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Paths

object Mod {

    val minecraft: MinecraftClient get() = MinecraftClient.getInstance()

    val logger: Logger get() = LoggerFactory.getLogger(name)

    val name: String get() = "LuaBox"
    val version: String get() = "25.2"

    val luaBoxPath = "${minecraft.runDirectory.path}/luabox/"

    fun createModFolder() {

        val folder = Paths.get(luaBoxPath).toFile()

        if (!folder.exists()) folder.mkdirs()
    }
}