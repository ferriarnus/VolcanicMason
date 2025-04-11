package dev.ferriarnus.volcanicmason.blockentity;

import com.minecolonies.api.tileentities.AbstractTileEntityColonyBuilding;
import com.minecolonies.core.tileentities.TileEntityColonyBuilding;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ModTileEntityColonyBuilding extends TileEntityColonyBuilding {

    public ModTileEntityColonyBuilding(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.MOD_BUILDING.get(), pos, state);
    }

    public ModTileEntityColonyBuilding(BlockEntityType<? extends AbstractTileEntityColonyBuilding> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
