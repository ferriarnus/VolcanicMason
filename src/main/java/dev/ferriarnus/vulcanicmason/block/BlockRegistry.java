package dev.ferriarnus.vulcanicmason.block;

import com.minecolonies.api.items.ItemBlockHut;
import dev.ferriarnus.vulcanicmason.VulcanicMasonMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry {
    public static DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(VulcanicMasonMod.MODID);
    public static DeferredRegister.Items ITEMS = DeferredRegister.createItems(VulcanicMasonMod.MODID);

    public static void register(IEventBus event) {
        BLOCKS.register(event);
        ITEMS.register(event);
    }

    public static DeferredBlock<VulcanicMasonHutBlock> VULCANIC_MASON = BLOCKS.register("vulcanicmason", VulcanicMasonHutBlock::new);
    public static DeferredItem<ItemBlockHut> VULCANIC_MASON_ITEM = ITEMS.register("vulcanicmason", () -> new ItemBlockHut(VULCANIC_MASON.get(), new Item.Properties()));


}
