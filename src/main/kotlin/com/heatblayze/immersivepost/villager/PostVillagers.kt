package com.heatblayze.immersivepost.villager

import com.google.common.collect.ImmutableSet
import com.heatblayze.immersivepost.ImmersivePostalService
import com.heatblayze.immersivepost.block.PostBlocks
import net.fabricmc.fabric.api.`object`.builder.v1.world.poi.PointOfInterestHelper
import net.minecraft.block.*;
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Identifier
import net.minecraft.village.VillagerProfession
import net.minecraft.world.poi.PointOfInterestType

object PostVillagers {
    val POI_STRING = "postmanpoi"

    val POSTMAN_POI_KEY: RegistryKey<PointOfInterestType> = RegistryKey.of(
        RegistryKeys.POINT_OF_INTEREST_TYPE,
        Identifier.of(ImmersivePostalService.MOD_ID, POI_STRING)
    )
    val POSTMAN_POI: PointOfInterestType = PointOfInterestHelper.register(
        Identifier.of(ImmersivePostalService.MOD_ID, POI_STRING),
        1,
        1,
        PostBlocks.POST_STATION_BLOCK
    )
    val SOUND_MASTER: VillagerProfession = Registry.register(
        Registries.VILLAGER_PROFESSION,
        RegistryKey.of(RegistryKeys.VILLAGER_PROFESSION, Identifier.of(ImmersivePostalService.MOD_ID, "postman")),
        VillagerProfession(
            "postman",
            { entry: RegistryEntry<PointOfInterestType> -> entry.matchesKey(POSTMAN_POI_KEY) },
            { entry: RegistryEntry<PointOfInterestType> -> entry.matchesKey(POSTMAN_POI_KEY) },
            ImmutableSet.of(),
            ImmutableSet.of(),
            SoundEvents.ENTITY_VILLAGER_WORK_SHEPHERD
        )
    )

    fun registerVillagers() {
        ImmersivePostalService.LOGGER.info("Registering Villagers for " + ImmersivePostalService.MOD_ID)
    }
}
