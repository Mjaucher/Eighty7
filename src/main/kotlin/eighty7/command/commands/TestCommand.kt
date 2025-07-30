package eighty7.command.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import eighty7.util.ChatUtil
import net.minecraft.command.CommandSource

object TestCommand {

    fun register(dispatcher: CommandDispatcher<CommandSource>) {

        dispatcher.register(

            LiteralArgumentBuilder.literal<CommandSource>("test").executes {

                ChatUtil.clientMessage("Test!", true)
                1
            }
        )
    }
}