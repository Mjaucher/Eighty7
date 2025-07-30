package eighty7.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import eighty7.command.CommandManager;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.CompletableFuture;

@Mixin(ChatInputSuggestor.class)
public abstract class ChatInputSuggestorMixin {

    @Final @Shadow TextFieldWidget textField;

    @Shadow private ParseResults<CommandSource> parse;
    @Shadow boolean completingSuggestions;
    @Shadow private CompletableFuture<Suggestions> pendingSuggestions;
    @Shadow private ChatInputSuggestor.SuggestionWindow window;

    @Shadow
    protected abstract void showCommandSuggestions();

    @Inject(
            method = "refresh",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/brigadier/StringReader;canRead()Z",
                    remap = false
            ),
            cancellable = true
    )
    public void refresh(CallbackInfo ci, @Local StringReader stringReader) {

        if (stringReader.canRead() && stringReader.peek() == '$') {

            CommandDispatcher<CommandSource> dispatcher = CommandManager.INSTANCE.getDispatcher();

            stringReader.setCursor(stringReader.getCursor() + 1);

            if (this.parse == null) {
                this.parse = dispatcher.parse(stringReader, CommandManager.INSTANCE.source());
            }

            int i = this.textField.getCursor();

            if (i >= 1 && (this.window == null || !this.completingSuggestions)) {
                this.pendingSuggestions = dispatcher.getCompletionSuggestions(this.parse, i);
                this.pendingSuggestions.thenRun(() -> {
                    if (this.pendingSuggestions.isDone()) {
                        this.showCommandSuggestions();
                    }
                });
            }

            ci.cancel();
        }
    }
}
