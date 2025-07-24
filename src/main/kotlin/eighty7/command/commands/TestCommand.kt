package eighty7.command.commands

import eighty7.command.*

@Command.Info(
    name = "test",
    description = "",
    valuesCount = 4,
    status = CommandStatus.Numeric,
    available = ""
)

object TestCommand: Command() {

    override fun runNumericCommand(
        values: ArrayList<Double>
    ) {

    }

    override fun helpComments(): Array<String> =
        arrayOf("test is a test")
}