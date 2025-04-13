package dev.ferriarnus.volcanicmason.buildings;

import com.minecolonies.api.colony.buildings.modules.IBuildingModuleView;
import com.minecolonies.api.colony.buildings.registry.BuildingEntry;
import com.minecolonies.api.entity.citizen.Skill;
import com.minecolonies.api.util.constant.Constants;
import com.minecolonies.core.colony.buildings.modules.BuildingModules;
import com.minecolonies.core.colony.buildings.modules.SettingsModule;
import com.minecolonies.core.colony.buildings.modules.WorkerBuildingModule;
import com.minecolonies.core.colony.buildings.modules.settings.BlockSetting;
import com.minecolonies.core.colony.buildings.modules.settings.IntSetting;
import com.minecolonies.core.colony.buildings.moduleviews.SettingsModuleView;
import com.minecolonies.core.colony.buildings.moduleviews.WorkerBuildingModuleView;
import com.minecolonies.core.colony.buildings.views.EmptyView;
import dev.ferriarnus.volcanicmason.VolcanicMasonMod;
import dev.ferriarnus.volcanicmason.block.BlockRegistry;
import dev.ferriarnus.volcanicmason.jobs.JobsRegistry;
import dev.ferriarnus.volcanicmason.settings.ModeSetting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BuildingRegistry {

    private final static DeferredRegister<BuildingEntry> BUILDINGS = DeferredRegister.create(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "buildings"), VolcanicMasonMod.MODID);

    public static void register(IEventBus eventBus) {
        BUILDINGS.register(eventBus);
    }

    public static final BuildingEntry.ModuleProducer<WorkerBuildingModule,WorkerBuildingModuleView> VOLCANIC_MASON_WORK =
            new BuildingEntry.ModuleProducer<>("volcanic_mason_work", () -> new WorkerBuildingModule(JobsRegistry.VOLCANIC_MASON.get(), Skill.Strength, Skill.Dexterity, false, (b) -> 1),
                    () -> WorkerBuildingModuleView::new);

    public static final BuildingEntry.ModuleProducer<BuildingVolcanicMason.LimitedMiningModule, IBuildingModuleView> VOLCANIC_MASON_CRAFT =
            new BuildingEntry.ModuleProducer<>("volcanic_mason_craft", BuildingVolcanicMason.LimitedMiningModule::new, null);

    public static final BuildingEntry.ModuleProducer<SettingsModule,SettingsModuleView> VOLCANIC_MASON_SETTINGS =
            new BuildingEntry.ModuleProducer<>("volcanic_mason_settings", () -> new SettingsModule()
                    .with(BuildingVolcanicMason.MINE_AMOUNT, new IntSetting(16))
                    .with(BuildingVolcanicMason.MODE, new ModeSetting()),
                    () -> SettingsModuleView::new);

    public static final DeferredHolder<BuildingEntry,BuildingEntry> VOLCANIC_MASON = BUILDINGS.register("volcanic_mason", () -> new BuildingEntry.Builder()
            .setBuildingBlock(BlockRegistry.VOLCANIC_MASON.get())
            .setBuildingProducer(BuildingVolcanicMason::new)
            .setBuildingViewProducer(() -> EmptyView::new)
            .setRegistryName(ResourceLocation.fromNamespaceAndPath(VolcanicMasonMod.MODID, "volcanic_mason"))
            .addBuildingModuleProducer(VOLCANIC_MASON_WORK)
            .addBuildingModuleProducer(VOLCANIC_MASON_CRAFT)
            .addBuildingModuleProducer(VOLCANIC_MASON_SETTINGS)
            .addBuildingModuleProducer(BuildingModules.MIN_STOCK)
            .addBuildingModuleProducer(BuildingModules.STATS_MODULE)
            .createBuildingEntry());
}
