package eighty7.mixin;

import eighty7.command.CommandManager;
import net.minecraft.client.gui.screen.ChatScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {

    @Inject(
            method = "sendMessage",
            at = @At("HEAD"),
            cancellable = true
    )
    private void sendMessage(String chatText, boolean addToHistory, CallbackInfo ci) {

        if (chatText.startsWith("$")) {

            String command = chatText.substring(1);
            CommandManager.INSTANCE.execute(command);
            ci.cancel();
        }
    }
}
