package com.heatblayze.immersivepost

import com.google.common.collect.ImmutableMap
import net.minecraft.entity.ai.brain.MemoryModuleType
import net.minecraft.entity.ai.brain.WalkTarget
import net.minecraft.entity.ai.brain.task.MultiTickTask
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Vec3d

// Some assumptions for how this will likely work:
// - Probably want the system split across a few different tasks, to do things like collecting mail from job site, calculating path points, etc.
// - Will want to use the Memory system to store the position of the next path point as the walk target
// - Presumably this will let the task be interrupted, so we will want the primary task to be able to take over again. Unsure if this is automatic.

class PostmanTask : MultiTickTask<VillagerEntity>(
    ImmutableMap.of()
) {
    override fun run(world: ServerWorld, entity: VillagerEntity, time: Long) {
        val target = Vec3d(0.0, entity.y,0.0)
        entity.brain.remember(MemoryModuleType.WALK_TARGET, WalkTarget(target, 1.0f, 0))
    }

    override fun isTimeLimitExceeded(time: Long): Boolean {
        return false
    }

    override fun shouldKeepRunning(world: ServerWorld, entity: VillagerEntity, time: Long): Boolean {
        val target = Vec3d(0.0, 80.0,0.0)
        return entity.squaredDistanceTo(target) > 5.0
    }

    override fun keepRunning(world: ServerWorld, entity: VillagerEntity, time: Long) {
        ImmersivePostalService.LOGGER.info("Engine is running!")
        val target = Vec3d(0.0, entity.y,0.0)
        entity.brain.remember(MemoryModuleType.WALK_TARGET, WalkTarget(target, 1.0f, 0))
    }
}
