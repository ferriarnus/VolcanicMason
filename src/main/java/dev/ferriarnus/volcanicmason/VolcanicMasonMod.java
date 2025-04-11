package dev.ferriarnus.volcanicmason;

import dev.ferriarnus.volcanicmason.block.BlockRegistry;
import dev.ferriarnus.volcanicmason.blockentity.BlockEntityRegistry;
import dev.ferriarnus.volcanicmason.buildings.BuildingRegistry;
import dev.ferriarnus.volcanicmason.jobs.JobsRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(VolcanicMasonMod.MODID)
public class VolcanicMasonMod {
    public static final String MODID = "volcanicmason";

    public VolcanicMasonMod(IEventBus modEventBus) {
        BlockEntityRegistry.register(modEventBus);
        BlockRegistry.register(modEventBus);

        BuildingRegistry.register(modEventBus);
        JobsRegistry.register(modEventBus);
    }
}
