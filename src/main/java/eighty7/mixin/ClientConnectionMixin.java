package eighty7.mixin;

import eighty7.command.Command;
import eighty7.command.CommandManager;
import eighty7.util.ChatUtil;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import eighty7.event.PacketCallback;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {

    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V",
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/ClientConnection;handlePacket(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/listener/PacketListener;)V"
            )},
            cancellable = true
    )
    private void onChannelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {

        PacketCallback.Companion.getEvent().invoker().interact(packet, ci);
    }

    @Inject(
            method = {"send(Lnet/minecraft/network/packet/Packet;Lio/netty/channel/ChannelFutureListener;)V"},
            at = @At(value = "HEAD"),
            cancellable = true
    )
    public void send(Packet<?> packet, ChannelFutureListener channelFutureListener, CallbackInfo ci) {

        if (packet instanceof ChatMessageC2SPacket pt) {

            String message = pt.chatMessage();

            if (message.startsWith("<") && message.endsWith(">")) {

                boolean found = false;

                for (Command command: CommandManager.Companion.getCommandArray()) {

                    String head = "<" + command.name() + " ";

                    if (message.startsWith(head)) {

                        found = true;

                        String valueText = message.substring(head.length(), message.length() - 1);

                        switch (command.status()) {

                            case String -> {

                                String[] valueArray = valueText.split(", ");

                                ArrayList<String> stringValues =
                                        new ArrayList<>(Arrays.asList(valueArray));

                                if (command.valuesCount() <= stringValues.size())
                                    command.runStringCommand(stringValues);
                                else command.error(
                                        "Command must have " + command.valuesCount() + " or less values!"
                                );

                                stringValues.clear();
                            }

                            case Numeric -> {

                                try {

                                    ArrayList<Double> numericValues = new ArrayList<>();

                                    String[] valueArray = valueText.split(" ");

                                    for (String value: valueArray)
                                        numericValues.add(Double.parseDouble(value));

                                    if (command.valuesCount() <= numericValues.size())
                                        command.runNumericCommand(numericValues);
                                    else command.error("Command must have " + command.valuesCount() + "or less values!");

                                    numericValues.clear();

                                } catch (NumberFormatException e) {
                                    String error =
                                            "Command was written incorrectly, example: \"<" + command.name();

                                    for (int ind = 1; ind <= command.valuesCount(); ind++) {
                                        if (ind <= 3)
                                            error += " Value" + ind;

                                        else if (ind == command.valuesCount())
                                            error += " ... Value" + ind;
                                    }

                                    command.error(error + ">\"!");
                                }
                            }

                            default -> {

                                if (valueText.equals("true")) {
                                    command.runBooleanCommand(true);

                                } else if (valueText.equals("false")) {
                                    command.runBooleanCommand(false);

                                } else command.error("Value can only be false or true!");
                            }
                        }
                    }
                }

                if (!found) {

                    ChatUtil.Companion.clientMessage("§cCommand not found!", true);
                }

                ci.cancel();
            }
        }
    }
}
