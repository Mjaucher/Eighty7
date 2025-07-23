package luabox

import luabox.event.ClientTickCallback
import net.fabricmc.api.ModInitializer
import net.minecraft.util.ActionResult

class Initializer: ModInitializer {

    override fun onInitialize() {

        Mod.createModFolder()

        Mod.logger.info("${Mod.name} started!")

        ClientTickCallback.EVENT.register { ci ->

            try {

                if (Mod.minecraft.world != null)
                    Scripts.mainScript.call()

            } catch (exception: Exception) {

                Mod.logger.error("${Mod.name}: $exception")
            }

            return@register ActionResult.PASS
        }
    }
}
