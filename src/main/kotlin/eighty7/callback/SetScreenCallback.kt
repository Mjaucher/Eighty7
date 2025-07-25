package eighty7.callback

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.client.gui.screen.Screen
import net.minecraft.util.ActionResult
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

fun interface SetScreenCallback {

    companion object {

        val event: Event<SetScreenCallback> =

            EventFactory.createArrayBacked(SetScreenCallback::class.java) { listeners ->

                SetScreenCallback { screen, ci ->

                    for (listener in listeners) {
                        val result = listener.interact(screen, ci)
                        if (result != ActionResult.PASS) return@SetScreenCallback result
                    }

                    return@SetScreenCallback ActionResult.PASS
                }
            }
    }

    fun interact(screen: Screen, ci: CallbackInfo): ActionResult
}