package eighty7.command

import eighty7.command.commands.*

class CommandManager {

    companion object {

        val commandArray: ArrayList<Command> =
            arrayListOf(HelpCommand, TestCommand, LuaCommand)
    }
}