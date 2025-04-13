package dev.ferriarnus.volcanicmason;

import com.minecolonies.api.colony.requestsystem.StandardFactoryController;
import com.minecolonies.api.util.constant.Constants;
import com.minecolonies.core.generation.DatagenLootTableManager;
import com.minecolonies.core.generation.defaults.DefaultEnchantmentProvider;
import dev.ferriarnus.volcanicmason.block.BlockRegistry;
import dev.ferriarnus.volcanicmason.blockentity.BlockEntityRegistry;
import dev.ferriarnus.volcanicmason.buildings.BuildingRegistry;
import dev.ferriarnus.volcanicmason.data.VolcanicResearchProvider;
import dev.ferriarnus.volcanicmason.jobs.JobsRegistry;
import dev.ferriarnus.volcanicmason.settings.ModeSetting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = VolcanicMasonMod.MODID)
@Mod(VolcanicMasonMod.MODID)
public class VolcanicMasonMod {
    public static final String MODID = "volcanicmason";

    public VolcanicMasonMod(IEventBus modEventBus) {
        BlockEntityRegistry.register(modEventBus);
        BlockRegistry.register(modEventBus);

        BuildingRegistry.register(modEventBus);
        JobsRegistry.register(modEventBus);
    }

    @SubscribeEvent
    public static void preInit(final FMLCommonSetupEvent event) {
        StandardFactoryController.getInstance().registerNewFactory(new ModeSetting.ModeSettingFactory());
    }

    @SubscribeEvent
    public static void dataGeneratorSetup(final GatherDataEvent event) {
        final DataGenerator generator = event.getGenerator();
        RegistrySetBuilder enchRegBuilder = new RegistrySetBuilder().add(Registries.ENCHANTMENT, DefaultEnchantmentProvider::bootstrap);
        DatapackBuiltinEntriesProvider enchRegProvider = new DatapackBuiltinEntriesProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), enchRegBuilder, Set.of(Constants.MOD_ID, "minecraft"));
        generator.addProvider(true, enchRegProvider);
        final CompletableFuture<HolderLookup.Provider> provider = enchRegProvider.getRegistryProvider().thenApply(p -> new DatagenLootTableManager(p, event.getExistingFileHelper()));

        generator.addProvider(event.includeServer(), new VolcanicResearchProvider(generator.getPackOutput(), provider));

    }

}
