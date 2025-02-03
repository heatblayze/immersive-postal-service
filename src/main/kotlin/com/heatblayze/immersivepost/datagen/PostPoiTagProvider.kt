package com.heatblayze.immersivepost.datagen

import com.heatblayze.immersivepost.ImmersivePostalService
import com.heatblayze.immersivepost.villager.PostVillagers
import net.minecraft.data.DataOutput
import net.minecraft.data.tag.TagProvider
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.PointOfInterestTypeTags
import net.minecraft.util.Identifier
import net.minecraft.world.poi.PointOfInterestType
import java.util.concurrent.CompletableFuture

class PostPoiTagProvider(
    output: DataOutput,
    registryLookupFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
) : TagProvider<PointOfInterestType>(output, RegistryKeys.POINT_OF_INTEREST_TYPE, registryLookupFuture) {
    override fun configure(registries: RegistryWrapper.WrapperLookup?) {
        getOrCreateTagBuilder(PointOfInterestTypeTags.ACQUIRABLE_JOB_SITE)
            .addOptional(Identifier.of(ImmersivePostalService.MOD_ID, PostVillagers.POI_STRING))
    }
}
