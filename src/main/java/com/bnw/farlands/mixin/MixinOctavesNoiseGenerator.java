package com.bnw.farlands.mixin;


import com.bnw.farlands.ConfigManager;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({OctavesNoiseGenerator.class})
public abstract class MixinOctavesNoiseGenerator {
    @Inject(method = {"wrap"}, at = {@At("RETURN")}, cancellable = true)
    private static void wrap(double value, CallbackInfoReturnable<Double> cir) {
        if(ConfigManager.getInstance().isFarlandsEnabled())
            cir.setReturnValue(value);
    }
}
