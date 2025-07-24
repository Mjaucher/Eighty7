package eighty7

import net.fabricmc.api.ModInitializer

class Initializer: ModInitializer {

    override fun onInitialize() {

        Mod.createScriptsFolder()

        Mod.logger.info("${Mod.name} started!")
    }
}
