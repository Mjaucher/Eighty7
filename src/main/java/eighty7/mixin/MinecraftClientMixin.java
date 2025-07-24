package eighty7.mixin;

import eighty7.Mod;
import eighty7.event.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({MinecraftClient.class})
public class MinecraftClientMixin {

    @ModifyArg(
            method = "updateWindowTitle",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/util/Window;setTitle(Ljava/lang/String;)V")
    )
    private String updateWindowTitle(String original) {

        return Mod.Companion.getName() + "-v" + Mod.Companion.getVersion();
    }

    @Inject(
            method = {"tick()V"},
            at = @At(
                    value = "TAIL"
            )
    )

    public void tick(CallbackInfo ci) {

        ClientTickCallback.Companion.getEvent().invoker().interact(ci);
    }
}