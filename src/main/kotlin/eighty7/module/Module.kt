package eighty7.module

abstract class Module(

    val name: String,
    val description: String,
    val category: ModuleCategory
) {

    var activated = false

    open fun invoke() {}
}