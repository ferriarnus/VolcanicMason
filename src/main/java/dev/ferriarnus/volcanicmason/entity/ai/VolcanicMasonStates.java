package dev.ferriarnus.volcanicmason.entity.ai;

import com.minecolonies.api.entity.ai.statemachine.states.IAIState;

public enum VolcanicMasonStates implements IAIState {
    VOLCANIC_MASON_HARVESTING(true),
    VOLCANIC_MASON_PLACE_FLUID(true);

    private final boolean isOkayToEat;

    VolcanicMasonStates(final boolean okayToEat)
    {
        this.isOkayToEat = okayToEat;
    }

    @Override
    public boolean isOkayToEat() {
        return isOkayToEat;
    }
}