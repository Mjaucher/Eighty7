package eighty7.module

abstract class Module(

    val name: String,
    val description: String,
    val category: ModuleCategory
) {

    var activated = false
    // https://github.com/LWJGL/lwjgl/blob/master/src/java/org/lwjgl/input/Keyboard.java
    var key = 0

    open fun invoke() {}
}