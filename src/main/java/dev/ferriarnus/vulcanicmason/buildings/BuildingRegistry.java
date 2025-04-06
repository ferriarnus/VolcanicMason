package dev.ferriarnus.vulcanicmason.buildings;

import com.minecolonies.api.colony.buildings.modules.IBuildingModuleView;
import com.minecolonies.api.colony.buildings.registry.BuildingEntry;
import com.minecolonies.api.entity.citizen.Skill;
import com.minecolonies.api.util.constant.Constants;
import com.minecolonies.core.colony.buildings.AbstractBuilding;
import com.minecolonies.core.colony.buildings.modules.SettingsModule;
import com.minecolonies.core.colony.buildings.modules.SimpleCraftingModule;
import com.minecolonies.core.colony.buildings.modules.WorkerBuildingModule;
import com.minecolonies.core.colony.buildings.modules.settings.BlockSetting;
import com.minecolonies.core.colony.buildings.modules.settings.BoolSetting;
import com.minecolonies.core.colony.buildings.modules.settings.IntSetting;
import com.minecolonies.core.colony.buildings.moduleviews.CraftingModuleView;
import com.minecolonies.core.colony.buildings.moduleviews.SettingsModuleView;
import com.minecolonies.core.colony.buildings.moduleviews.WorkerBuildingModuleView;
import com.minecolonies.core.colony.buildings.views.EmptyView;
import com.minecolonies.core.colony.buildings.workerbuildings.BuildingCowboy;
import dev.ferriarnus.vulcanicmason.VulcanicMasonMod;
import dev.ferriarnus.vulcanicmason.block.BlockRegistry;
import dev.ferriarnus.vulcanicmason.jobs.JobsRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.minecolonies.core.colony.buildings.modules.BuildingModules.MIN_STOCK;
import static com.minecolonies.core.colony.buildings.modules.BuildingModules.STATS_MODULE;

public class BuildingRegistry {

    private final static DeferredRegister<BuildingEntry> BUILDINGS = DeferredRegister.create(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "buildings"), VulcanicMasonMod.MODID);

    public static void register(IEventBus eventBus) {
        BUILDINGS.register(eventBus);
    }

    public static final BuildingEntry.ModuleProducer<WorkerBuildingModule,WorkerBuildingModuleView> VULCANIC_MASON_WORK =
            new BuildingEntry.ModuleProducer<>("vulcanic_mason_work", () -> new WorkerBuildingModule(JobsRegistry.VULCANIC_MASON.get(), Skill.Strength, Skill.Dexterity, false, (b) -> 1),
                    () -> WorkerBuildingModuleView::new);

    public static final BuildingEntry.ModuleProducer<BuildingVulcanicMason.LimitedMiningModule, IBuildingModuleView> VULCANIC_MASON_CRAFT =
            new BuildingEntry.ModuleProducer<>("vulcanic_mason_craft", BuildingVulcanicMason.LimitedMiningModule::new, null);

    public static final BuildingEntry.ModuleProducer<SettingsModule,SettingsModuleView> VULCANIC_MASON_SETTINGS  =
            new BuildingEntry.ModuleProducer<>("vulcanic_mason_settings", () -> new SettingsModule()
                    .with(BuildingVulcanicMason.MINE_AMOUNT, new IntSetting(16))
                    .with(BuildingVulcanicMason.MODE, new BlockSetting((BlockItem) Blocks.COBBLESTONE.asItem())),
                    () -> SettingsModuleView::new);

    public static final DeferredHolder<BuildingEntry,BuildingEntry> VULCANIC_MASON = BUILDINGS.register("vulcanic_mason", () -> new BuildingEntry.Builder()
            .setBuildingBlock(BlockRegistry.VULCANIC_MASON.get())
            .setBuildingProducer(BuildingVulcanicMason::new)
            .setBuildingViewProducer(() -> EmptyView::new)
            .setRegistryName(ResourceLocation.fromNamespaceAndPath(VulcanicMasonMod.MODID, "vulcanic_mason"))
            .addBuildingModuleProducer(VULCANIC_MASON_WORK)
            .addBuildingModuleProducer(VULCANIC_MASON_CRAFT)
            .addBuildingModuleProducer(VULCANIC_MASON_SETTINGS)
            .createBuildingEntry());
}
