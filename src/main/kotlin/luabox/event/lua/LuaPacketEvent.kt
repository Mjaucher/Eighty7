package luabox.event.lua

import luabox.Mod
import luabox.Mod.logger
import luabox.Mod.minecraft
import luabox.Scripts
import luabox.event.ClientTickCallback
import luabox.event.PacketCallback
import net.minecraft.util.ActionResult
import org.luaj.vm2.LuaFunction
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.jse.CoerceJavaToLua

class LuaPacketEvent: LuaEvent("packet") {

    companion object {

        private var packet = LuaValue.NIL
    }

    override fun invoke(luaFunction: LuaFunction) {

        PacketCallback.event.register { packet, ci ->

            Companion.packet = CoerceJavaToLua.coerce(packet)

            return@register ActionResult.PASS
        }

        if (packet != LuaValue.NIL) {

            luaFunction.invoke(packet)
            packet = LuaValue.NIL
        }
    }
}