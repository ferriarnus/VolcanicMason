package dev.ferriarnus.vulcanicmason.blockentity;

import dev.ferriarnus.vulcanicmason.VulcanicMasonMod;
import dev.ferriarnus.vulcanicmason.block.BlockRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityRegistry {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, VulcanicMasonMod.MODID);

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ModTileEntityColonyBuilding>> MOD_BUILDING = BLOCK_ENTITIES.register("mod_buildings", () -> BlockEntityType.Builder.of(ModTileEntityColonyBuilding::new, BlockRegistry.VULCANIC_MASON.get()).build(null));
}
