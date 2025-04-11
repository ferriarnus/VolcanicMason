package dev.ferriarnus.volcanicmason.block;

import com.minecolonies.api.items.ItemBlockHut;
import dev.ferriarnus.volcanicmason.VolcanicMasonMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry {
    public static DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(VolcanicMasonMod.MODID);
    public static DeferredRegister.Items ITEMS = DeferredRegister.createItems(VolcanicMasonMod.MODID);

    public static void register(IEventBus event) {
        BLOCKS.register(event);
        ITEMS.register(event);
    }

    public static DeferredBlock<VolcanicMasonHutBlock> VOLCANIC_MASON = BLOCKS.register("volcanic_mason", VolcanicMasonHutBlock::new);
    public static DeferredItem<ItemBlockHut> VOLCANIC_MASON_ITEM = ITEMS.register("volcanic_mason", () -> new ItemBlockHut(VOLCANIC_MASON.get(), new Item.Properties()));


}
