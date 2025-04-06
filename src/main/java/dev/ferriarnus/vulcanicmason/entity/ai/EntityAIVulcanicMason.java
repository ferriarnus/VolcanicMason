package dev.ferriarnus.vulcanicmason.entity.ai;

import com.minecolonies.api.configuration.ServerConfiguration;
import com.minecolonies.api.entity.ai.statemachine.AITarget;
import com.minecolonies.api.entity.ai.statemachine.states.AIWorkerState;
import com.minecolonies.api.entity.ai.statemachine.states.IAIState;
import com.minecolonies.api.research.util.ResearchConstants;
import com.minecolonies.api.util.InventoryUtils;
import com.minecolonies.core.MineColonies;
import com.minecolonies.core.entity.ai.workers.AbstractEntityAIInteract;
import dev.ferriarnus.vulcanicmason.buildings.BuildingVulcanicMason;
import dev.ferriarnus.vulcanicmason.jobs.JobVulcanicMason;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import static com.minecolonies.api.entity.ai.statemachine.states.AIWorkerState.*;
import static com.minecolonies.api.util.constant.Constants.TICKS_SECOND;
import static com.minecolonies.api.util.constant.Constants.UPDATE_FLAG;
import static dev.ferriarnus.vulcanicmason.entity.ai.VulcanicMasonStates.*;

public class EntityAIVulcanicMason extends AbstractEntityAIInteract<JobVulcanicMason, BuildingVulcanicMason> {

    public EntityAIVulcanicMason(@NotNull JobVulcanicMason job) {
        super(job);
        super.registerTargets(
                new AITarget<IAIState>(AIWorkerState.IDLE, () -> AIWorkerState.START_WORKING, 1),
                new AITarget<IAIState>(AIWorkerState.START_WORKING, this::decide, 5),
                new AITarget<IAIState>(VULCANIC_MASON_PLACE_FLUID, this::placeFluid, TICKS_SECOND),
                new AITarget<IAIState>(VULCANIC_MASON_HARVESTING, this::harvest, TICKS_SECOND)
        );
        this.worker.setCanPickUpLoot(true);
    }

    private IAIState decide() {
        if (building.getBlockToMine() != null) {
            if (building.getFirstModuleOccurance(BuildingVulcanicMason.LimitedMiningModule.class).canMine()) {
                return VULCANIC_MASON_HARVESTING;
            }
        }

        if (job.getActionsDone() > 0) {
            // Wait to dump before continuing.
            return getState();
        }

        return START_WORKING;
    }

    private IAIState placeFluid() {
        final BlockPos posToPlace = building.getBlockToPlace();
        if (posToPlace == null) {
            return START_WORKING;
        }

        final int slot = getSlotFluid();
        if (slot == -1) {
//            if (InventoryUtils.getCountFromBuilding(building, CONCRETE) > 0)
//            {
//                needsCurrently = new Tuple<>(CONCRETE, STACKSIZE);
//                return GATHERING_REQUIRED_MATERIALS;
//            }

            return START_WORKING;
        }

        if (!walkToWorkPos(posToPlace)) {
            return getState();
        }

        final ItemStack stack = worker.getInventoryCitizen().getStackInSlot(slot);
        final Block block = ((BlockItem) stack.getItem()).getBlock();
        if (InventoryUtils.attemptReduceStackInItemHandler(worker.getInventoryCitizen(), stack, 1)) {
            world.setBlock(posToPlace, block.defaultBlockState().updateShape(Direction.DOWN, block.defaultBlockState(), world, posToPlace, posToPlace), UPDATE_FLAG);
        }

        return getState();
    }

    private int getSlotFluid() {
        return 0;
    }

    private IAIState harvest() {
        final BlockPos posToMine = building.getBlockToMine();
        if (posToMine == null) {
            this.resetActionsDone();
            return START_WORKING;
        }

        if (!walkToWorkPos(posToMine)) {
            return getState();
        }

        if (mineBlock(posToMine)) {
            building.getFirstModuleOccurance(BuildingVulcanicMason.LimitedMiningModule.class).mine();
            incrementActionsDoneAndDecSaturation();
            worker.getCitizenExperienceHandler().addExperience(1.0 / 2.0);
        }

        return getState();
    }

    @Override
    public boolean shouldSilkTouchBlock(BlockState curBlockState) {
        return true;
    }

    @Override
    public int getBlockMiningTime(@NotNull BlockState state, @NotNull BlockPos pos) {
        if (this.worker.getMainHandItem() == null) {
            return (int) state.getDestroySpeed(this.world, pos);
        } else {
            return MineColonies.getConfig().getServer().pvp_mode.get() ? 250 : this.calculateWorkerMiningDelay(state, pos);
        }
    }

    //TODO increase speed with level
    private int calculateWorkerMiningDelay(@NotNull BlockState state, @NotNull BlockPos pos) {
        double reduction = 1.0 - this.worker.getCitizenColonyHandler().getColonyOrRegister().getResearchManager().getResearchEffects().getEffectStrength(ResearchConstants.BLOCK_BREAK_SPEED);
        return (int)(500.0 * Math.pow(0.85, (double)this.getBreakSpeedLevel() / 2.0) * (double)this.world.getBlockState(pos).getDestroySpeed(this.world, pos) / (double)this.worker.getMainHandItem().getItem().getDestroySpeed(this.worker.getMainHandItem(), state) * reduction);
    }

    @Override
    public Class<BuildingVulcanicMason> getExpectedBuildingClass() {
        return BuildingVulcanicMason.class;
    }
}
