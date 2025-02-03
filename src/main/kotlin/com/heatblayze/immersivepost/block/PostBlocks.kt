package com.heatblayze.immersivepost.block

import com.heatblayze.immersivepost.ImmersivePostalService
import com.heatblayze.immersivepost.block.custom.PostmanProfessionBlock
import jdk.internal.org.jline.keymap.KeyMap.key
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier


object PostBlocks {
    val POST_STATION_BLOCK: Block = registerBlock(
        "post_station_block",
        AbstractBlock.Settings.copy(Blocks.IRON_BLOCK),
        ::PostmanProfessionBlock
    )

    private fun registerBlock(
        name: String,
        baseSettings: AbstractBlock.Settings,
        blockFunc: (settings: AbstractBlock.Settings) -> Block
    ): Block {
        val identifier = Identifier.of(ImmersivePostalService.MOD_ID, name)
        val key = RegistryKey.of(RegistryKeys.BLOCK, identifier)
        val settings = baseSettings.registryKey(key)
        val block = blockFunc(settings)

        registerBlockItem(name, block)

        return Registry.register(
            Registries.BLOCK,
            RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(ImmersivePostalService.MOD_ID, name)),
            block
        )
    }

    private fun registerBlockItem(name: String, block: Block): Item {
        val identifier = Identifier.of(ImmersivePostalService.MOD_ID, name)
        val key = RegistryKey.of(RegistryKeys.ITEM, identifier)
        val settings = Item.Settings().registryKey(key)
        return Registry.register(
            Registries.ITEM,
            key,
            BlockItem(block, settings)
        )
    }

    fun registerModBlocks() {
        ImmersivePostalService.LOGGER.info("Registering ModBlocks for " + ImmersivePostalService.MOD_ID)
    }
}
