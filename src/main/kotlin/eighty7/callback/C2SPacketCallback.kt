package eighty7.callback

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.network.packet.Packet
import net.minecraft.util.ActionResult
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

fun interface C2SPacketCallback {

    companion object {

        val event: Event<C2SPacketCallback> =

            EventFactory.createArrayBacked(C2SPacketCallback::class.java) { listeners ->

                C2SPacketCallback { packet, ci ->

                    for (listener in listeners) {
                        val result = listener.interact(packet, ci)
                        if (result != ActionResult.PASS) return@C2SPacketCallback result
                    }

                    return@C2SPacketCallback ActionResult.PASS
                }
            }
    }

    fun interact(packet: Packet<*>?, ci: CallbackInfo): ActionResult
}