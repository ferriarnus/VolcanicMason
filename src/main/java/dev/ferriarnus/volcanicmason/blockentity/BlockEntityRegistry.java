package dev.ferriarnus.volcanicmason.blockentity;

import com.minecolonies.api.util.IItemHandlerCapProvider;
import dev.ferriarnus.volcanicmason.VolcanicMasonMod;
import dev.ferriarnus.volcanicmason.block.BlockRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = VolcanicMasonMod.MODID)
public class BlockEntityRegistry {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, VolcanicMasonMod.MODID);

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ModTileEntityColonyBuilding>> MOD_BUILDING = BLOCK_ENTITIES.register("mod_buildings", () -> BlockEntityType.Builder.of(ModTileEntityColonyBuilding::new, BlockRegistry.VOLCANIC_MASON.get()).build(null));

    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BlockEntityRegistry.MOD_BUILDING.get(), IItemHandlerCapProvider::getItemHandlerCap);
    }
}
