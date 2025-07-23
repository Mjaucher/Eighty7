package luabox.mixin;

import luabox.Mod;
import luabox.event.ClientTickCallback;
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
    private String setTitle(String original) {

        return Mod.INSTANCE.getName();
    }

    @Inject(
            method = {"tick()V"},
            at = @At(
                    value = "TAIL"
            )
    )

    public void tick(CallbackInfo ci) {

        ClientTickCallback.Companion.getEVENT().invoker().interact(ci);
    }
}