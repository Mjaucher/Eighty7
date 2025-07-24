package eighty7.luabox

import eighty7.Mod

class Scripts: LuaBox(path = Mod.luaBoxPath) {

    init {

        addIndex("minecraft", Mod.minecraft)
    }
}