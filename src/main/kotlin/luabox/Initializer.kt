package luabox

import net.fabricmc.api.ModInitializer

class Initializer: ModInitializer {

    override fun onInitialize() {

        Mod.createModFolder()
        Mod.runningMainScript()

        Mod.logger.info("${Mod.name} started!")
    }
}
