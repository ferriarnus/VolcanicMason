package dev.ferriarnus.vulcanicmason.entity.ai;

import com.minecolonies.api.colony.requestsystem.request.RequestState;
import com.minecolonies.api.entity.ai.statemachine.AITarget;
import com.minecolonies.api.entity.ai.statemachine.states.IAIState;
import com.minecolonies.api.util.InventoryUtils;
import com.minecolonies.core.entity.ai.workers.crafting.AbstractEntityAICrafting;
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

public class EntityAIVulcanicMason extends AbstractEntityAICrafting<JobVulcanicMason, BuildingVulcanicMason> {

    public EntityAIVulcanicMason(@NotNull JobVulcanicMason job) {
        super(job);
        super.registerTargets(
                new AITarget<IAIState>(VULCANIC_MASON_PLACE_FLUID, this::placeFluid, TICKS_SECOND),
                new AITarget<IAIState>(VULCANIC_MASON_HARVESTING, this::harvest, TICKS_SECOND)
        );
    }


    private IAIState placeFluid() {
        final BlockPos posToPlace = building.getBlockToPlace();
        if (posToPlace == null)
        {
            return START_WORKING;
        }

        final int slot = getSlotFluid();
        if (slot == -1)
        {
//            if (InventoryUtils.getCountFromBuilding(building, CONCRETE) > 0)
//            {
//                needsCurrently = new Tuple<>(CONCRETE, STACKSIZE);
//                return GATHERING_REQUIRED_MATERIALS;
//            }

            return START_WORKING;
        }

        if (!walkToWorkPos(posToPlace))
        {
            return getState();
        }

        final ItemStack stack = worker.getInventoryCitizen().getStackInSlot(slot);
        final Block block = ((BlockItem) stack.getItem()).getBlock();
        if (InventoryUtils.attemptReduceStackInItemHandler(worker.getInventoryCitizen(), stack, 1))
        {
            world.setBlock(posToPlace, block.defaultBlockState().updateShape(Direction.DOWN, block.defaultBlockState(), world, posToPlace, posToPlace), UPDATE_FLAG);
        }

        return getState();
    }

    private int getSlotFluid() {
        return 0;
    }

    private IAIState harvest() {
        final BlockPos posToMine = building.getBlockToMine();
        if (posToMine == null)
        {
            this.resetActionsDone();
            return START_WORKING;
        }

        if (!walkToWorkPos(posToMine))
        {
            return getState();
        }

        final BlockState blockToMine = world.getBlockState(posToMine);
        if (mineBlock(posToMine))
        {
            if (currentRequest != null && currentRecipeStorage != null && blockToMine.getBlock().asItem().equals(currentRecipeStorage.getPrimaryOutput().getItem()))
            {
                currentRequest.addDelivery(new ItemStack(blockToMine.getBlock(), 1));
                job.setCraftCounter(job.getCraftCounter() + 1);
                if (job.getCraftCounter() >= job.getMaxCraftingCount())
                {
                    incrementActionsDone(getActionRewardForCraftingSuccess());
                    worker.decreaseSaturationForAction();
                    job.finishRequest(true);
                    worker.getCitizenExperienceHandler().addExperience(currentRequest.getRequest().getCount() / 2.0);
                    currentRequest = null;
                    currentRecipeStorage = null;
                    resetValues();

                    if (inventoryNeedsDump() && job.getMaxCraftingCount() == 0 && job.getProgress() == 0 && job.getCraftCounter() == 0 && currentRequest != null)
                    {
                        worker.getCitizenExperienceHandler().addExperience(currentRequest.getRequest().getCount() / 2.0);
                    }

                    return START_WORKING;
                }
            }
        }

        return getState();
    }

    @Override
    protected IAIState decide()
    {
        if ((walkTo == null && !walkToBuilding()) || job.getCurrentTask() == null)
        {
            return START_WORKING;
        }

        if (job.getActionsDone() > 0)
        {
            // Wait to dump before continuing.
            return getState();
        }

        if (currentRequest != null && currentRecipeStorage != null)
        {
            return QUERY_ITEMS;
        }

        return GET_RECIPE;
    }

    @Override
    protected IAIState craft()
    {
        if (currentRecipeStorage == null || job.getCurrentTask() == null)
        {
            return START_WORKING;
        }

        if (currentRequest == null && job.getCurrentTask() != null)
        {
            return GET_RECIPE;
        }

        if (walkTo == null && !walkToBuilding())
        {
            return getState();
        }

        currentRequest = job.getCurrentTask();

        if (currentRequest != null && (currentRequest.getState() == RequestState.CANCELLED || currentRequest.getState() == RequestState.FAILED))
        {
            incrementActionsDone(getActionRewardForCraftingSuccess());
            currentRequest = null;
            currentRecipeStorage = null;
            return START_WORKING;
        }

        return performWork();
    }

    private IAIState performWork()
    {
        final BlockPos blockToMine = building.getBlockToMine();
        if (blockToMine != null)
        {
            return VULCANIC_MASON_HARVESTING;
        }

        return START_WORKING;
    }

    @Override
    public Class<BuildingVulcanicMason> getExpectedBuildingClass() {
        return BuildingVulcanicMason.class;
    }
}
