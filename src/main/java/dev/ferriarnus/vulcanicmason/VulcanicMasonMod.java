package dev.ferriarnus.vulcanicmason;

import dev.ferriarnus.vulcanicmason.block.BlockRegistry;
import dev.ferriarnus.vulcanicmason.blockentity.BlockEntityRegistry;
import dev.ferriarnus.vulcanicmason.buildings.BuildingRegistry;
import dev.ferriarnus.vulcanicmason.jobs.JobsRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(VulcanicMasonMod.MODID)
public class VulcanicMasonMod {
    public static final String MODID = "vulcanicmason";

    public VulcanicMasonMod(IEventBus modEventBus) {
        BlockEntityRegistry.register(modEventBus);
        BlockRegistry.register(modEventBus);

        BuildingRegistry.register(modEventBus);
        JobsRegistry.register(modEventBus);
    }
}
