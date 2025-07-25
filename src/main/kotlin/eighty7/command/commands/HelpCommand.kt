package eighty7.command.commands

import eighty7.command.*

object HelpCommand: Command(

    name = "help",
    description = "This command gives information about the mod!",
    valuesCount = 1,
    status = CommandStatus.String,
    available = "list or 'command name'"
) {

    override fun runStringCommand(
        values: ArrayList<String>
    ) {
        var commandListString = "Command list:"

        CommandManager.commandArray.forEach {
            commandListString +=
                (if (it != CommandManager.commandArray[0]) ", " else " ") + it.name()
        }

        if (values[0] == "list") {

            success(false, commandListString)
        } else {

            var found = false

            CommandManager.commandArray.forEach {

                if (values[0] == it.name()) {

                    var helpComment = "\n"

                    it.helpComments().forEach { h ->

                        helpComment += h + if(h != it.helpComments().last()) "\n" else ""
                    }

                    success(
                        false,
                        "*------------------------------------------------*",

                        "Name: ${it.name()}.",
                        "Description: ${it.description()}",
                        "Max number of values: ${it.valuesCount()}.",
                        "Status: ${
                            if (it.status() == CommandStatus.String)
                                "String"
                            else if (it.status() == CommandStatus.Numeric)
                                "Numeric"
                            else
                                "Boolean"
                        }.",
                        "Available values: ${it.available()} ",
                        helpComment,
                        "*------------------------------------------------*"
                    )

                    found = true
                }
            }

            if (!found) error("Value \"${values[0]}\" does not exist!")
        }
    }

    override fun helpComments(): Array<String> =
        arrayOf(
            "Type \"list\" to get a list of commands",
            "If you enter the name of the commands, you can get information about it",
            "Command input example: <lua run, main.lua>"
        )
}