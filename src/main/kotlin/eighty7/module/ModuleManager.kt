package eighty7.module

import eighty7.module.misc.InstantRespawn

class ModuleManager {

    companion object {

        val moduleArray: ArrayList<Module> = arrayListOf(

            InstantRespawn()
        )
    }
}