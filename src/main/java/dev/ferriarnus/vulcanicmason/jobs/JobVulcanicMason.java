package dev.ferriarnus.vulcanicmason.jobs;

import com.minecolonies.api.colony.ICitizenData;
import com.minecolonies.core.colony.jobs.AbstractJobCrafter;
import dev.ferriarnus.vulcanicmason.entity.ai.EntityAIVulcanicMason;

public class JobVulcanicMason extends AbstractJobCrafter<EntityAIVulcanicMason, JobVulcanicMason> {

    public JobVulcanicMason(ICitizenData entity) {
        super(entity);
    }

    @Override
    public EntityAIVulcanicMason generateAI() {
        return new EntityAIVulcanicMason(this);
    }
}
