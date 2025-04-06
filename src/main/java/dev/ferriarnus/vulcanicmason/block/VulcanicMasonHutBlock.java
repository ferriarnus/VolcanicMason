package dev.ferriarnus.vulcanicmason.block;

import com.minecolonies.api.blocks.AbstractBlockHut;
import com.minecolonies.api.colony.buildings.registry.BuildingEntry;
import com.minecolonies.core.tileentities.TileEntityColonyBuilding;
import dev.ferriarnus.vulcanicmason.blockentity.BlockEntityRegistry;
import dev.ferriarnus.vulcanicmason.buildings.BuildingRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VulcanicMasonHutBlock extends AbstractBlockHut<VulcanicMasonHutBlock> {

    public static final String VULCANIC_MASON = "vulcanic_mason";

    @Override
    public String getHutName() {
        return VULCANIC_MASON;
    }

    @Override
    public BuildingEntry getBuildingEntry() {
        return BuildingRegistry.VULCANIC_MASON.get();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        TileEntityColonyBuilding building = BlockEntityRegistry.MOD_BUILDING.get().create(blockPos, blockState);
        building.registryName = this.getBuildingEntry().getRegistryName();
        return building;
    }
}
