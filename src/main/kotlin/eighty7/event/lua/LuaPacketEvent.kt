package eighty7.event.lua

import eighty7.event.PacketCallback
import net.minecraft.util.ActionResult
import org.luaj.vm2.LuaFunction
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.jse.CoerceJavaToLua

class LuaPacketEvent: LuaEvent("onPacket") {

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