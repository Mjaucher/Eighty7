package luabox.event

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.network.packet.Packet
import net.minecraft.util.ActionResult
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

fun interface PacketCallback {

    companion object {

        val event: Event<PacketCallback> =

            EventFactory.createArrayBacked(PacketCallback::class.java) { listeners ->

                PacketCallback { packet, ci ->

                    for (listener in listeners) {
                        val result = listener.interact(packet, ci)
                        if (result != ActionResult.PASS) return@PacketCallback result
                    }

                    return@PacketCallback ActionResult.PASS
                }
            }
    }

    fun interact(packet: Packet<*>?, ci: CallbackInfo): ActionResult
}