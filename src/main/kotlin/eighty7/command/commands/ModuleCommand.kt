package eighty7.command.commands

import eighty7.command.*
import eighty7.module.ModuleManager
import eighty7.util.ChatUtil

object ModuleCommand: Command(

    name = "module",
    description = "Command to enable and disable modules.",
    valuesCount = 1,
    status = CommandStatus.String,
    available = "'module name'"
) {

    override fun runStringCommand(
        values: ArrayList<String>
    ) {

        if (values[0] == "list") {

            ModuleManager.moduleArray.forEach {

                ChatUtil.clientMessage(
                    "${it.name}: ${if (it.activated) "§aActivated" else "§cDisabled"}",
                    false
                )
            }

            return
        }

        ModuleManager.moduleArray.forEach {

            if (values[0] == it.name) {

                it.activated = !it.activated

            } else {

                error("Module \"${values[0]}\" does not exist!")
            }
        }
    }

    override fun helpComments(): Array<String> =
        arrayOf(
            "Write the name of the module to enable and disable it.",
            "Or \"list\" to get a list of modules."
        )
}