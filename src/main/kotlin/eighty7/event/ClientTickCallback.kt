package eighty7.event

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.util.ActionResult
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

fun interface ClientTickCallback {

    companion object {

        val event: Event<ClientTickCallback> =

            EventFactory.createArrayBacked(ClientTickCallback::class.java) { listeners ->

                ClientTickCallback { ci ->

                    for (listener in listeners) {
                        val result = listener.interact(ci)
                        if (result != ActionResult.PASS) return@ClientTickCallback result
                    }

                    return@ClientTickCallback ActionResult.PASS
                }
            }
    }

    fun interact(ci: CallbackInfo): ActionResult
}