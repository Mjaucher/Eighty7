package eighty7.event.lua

import eighty7.event.SendChatMessageCallback
import net.minecraft.util.ActionResult
import org.luaj.vm2.LuaFunction
import org.luaj.vm2.lib.jse.CoerceJavaToLua

class LuaSendChatMessageEvent: LuaEvent("onSendChatMessage") {

    override fun invoke(luaFunction: LuaFunction) =

        SendChatMessageCallback.event.register { message, ci ->

            val message = CoerceJavaToLua.coerce(message)

            luaFunction.invoke(message)

            return@register ActionResult.PASS
        }
}