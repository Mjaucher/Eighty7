package eighty7.command.commands

import eighty7.Mod
import eighty7.command.Command
import eighty7.command.CommandStatus
import eighty7.util.ArrayListUtil
import eighty7.util.ChatUtil
import eighty7.util.FileUtil
import java.nio.file.Files
import java.nio.file.Paths

object LuaCommand: Command(

    name = "lua",
    description = "Command for interacting with scripts.",
    valuesCount = 2,
    status = CommandStatus.String,
    available = "run, out, create, del -> 'script name'; get -> list"
) {

    var scriptsArrayList = ArrayList<String>()

    override fun runStringCommand(values: ArrayList<String>) {

        val scriptPathString =
            "${Mod.Companion.luaBoxPath}${values[1]}"

        when (values[0]) {

            "run", "out" -> {

                if (values[1].endsWith(".lua")) {

                    if (FileUtil.fileExists(scriptPathString)) {

                        try {
                            if (values[0] == "run") {

                                if (!ArrayListUtil.listElementExists(scriptsArrayList, values[1])) {

                                    scriptsArrayList.add(values[1])
                                    success(
                                        true,
                                        "${values[1]} was successfully run!"
                                    )
                                } else
                                    error("\"${values[1]}\" is already running!")
                            } else {

                                if (ArrayListUtil.listElementExists(scriptsArrayList, values[1])) {

                                    scriptsArrayList = ArrayListUtil.deleteListElement(scriptsArrayList, values[1])
                                    success(
                                        true,
                                        "${values[1]} disabled!"
                                    )
                                } else
                                    error("\"${values[1]}\" is not running!")
                            }
                        } catch (exception: Exception) {

                            ChatUtil.clientMessage(
                                "§c$exception",
                                true
                            )
                        }

                    } else
                        error("\"${values[1]}\" does not exist!")
                } else
                    error("This file extension is not available!")
            }

            "create" -> {

                if (!FileUtil.fileExists(scriptPathString)) {

                    FileUtil.createFile(FileUtil.path(scriptPathString))
                    success(
                        true,
                        "${values[1]} was created successfully!"
                    )
                } else
                    error("A file already exists!")
            }

            "del", "delete" -> {

                if (FileUtil.fileExists(scriptPathString)) {

                    FileUtil.deleteFile(FileUtil.path(scriptPathString))
                    success(
                        true,
                        "File ${values[1]} has been deleted!"
                    )
                } else
                    error("\"${values[1]}\" does not exist!")
            }

            "get" -> {

                if (values[1] == "files") {

                    var scriptsListString = "Files list:"

                    Files.walk(Paths.get(Mod.Companion.luaBoxPath)).forEach {

                        if (it.toFile().isFile && it.toFile().path.endsWith(".lua"))
                            scriptsListString += (if (scriptsListString != "Files list:") ", " else " ") + it.fileName
                    }

                    if (scriptsListString == "Files list:")

                        error("Scripts folder is empty!")
                    else
                        success(
                            true,
                            "$scriptsListString."
                        )

                } else if (values[1] == "list") {

                    var runningScriptsString = "Running scripts:"

                    scriptsArrayList.forEach {

                        runningScriptsString += (if (runningScriptsString != "Running scripts:") ", " else " ") + it
                    }

                    if (runningScriptsString == "Running scripts:")

                        error("No scripts were run!")
                    else
                        success(
                            true,
                            "$runningScriptsString."
                        )

                } else
                    error("Value ${values[1]} is not in the list of values!")
            }

            else ->
                error("Command was written incorrectly, example: <${name()} run, script.lua>")
        }
    }

    override fun helpComments(): Array<String> =
        arrayOf("test is a test")
}
