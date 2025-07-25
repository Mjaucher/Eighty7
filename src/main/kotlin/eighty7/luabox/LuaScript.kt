package eighty7.luabox

import org.luaj.vm2.Globals
import org.luaj.vm2.LoadState
import org.luaj.vm2.LuaValue
import org.luaj.vm2.compiler.LuaC
import org.luaj.vm2.lib.Bit32Lib
import org.luaj.vm2.lib.CoroutineLib
import org.luaj.vm2.lib.PackageLib
import org.luaj.vm2.lib.StringLib
import org.luaj.vm2.lib.TableLib
import org.luaj.vm2.lib.jse.CoerceJavaToLua
import org.luaj.vm2.lib.jse.JseBaseLib
import org.luaj.vm2.lib.jse.JseIoLib
import org.luaj.vm2.lib.jse.JseMathLib
import org.luaj.vm2.lib.jse.JseOsLib
import org.luaj.vm2.lib.jse.LuajavaLib
import java.io.IOException

class LuaScript(

    private val fileName: String,
    private val luaBox: LuaBox
    ) {

    private val globals = Globals()

    init {

        globals.load(JseBaseLib())
        globals.load(PackageLib())
        globals.load(Bit32Lib())
        globals.load(TableLib())
        globals.load(StringLib())
        globals.load(CoroutineLib())
        globals.load(JseMathLib())
        globals.load(JseIoLib())
        globals.load(JseOsLib())
        globals.load(LuajavaLib())

        globals.set("luabox", CoerceJavaToLua.coerce(luaBox))
        globals.set("script", CoerceJavaToLua.coerce(this))

        luaBox.indexes.forEach {
            globals.set(it.key, it.value)
        }

        LoadState.install(globals)
        LuaC.install(globals)
    }

    fun path(): String = luaBox.path

    fun call() {

        try {

            val chunk = globals.loadfile(path() + fileName)

            setIndexes()
            chunk.call()

        } catch (exception: IOException) {

            exception.printStackTrace()
        }
    }

    fun callFunction(functionName: String, vararg luaValues: LuaValue) {

        try {

            val chunk = globals.loadfile(path() + fileName)

            setIndexes()
            chunk.call()

            globals[functionName].invoke(luaValues)

        } catch (exception: IOException) {

            exception.printStackTrace()
        }
    }

    private fun setIndexes() =

        luaBox.indexes.forEach {
            globals.set(it.key, it.value)
        }
}