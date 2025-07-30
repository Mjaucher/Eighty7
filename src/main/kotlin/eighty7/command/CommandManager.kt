package eighty7.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.exceptions.CommandSyntaxException
import eighty7.Mod
import eighty7.command.commands.ModuleCommand
import eighty7.command.commands.TestCommand
import eighty7.util.ChatUtil
import net.minecraft.client.network.ClientCommandSource
import net.minecraft.command.CommandSource

object CommandManager {

    val dispatcher = CommandDispatcher<CommandSource>()

    init {

        TestCommand.register(dispatcher)
        ModuleCommand.register(dispatcher)
        BindCommand.register(dispatcher)
    }

    fun source(): ClientCommandSource? {

        val commandSource = Mod.minecraft.networkHandler?.commandSource

        return commandSource
    }

    fun execute(input: String) {

        try {

            dispatcher.execute(input, source())

        } catch (e: CommandSyntaxException) {

            ChatUtil.clientMessage("§cError in command: ${e.message}", true)

        } catch (e: Exception) {

            ChatUtil.clientMessage("§cUnknown error: ${e.message}", true)
        }
    }
}