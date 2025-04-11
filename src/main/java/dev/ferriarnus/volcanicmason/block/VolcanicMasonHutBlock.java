package dev.ferriarnus.volcanicmason.block;

import com.minecolonies.api.blocks.AbstractBlockHut;
import com.minecolonies.api.colony.buildings.registry.BuildingEntry;
import com.minecolonies.core.tileentities.TileEntityColonyBuilding;
import dev.ferriarnus.volcanicmason.blockentity.BlockEntityRegistry;
import dev.ferriarnus.volcanicmason.buildings.BuildingRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VolcanicMasonHutBlock extends AbstractBlockHut<VolcanicMasonHutBlock> {

    public static final String VOLCANIC_MASON = "volcanic_mason";

    @Override
    public String getHutName() {
        return VOLCANIC_MASON;
    }

    @Override
    public BuildingEntry getBuildingEntry() {
        return BuildingRegistry.VOLCANIC_MASON.get();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        TileEntityColonyBuilding building = BlockEntityRegistry.MOD_BUILDING.get().create(blockPos, blockState);
        building.registryName = this.getBuildingEntry().getRegistryName();
        return building;
    }
}
