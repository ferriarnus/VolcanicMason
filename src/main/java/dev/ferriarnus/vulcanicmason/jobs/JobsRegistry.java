package dev.ferriarnus.vulcanicmason.jobs;

import com.minecolonies.api.colony.jobs.ModJobs;
import com.minecolonies.api.colony.jobs.registry.JobEntry;
import com.minecolonies.api.util.constant.Constants;
import com.minecolonies.core.colony.jobs.views.CrafterJobView;
import com.minecolonies.core.colony.jobs.views.DefaultJobView;
import dev.ferriarnus.vulcanicmason.VulcanicMasonMod;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class JobsRegistry {
    public final static DeferredRegister<JobEntry> JOBS = DeferredRegister.create(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "jobs"), VulcanicMasonMod.MODID);

    public static void register(IEventBus eventBus) {
        JOBS.register(eventBus);
    }

    public static final DeferredHolder<JobEntry, JobEntry> VULCANIC_MASON = register(JOBS, "vulcanic_mason", () -> new JobEntry.Builder()
            .setJobProducer(JobVulcanicMason::new)
            .setJobViewProducer(() -> DefaultJobView::new)
            .setRegistryName(ResourceLocation.fromNamespaceAndPath(VulcanicMasonMod.MODID, "vulcanic_mason"))
            .createJobEntry());

    private static DeferredHolder<JobEntry, JobEntry> register(final DeferredRegister<JobEntry> deferredRegister, final String path, final Supplier<JobEntry> supplier) {
        ModJobs.jobs.add(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path));
        return deferredRegister.register(path, supplier);
    }
}
