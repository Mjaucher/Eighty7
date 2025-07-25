package eighty7.command.commands

import eighty7.command.*

object TestCommand: Command(

    name = "test",
    description = "",
    valuesCount = 4,
    status = CommandStatus.Numeric,
    available = ""
) {

    override fun runNumericCommand(
        values: ArrayList<Double>
    ) {

    }

    override fun helpComments(): Array<String> =
        arrayOf("test is a test")
}