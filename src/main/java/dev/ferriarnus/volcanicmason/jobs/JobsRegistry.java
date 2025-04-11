package dev.ferriarnus.volcanicmason.jobs;

import com.minecolonies.api.colony.jobs.ModJobs;
import com.minecolonies.api.colony.jobs.registry.JobEntry;
import com.minecolonies.api.util.constant.Constants;
import com.minecolonies.core.colony.jobs.views.DefaultJobView;
import dev.ferriarnus.volcanicmason.VolcanicMasonMod;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class JobsRegistry {
    public final static DeferredRegister<JobEntry> JOBS = DeferredRegister.create(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "jobs"), VolcanicMasonMod.MODID);

    public static void register(IEventBus eventBus) {
        JOBS.register(eventBus);
    }

    public static final DeferredHolder<JobEntry, JobEntry> VOLCANIC_MASON = register(JOBS, "volcanic_mason", () -> new JobEntry.Builder()
            .setJobProducer(JobVolcanicMason::new)
            .setJobViewProducer(() -> DefaultJobView::new)
            .setRegistryName(ResourceLocation.fromNamespaceAndPath(VolcanicMasonMod.MODID, "volcanic_mason"))
            .createJobEntry());

    private static DeferredHolder<JobEntry, JobEntry> register(final DeferredRegister<JobEntry> deferredRegister, final String path, final Supplier<JobEntry> supplier) {
        ModJobs.jobs.add(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path));
        return deferredRegister.register(path, supplier);
    }
}
