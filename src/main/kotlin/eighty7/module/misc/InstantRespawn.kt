package eighty7.module.misc

import eighty7.Mod
import eighty7.callback.SetScreenCallback
import eighty7.module.Module
import eighty7.module.ModuleCategory
import net.minecraft.client.gui.screen.DeathScreen
import net.minecraft.util.ActionResult

object InstantRespawn: Module(

    name = "instant.respawn",
    description = "Instantly respawns ignoring the death screen.",
    category = ModuleCategory.Misc
) {

    override fun invoke() = SetScreenCallback.Companion.event.register { screen, ci ->

        if (screen is DeathScreen) {

            Mod.Companion.minecraft.player?.requestRespawn()

            ci.cancel()
        }

        return@register ActionResult.PASS
    }
}