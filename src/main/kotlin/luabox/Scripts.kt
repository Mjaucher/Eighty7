package luabox

class Scripts: LuaBox(path = Mod.luaBoxPath) {

    init {

        addIndex("minecraft", Mod.minecraft)
    }

    companion object {

        val mainScript = Scripts().newScript("main.lua")
    }
}