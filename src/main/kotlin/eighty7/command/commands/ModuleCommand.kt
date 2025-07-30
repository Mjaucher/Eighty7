package eighty7.command.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import eighty7.module.ModuleManager
import eighty7.util.ChatUtil
import net.minecraft.command.CommandSource

object ModuleCommand {

    fun register(dispatcher: CommandDispatcher<CommandSource>) {

        dispatcher.register(module()

            .executes {
                context -> execute(context)
            })
    }

    fun module() =

        LiteralArgumentBuilder.literal<CommandSource>("module").then(
            RequiredArgumentBuilder.argument<CommandSource, String>(
                "name", StringArgumentType.greedyString()).suggests { _, builder ->

                for (module in ModuleManager.moduleArray) {

                    builder.suggest(module.name)
                }

                builder.buildFuture()

            })

    private fun execute(context: CommandContext<CommandSource>): Int {

        val input = StringArgumentType.getString(context, "name").lowercase()

        if (ModuleManager.matchModule(input) == null) {

            ChatUtil.clientMessage("§cModule \"$input\" not found.", true)

            return 0
        }

        ModuleManager.activateModule(

            ModuleManager.matchModule(input)!!
        )

        return 1
    }
}
