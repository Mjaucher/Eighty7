package luabox

import net.minecraft.network.packet.Packet
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.jse.CoerceJavaToLua

class Scripts: LuaBox(path = Mod.luaBoxPath) {

    init {

        addIndex("minecraft", Mod.minecraft)
        addIndex("is", fun (obj1: String, obj2: Packet<*>): Boolean {

            val objSimpleName = obj2::class.java.simpleName

            return objSimpleName == obj1
        })
        addIndex("test", fun (obj2: Packet<*>): String {

            return obj2::class.java.simpleName
        })
    }

    companion object {

        val mainScript = Scripts().newScript("main.lua")
    }
}