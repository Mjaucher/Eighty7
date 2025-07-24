package eighty7

import eighty7.util.FileUtil
import net.minecraft.client.MinecraftClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color
import java.nio.file.Paths

class Mod {

    companion object {

        val minecraft: MinecraftClient get() = MinecraftClient.getInstance()

        val logger: Logger get() = LoggerFactory.getLogger(name)

        val name = "Eighty7"
        val version = "25.3"

        val color = Color(0xff9bff)

        val luaBoxPath = "${minecraft.runDirectory.path}/luabox/"

        fun createScriptsFolder() = FileUtil.createFolder(Paths.get(luaBoxPath))
    }
}