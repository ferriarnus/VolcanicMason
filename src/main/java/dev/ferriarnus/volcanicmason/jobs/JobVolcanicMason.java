package dev.ferriarnus.volcanicmason.jobs;

import com.minecolonies.api.colony.ICitizenData;
import com.minecolonies.core.colony.jobs.AbstractJob;
import dev.ferriarnus.volcanicmason.entity.ai.EntityAIVolcanicMason;

public class JobVolcanicMason extends AbstractJob<EntityAIVolcanicMason, JobVolcanicMason> {

    public JobVolcanicMason(ICitizenData entity) {
        super(entity);
    }

    @Override
    public EntityAIVolcanicMason generateAI() {
        return new EntityAIVolcanicMason(this);
    }
}
