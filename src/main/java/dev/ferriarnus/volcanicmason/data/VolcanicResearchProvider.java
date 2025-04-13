package dev.ferriarnus.volcanicmason.data;

import com.minecolonies.api.research.AbstractResearchProvider;
import com.minecolonies.api.util.constant.Constants;
import com.minecolonies.core.generation.defaults.DefaultResearchProvider;
import dev.ferriarnus.volcanicmason.VolcanicMasonMod;
import dev.ferriarnus.volcanicmason.block.BlockRegistry;
import dev.ferriarnus.volcanicmason.buildings.BuildingRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class VolcanicResearchProvider extends AbstractResearchProvider {
    private static final ResourceLocation TECH   = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "technology");

    public static final ResourceLocation STONE = ResourceLocation.fromNamespaceAndPath(VolcanicMasonMod.MODID, "stone");
    public static final ResourceLocation BASALT = ResourceLocation.fromNamespaceAndPath(VolcanicMasonMod.MODID, "basalt");
    public static final ResourceLocation OBSIDIAN = ResourceLocation.fromNamespaceAndPath(VolcanicMasonMod.MODID, "obsidian");

    public VolcanicResearchProvider(@NotNull PackOutput packOutput, @NotNull CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider);
    }

    @Override
    protected Collection<ResearchBranch> getResearchBranchCollection() {
        return List.of();
    }

    @Override
    protected Collection<ResearchEffect> getResearchEffectCollection() {
        List<ResearchEffect> effects = new ArrayList<>();
        effects.add(new ResearchEffect(STONE).setTranslatedName("Volcanicmasons can now gather stone blocks"));
        effects.add(new ResearchEffect(BASALT).setTranslatedName("Volcanicmasons can now gather basalt blocks"));
        effects.add(new ResearchEffect(OBSIDIAN).setTranslatedName("Volcanicmasons can now gather obsidian blocks"));

        effects.add(new ResearchEffect(BuildingRegistry.VOLCANIC_MASON.get().getBuildingBlock()).setTranslatedName("Unlocks Volcanicmason").setLevels(new double[] {5}));

        return effects;
    }

    @Override
    protected Collection<Research> getResearchCollection() {
        List<Research> researches = new ArrayList<>();

        Research vulcanicmason = new Research(ResourceLocation.fromNamespaceAndPath(VolcanicMasonMod.MODID, "technology/vulcanicmason"), TECH)
                .setTranslatedName("Cobble for days")
                .setTranslatedSubtitle("Unlimited power!")
                .setIcon(BlockRegistry.VOLCANIC_MASON.asItem())
                .addBuildingRequirement("simplequarry", 1)
                .addItemCost(Items.LAVA_BUCKET, 1, provider)
                .addItemCost(Items.WATER_BUCKET, 1, provider)
                .addEffect(BuildingRegistry.VOLCANIC_MASON.get().getBuildingBlock(), 1)
                .addToList(researches);

        Research stone = new Research(ResourceLocation.fromNamespaceAndPath(VolcanicMasonMod.MODID, "technology/stone"), TECH)
                .setTranslatedName("StoneWorks")
                .setTranslatedSubtitle("For when the smelter is busy")
                .setIcon(Items.STONE)
                .addBuildingRequirement("volcanic_mason", 2)
                .addBuildingRequirement("stonesmeltery", 3)
                .setParentResearch(vulcanicmason)
                .addItemCost(Items.STONE, 128, provider)
                .addEffect(STONE, 1)
                .addToList(researches);

        Research basalt = new Research(ResourceLocation.fromNamespaceAndPath(VolcanicMasonMod.MODID, "technology/basalt"), TECH)
                .setTranslatedName("NetherWorks")
                .setTranslatedSubtitle("This is a thing?")
                .setIcon(Items.BASALT)
                .addBuildingRequirement("volcanic_mason", 3)
                .addBuildingRequirement("netherworker", 1)
                .setParentResearch(stone)
                .addItemCost(Items.BLUE_ICE, 1, provider)
                .addEffect(BASALT, 1)
                .addToList(researches);

        Research obsidian = new Research(ResourceLocation.fromNamespaceAndPath(VolcanicMasonMod.MODID, "technology/obsidian"), TECH)
                .setTranslatedName("Mining hard")
                .setTranslatedSubtitle("Well that took some time")
                .setIcon(Items.OBSIDIAN)
                .addBuildingRequirement("vulcanic_mason", 4)
                .addBuildingRequirement("netherworker", 3)
                .setParentResearch(basalt)
                .addItemCost(Items.OBSIDIAN, 32, provider)
                .addEffect(OBSIDIAN, 1)
                .addToList(researches);

        return researches;
    }
}
