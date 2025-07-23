package luabox

import luabox.event.ClientTickCallback
import luabox.event.PacketCallback
import luabox.event.lua.LuaPacketEvent
import net.minecraft.client.MinecraftClient
import net.minecraft.util.ActionResult
import org.luaj.vm2.lib.jse.CoerceJavaToLua
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

    fun runningMainScript() = ClientTickCallback.event.register { ci ->

        try {

            if (minecraft.world != null)
                Scripts.mainScript.call()

        } catch (exception: Exception) {

            logger.error("${name}: $exception")
        }

        return@register ActionResult.PASS
    }
}