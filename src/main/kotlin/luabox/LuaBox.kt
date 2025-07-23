package luabox

import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.jse.CoerceJavaToLua

open class LuaBox(val path: String) {

    val indexes = hashMapOf<String, LuaValue>()

    fun newScript(fileName: String) = LuaScript(fileName, this)

    fun addIndex(key: String, value: Any) {
        indexes[key] = CoerceJavaToLua.coerce(value)
    }
}