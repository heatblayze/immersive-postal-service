package com.heatblayze.immersivepost

import com.heatblayze.immersivepost.block.PostBlocks
import com.heatblayze.immersivepost.villager.PostVillagers
import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object ImmersivePostalService : ModInitializer {
	const val MOD_ID: String = "immersive-postal-service"
	val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		PostBlocks.registerModBlocks()
		PostVillagers.registerVillagers()
	}
}
