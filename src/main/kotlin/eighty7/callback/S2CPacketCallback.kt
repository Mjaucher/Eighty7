package eighty7.callback

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.network.packet.Packet
import net.minecraft.util.ActionResult
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

fun interface S2CPacketCallback {

    companion object {

        val event: Event<S2CPacketCallback> =

            EventFactory.createArrayBacked(S2CPacketCallback::class.java) { listeners ->

                S2CPacketCallback { packet, ci ->

                    for (listener in listeners) {
                        val result = listener.interact(packet, ci)
                        if (result != ActionResult.PASS) return@S2CPacketCallback result
                    }

                    return@S2CPacketCallback ActionResult.PASS
                }
            }
    }

    fun interact(packet: Packet<*>?, ci: CallbackInfo): ActionResult
}