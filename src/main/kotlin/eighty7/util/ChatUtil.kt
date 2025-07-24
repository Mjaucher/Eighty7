package eighty7.util

import eighty7.Mod
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.text.TextColor

class ChatUtil {

    companion object {

        fun clientMessage(message: String, withPrefix: Boolean) {

            val result = Text.empty()

            if (withPrefix) {

                val prefix = Text.literal(
                    "[" + Mod.name + "-v" + Mod.version + "]"
                )

                result.append(prefix)
            }

            val message = Text.literal(message)

            val coloredStyle = Style.EMPTY.withColor(TextColor.fromRgb(Mod.color.rgb))

            result.style = coloredStyle
            result.append(message)

            Mod.minecraft.inGameHud.chatHud.addMessage(result)
        }

        fun serverMessage(message: String) =
            Mod.minecraft.player?.networkHandler?.sendChatMessage(message)

        fun lastMessage(): String =
            Mod.minecraft.inGameHud.chatHud.messageHistory.last()
    }
}