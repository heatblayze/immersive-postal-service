package com.heatblayze.immersivepost.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.heatblayze.immersivepost.PostmanTask;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.VillagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public class PostmanVillagerMixin {
	@Inject(at = @At("HEAD"), method = "initBrain", cancellable = true)
	private void initBrain(Brain<VillagerEntity> brain, CallbackInfo ci) {
		// Testing overriding default villager behaviour. It works!
		VillagerEntity thisObject = (VillagerEntity)(Object)this;
		if (!thisObject.getVillagerData().getProfession().id().equals("postman")) {
			return;
		}

		var profession = thisObject.getVillagerData().getProfession();
		brain.setTaskList(Activity.CORE, ImmutableList.of(
				Pair.of(0, new StayAboveWaterTask<>(0.8F)),
				Pair.of(0, OpenDoorsTask.create()),
				Pair.of(0, new UpdateLookControlTask(45, 90)),
				Pair.of(0, new PanicTask()),
				Pair.of(0, WakeUpTask.create()),
				Pair.of(0, ForgetCompletedPointOfInterestTask.create(profession.heldWorkstation(), MemoryModuleType.JOB_SITE)),
				Pair.of(0, ForgetCompletedPointOfInterestTask.create(profession.acquirableWorkstation(), MemoryModuleType.POTENTIAL_JOB_SITE)),
				Pair.of(1, new MoveToTargetTask()),
				Pair.of(2, WorkStationCompetitionTask.create()),
				Pair.of(10, UpdateJobSiteTask.create()),
				Pair.of(10, LoseJobOnSiteLossTask.create())
		));

		brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(1, new PostmanTask())));
		brain.setTaskList(Activity.PANIC, VillagerTaskListProvider.createPanicTasks(profession, 0.5F));

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.CORE);
		brain.doExclusively(Activity.CORE);
		brain.refreshActivities(thisObject.getWorld().getTimeOfDay(), thisObject.getWorld().getTime());

		ci.cancel();
	}
}
