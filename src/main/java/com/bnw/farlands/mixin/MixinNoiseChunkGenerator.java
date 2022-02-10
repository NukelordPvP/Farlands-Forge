package com.bnw.farlands.mixin;

import com.bnw.farlands.ConfigManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Supplier;

@Mixin({NoiseChunkGenerator.class})

public class MixinNoiseChunkGenerator {


    @Final
    @Shadow
    protected Supplier<DimensionSettings> settings;

    @ModifyConstant(
            constant = @Constant(
                    doubleValue = 684.412D,
                    ordinal = 0
            ),
            method = "fillNoiseColumn"
    )
    private double setCoordinateScale(double original) {
        if(!ConfigManager.getInstance().isFarlandsEnabled())
        {
            return 684.412d;
        }
        if(this.settings.get().getDefaultBlock().is(Blocks.STONE))
        {
            return ConfigManager.getInstance().getOverworldScale();

        }
        else if(this.settings.get().getDefaultBlock().is(Blocks.END_STONE))
        {
                return ConfigManager.getInstance().getEndScale();
        }
        return ConfigManager.getInstance().getNetherScale();
    }
}
