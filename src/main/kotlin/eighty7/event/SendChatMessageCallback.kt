package eighty7.event

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.util.ActionResult
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

fun interface SendChatMessageCallback {

    companion object {

        val event: Event<SendChatMessageCallback> =

            EventFactory.createArrayBacked(SendChatMessageCallback::class.java) { listeners ->

                SendChatMessageCallback { message, ci ->

                    for (listener in listeners) {
                        val result = listener.interact(message,ci)
                        if (result != ActionResult.PASS) return@SendChatMessageCallback result
                    }

                    return@SendChatMessageCallback ActionResult.PASS
                }
            }
    }

    fun interact(message: String, ci: CallbackInfo): ActionResult
}