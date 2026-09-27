package dev.ferriarnus.volcanicmason.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class VolcanicMasonConfig {

    public static final VolcanicMasonConfig COMMON;
    public static final ModConfigSpec COMMON_SPEC;

    public final ModConfigSpec.BooleanValue NO_RESEARCH;

    static {
        Pair<VolcanicMasonConfig, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(VolcanicMasonConfig::new);
        COMMON = commonSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();
    }

    public VolcanicMasonConfig(ModConfigSpec.Builder builder) {
        NO_RESEARCH = builder.comment("Removes the need to research the Volcanic Mason. This applies to a world once active. Use the /datapacks command to disable.")
                .define("no_research", false);
    }

}
