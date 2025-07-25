package eighty7.command

import eighty7.util.ChatUtil

abstract class Command(

    val name: String,
    val description: String,
    val valuesCount: Int,
    val status: CommandStatus,
    val available: String
) {

    open fun runStringCommand(
        values: ArrayList<String>
    ) {}

    open fun runNumericCommand(
        values : ArrayList<Double>
    ) {}

    open fun runBooleanCommand(
        values : Boolean
    ) {}

    abstract fun helpComments(): Array<String>

    fun name() = name
    fun description() = description
    fun valuesCount() = valuesCount
    fun status() = status
    fun available() = available

    open fun error(
        reason: String
    ) = ChatUtil.clientMessage("§c$reason", true)

    open fun success(
        prefix: Boolean,
        vararg text : String
    ) {
        var count = 1

        text.forEach {
            ChatUtil.clientMessage("§${if (count % 2 == 0) "a" else "2"}$it", prefix)
            count++
        }
    }
}
