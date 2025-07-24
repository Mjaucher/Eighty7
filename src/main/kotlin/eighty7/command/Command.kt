package eighty7.command

import eighty7.util.ChatUtil

abstract class Command {

    @Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Info(
        val name: String,
        val description: String,
        val valuesCount: Int,
        val status: CommandStatus,
        val available: String
    )

    private val annotation =
        javaClass.getAnnotation(Info::class.java)

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

    fun name() = annotation.name
    fun description() = annotation.description
    fun valuesCount() = annotation.valuesCount
    fun status() = annotation.status
    fun available() = annotation.available

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
