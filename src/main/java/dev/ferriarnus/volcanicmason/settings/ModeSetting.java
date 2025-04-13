package dev.ferriarnus.volcanicmason.settings;

import com.google.common.reflect.TypeToken;
import com.ldtteam.blockui.Pane;
import com.ldtteam.blockui.controls.ButtonImage;
import com.ldtteam.blockui.controls.ItemIcon;
import com.ldtteam.blockui.views.BOWindow;
import com.minecolonies.api.colony.buildings.modules.settings.ISetting;
import com.minecolonies.api.colony.buildings.modules.settings.ISettingKey;
import com.minecolonies.api.colony.buildings.modules.settings.ISettingsModuleView;
import com.minecolonies.api.colony.buildings.views.IBuildingView;
import com.minecolonies.api.colony.requestsystem.factory.FactoryVoidInput;
import com.minecolonies.api.colony.requestsystem.factory.IFactory;
import com.minecolonies.api.colony.requestsystem.factory.IFactoryController;
import com.minecolonies.api.research.effects.IResearchEffectManager;
import com.minecolonies.api.util.constant.TypeConstants;
import dev.ferriarnus.volcanicmason.data.VolcanicResearchProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ModeSetting implements ISetting<Modes> {
    private Modes mode = Modes.COBBLESTONE;

    @Override
    public ResourceLocation getLayoutItem() {
        return ResourceLocation.fromNamespaceAndPath("minecolonies", "gui/layouthuts/layoutcraftingsetting.xml");
    }

    @Override
    public void setupHandler(ISettingKey<?> iSettingKey, Pane pane, ISettingsModuleView iSettingsModuleView, IBuildingView iBuildingView, BOWindow boWindow) {
        pane.findPaneOfTypeByID("trigger", ButtonImage.class).setHandler((input) -> {
            List<Modes> list = new ArrayList<>();
            list.add(Modes.COBBLESTONE);
            final IResearchEffectManager effects = iBuildingView.getColony().getResearchManager().getResearchEffects();
            if (effects.getEffectStrength(VolcanicResearchProvider.STONE) > 0) {
                list.add(Modes.STONE);
            }
            if (effects.getEffectStrength(VolcanicResearchProvider.OBSIDIAN) > 0) {
                list.add(Modes.OBSIDIAN);
            }
            if (effects.getEffectStrength(VolcanicResearchProvider.BASALT) > 0) {
                list.add(Modes.BASALT);
            }
            int currentIntIndex = 0;

            for(int index = 0; index < list.size(); index++) {
                Modes mode = list.get(index);
                if (mode.equals(this.mode)) {
                    currentIntIndex = index;
                    break;
                }
            }

            int newIndex = currentIntIndex + 1;
            if (newIndex >= list.size()) {
                newIndex = 0;
            }

            this.mode = list.get(newIndex);
            iSettingsModuleView.trigger(iSettingKey);
        });
    }

    @Override
    public void render(ISettingKey<?> iSettingKey, Pane pane, ISettingsModuleView iSettingsModuleView, IBuildingView iBuildingView, BOWindow boWindow) {
        ButtonImage triggerButton = pane.findPaneOfTypeByID("trigger", ButtonImage.class);
        triggerButton.setEnabled(this.isActive(iSettingsModuleView));
        triggerButton.setText(Component.translatable(this.mode.getItem().getDescriptionId()));
        this.setHoverPane(iSettingKey, triggerButton, iSettingsModuleView);
        pane.findPaneOfTypeByID("iconfrom", ItemIcon.class).setItem(this.mode.getFluidStack());
        pane.findPaneOfTypeByID("iconto", ItemIcon.class).setItem(this.mode.getItem());
    }

    @Override
    public void copyValue(ISetting<?> iSetting) {
        if (iSetting instanceof ModeSetting modeSetting) {
            this.mode = modeSetting.mode;
        }
    }

    @Override
    public Modes getValue() {
        return mode;
    }

    public static class ModeSettingFactory implements IFactory<FactoryVoidInput, ModeSetting> {
        private static final String TAG_MODULE = "value";
        public static final TypeToken<ModeSetting> MODE = TypeToken.of(ModeSetting.class);

        public ModeSettingFactory() {
        }

        @Override
        public @NotNull TypeToken<? extends ModeSetting> getFactoryOutputType() {
            return MODE;
        }

        @Override
        public @NotNull TypeToken<? extends FactoryVoidInput> getFactoryInputType() {
            return TypeConstants.FACTORYVOIDINPUT;
        }

        @Override
        public short getSerializationId() {
            return 101;
        }

        @Override
        public @NotNull ModeSetting getNewInstance(@NotNull IFactoryController iFactoryController, @NotNull FactoryVoidInput factoryVoidInput, @NotNull Object... objects) throws IllegalArgumentException {
            return new ModeSetting();
        }

        @Override
        public @NotNull CompoundTag serialize(@NotNull HolderLookup.Provider provider, @NotNull IFactoryController iFactoryController, @NotNull ModeSetting modeSetting) {
            CompoundTag tag = new CompoundTag();
            tag.putInt(TAG_MODULE, modeSetting.mode.ordinal());
            return tag;
        }

        @Override
        public @NotNull ModeSetting deserialize(@NotNull HolderLookup.Provider provider, @NotNull IFactoryController iFactoryController, @NotNull CompoundTag compoundTag) throws Throwable {
            ModeSetting modeSetting = new ModeSetting();
            modeSetting.mode = Modes.values()[compoundTag.getInt(TAG_MODULE)];
            return modeSetting;
        }

        @NotNull
        @Override
        public void serialize(@NotNull IFactoryController iFactoryController, @NotNull ModeSetting modeSetting, RegistryFriendlyByteBuf registryFriendlyByteBuf) {
            registryFriendlyByteBuf.writeInt(modeSetting.mode.ordinal());
        }

        @Override
        public @NotNull ModeSetting deserialize(@NotNull IFactoryController iFactoryController, @NotNull RegistryFriendlyByteBuf registryFriendlyByteBuf) throws Throwable {
            ModeSetting modeSetting = new ModeSetting();
            modeSetting.mode = Modes.values()[registryFriendlyByteBuf.readInt()];
            return modeSetting;
        }
    }
}
