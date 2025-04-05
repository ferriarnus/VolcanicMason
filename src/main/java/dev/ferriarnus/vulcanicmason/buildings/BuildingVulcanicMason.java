package dev.ferriarnus.vulcanicmason.buildings;

import com.minecolonies.api.colony.IColony;
import com.minecolonies.core.colony.buildings.AbstractBuilding;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

public class BuildingVulcanicMason extends AbstractBuilding {

    public static final String VULCANICMASON = "vulcanicmason";

    protected BuildingVulcanicMason(@NotNull IColony colony, BlockPos pos) {
        super(colony, pos);
    }

    @Override
    public String getSchematicName() {
        return VULCANICMASON;
    }

    public BlockPos getBlockToMine() {
        return BlockPos.ZERO;
    }

    public BlockPos getBlockToPlace() {
        return BlockPos.ZERO;
    }
}
