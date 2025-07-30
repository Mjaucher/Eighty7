package eighty7.module

import eighty7.module.misc.InstantRespawn
import eighty7.util.ChatUtil

object ModuleManager {

    val moduleArray = arrayListOf<Module>(

        InstantRespawn
    )

    fun activateModule(module: Module) {

        module.activated = !module.activated

        ChatUtil.clientMessage(
            "${module.name}: ${if (module.activated) "§aActivated" else "§cDisabled"}",
            false
        )
    }

    fun matchModule(input: String) = moduleArray.find {

        return moduleArray.find {

            it.name == input
        }
    }
}