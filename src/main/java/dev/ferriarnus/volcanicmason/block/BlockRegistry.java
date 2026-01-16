package dev.ferriarnus.volcanicmason.block;

import com.minecolonies.api.items.ItemBlockHut;
import dev.ferriarnus.volcanicmason.VolcanicMasonMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(VolcanicMasonMod.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(VolcanicMasonMod.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VolcanicMasonMod.MODID);

    public static void register(IEventBus event) {
        BLOCKS.register(event);
        ITEMS.register(event);
        CREATIVE_MODE_TABS.register(event);
    }

    public static final DeferredBlock<VolcanicMasonHutBlock> VOLCANIC_MASON = BLOCKS.register("volcanic_mason", VolcanicMasonHutBlock::new);
    public static final DeferredItem<ItemBlockHut> VOLCANIC_MASON_ITEM = ITEMS.register("volcanic_mason", () -> new ItemBlockHut(VOLCANIC_MASON.get(), new Item.Properties()));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> VOLCANIC_MASON_TAB = CREATIVE_MODE_TABS.register("volcanic_mason", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.volcanic_mason"))
            .icon(() -> VOLCANIC_MASON_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(VOLCANIC_MASON_ITEM.get());
            }).build());

}
