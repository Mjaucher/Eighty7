package eighty7.mixin;

import eighty7.callback.C2SPacketCallback;
import eighty7.callback.S2CPacketCallback;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

    @Inject(
            method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void onChannelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {

        S2CPacketCallback.Companion.getEvent().invoker().interact(packet, ci);
    }

    @Inject(
            method = "send(Lnet/minecraft/network/packet/Packet;Lio/netty/channel/ChannelFutureListener;)V",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    public void send(Packet<?> packet, ChannelFutureListener channelFutureListener, CallbackInfo ci) {

        C2SPacketCallback.Companion.getEvent().invoker().interact(packet, ci);
    }
}
