package dev.ferriarnus.vulcanicmason.entity.ai;

import com.minecolonies.api.entity.ai.statemachine.states.IAIState;

public enum VulcanicMasonStates implements IAIState {
    VULCANIC_MASON_HARVESTING(true),
    VULCANIC_MASON_PLACE_FLUID(true);

    private boolean isOkayToEat;
    VulcanicMasonStates(final boolean okayToEat)
    {
        this.isOkayToEat = okayToEat;
    }


    @Override
    public boolean isOkayToEat() {
        return isOkayToEat;
    }
}