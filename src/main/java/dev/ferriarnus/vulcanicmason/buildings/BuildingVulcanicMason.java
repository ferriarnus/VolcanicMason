package dev.ferriarnus.vulcanicmason.buildings;

import com.minecolonies.api.colony.IColony;
import com.minecolonies.api.colony.buildings.modules.AbstractBuildingModule;
import com.minecolonies.api.colony.buildings.modules.IBuildingEventsModule;
import com.minecolonies.api.colony.buildings.modules.IPersistentModule;
import com.minecolonies.api.colony.buildings.modules.settings.ISettingKey;
import com.minecolonies.api.equipment.ModEquipmentTypes;
import com.minecolonies.api.util.BlockPosUtil;
import com.minecolonies.api.util.ItemStackUtils;
import com.minecolonies.core.colony.buildings.AbstractBuilding;
import com.minecolonies.core.colony.buildings.modules.settings.BlockSetting;
import com.minecolonies.core.colony.buildings.modules.settings.IntSetting;
import com.minecolonies.core.colony.buildings.modules.settings.SettingKey;
import dev.ferriarnus.vulcanicmason.VulcanicMasonMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.minecolonies.api.util.constant.EquipmentLevelConstants.TOOL_LEVEL_WOOD_OR_GOLD;

public class BuildingVulcanicMason extends AbstractBuilding {

    public static final String VULCANIC_MASON = "vulcanic_mason";
    private static final String TAG_CLOCATION = "cobble";
    private static final String TAG_SLOCATION = "stone";
    private static final String TAG_OLOCATION = "obsidian";
    private static final String TAG_BLOCATION = "basalt";

    public static final ISettingKey<IntSetting> MINE_AMOUNT = new SettingKey<>(IntSetting.class, ResourceLocation.fromNamespaceAndPath(VulcanicMasonMod.MODID, "mineamount"));
    public static final ISettingKey<BlockSetting> MODE = new SettingKey<>(BlockSetting.class, ResourceLocation.fromNamespaceAndPath(VulcanicMasonMod.MODID, "mode"));

    protected BlockPos cobbleLocation;
    protected BlockPos stoneLocation;
    protected BlockPos obsidianLocation;
    protected BlockPos basaltLocation;


    protected BuildingVulcanicMason(@NotNull IColony colony, BlockPos pos) {
        super(colony, pos);
        keepX.put(itemStack -> ItemStackUtils.hasEquipmentLevel(itemStack, ModEquipmentTypes.pickaxe.get(), TOOL_LEVEL_WOOD_OR_GOLD, getMaxEquipmentLevel()), new Tuple<>(1, true));
    }

    @Override
    public String getSchematicName() {
        return VULCANIC_MASON;
    }

    public BlockPos getBlockToMine() {
        if (cobbleLocation == null) {
            loadPos();
        }
        return stoneLocation;
    }

    public BlockPos getBlockToPlace() {
        return obsidianLocation;
    }

    @Override
    public void deserializeNBT(@NotNull final HolderLookup.Provider provider, final CompoundTag compound) {
        super.deserializeNBT(provider, compound);

        cobbleLocation = BlockPosUtil.readOrNull(compound, TAG_CLOCATION);
        stoneLocation = BlockPosUtil.readOrNull(compound, TAG_SLOCATION);
        obsidianLocation = BlockPosUtil.readOrNull(compound, TAG_OLOCATION);
        basaltLocation = BlockPosUtil.readOrNull(compound, TAG_BLOCATION);
    }

    @Override
    public CompoundTag serializeNBT(@NotNull final HolderLookup.Provider provider) {
        final CompoundTag compound = super.serializeNBT(provider);

        BlockPosUtil.writeOptional(compound, TAG_CLOCATION, cobbleLocation);
        BlockPosUtil.writeOptional(compound, TAG_SLOCATION, stoneLocation);
        BlockPosUtil.writeOptional(compound, TAG_OLOCATION, obsidianLocation);
        BlockPosUtil.writeOptional(compound, TAG_BLOCATION, basaltLocation);

        return compound;
    }

    private void loadPos()
    {
        final Map<String, Set<BlockPos>> map = tileEntity.getWorldTagNamePosMap();
        final Set<BlockPos> cobblePos = map.getOrDefault(TAG_CLOCATION, new HashSet<>());
        final Set<BlockPos> stonePos = map.getOrDefault(TAG_SLOCATION, new HashSet<>());
        final Set<BlockPos> obsidianPos = map.getOrDefault(TAG_OLOCATION, new HashSet<>());
        final Set<BlockPos> basaltPos = map.getOrDefault(TAG_BLOCATION, new HashSet<>());

        cobbleLocation = cobblePos.iterator().next();
        stoneLocation = stonePos.iterator().next();
        obsidianLocation = obsidianPos.iterator().next();
        basaltLocation = basaltPos.iterator().next();

    }

    public static class LimitedMiningModule extends AbstractBuildingModule implements IBuildingEventsModule, IPersistentModule {

        private int mined = 0;

        public boolean canMine() {
            return mined < this.getBuilding().getSetting(MINE_AMOUNT).getValue();
        }

        public void mine() {
            mined++;
        }

        @Override
        public void serializeNBT(@NotNull HolderLookup.Provider provider, CompoundTag compound) {
            compound.putInt("mined", mined);
        }

        @Override
        public void deserializeNBT(@NotNull HolderLookup.Provider provider, CompoundTag compound) {
            mined = compound.getInt("mined");
        }

        @Override
        public void onWakeUp()
        {
            this.mined = 0;
        }
    }

    public enum Mode {
        COBBLESTONE,
        STONE,
        OBSIDIAN,
        BASALT
    }
}
