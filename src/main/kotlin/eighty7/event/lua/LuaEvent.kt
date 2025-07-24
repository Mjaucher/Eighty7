package eighty7.event.lua

import org.luaj.vm2.LuaFunction

abstract class LuaEvent(val type: String){

    abstract fun invoke(luaFunction: LuaFunction)
}