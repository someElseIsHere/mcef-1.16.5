package com.cinemamod.mcef.mixins;

import com.cinemamod.mcef.MCEF;
import com.cinemamod.mcef.internal.MCEFDownloadListener;
import com.cinemamod.mcef.internal.MCEFDownloaderMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class CefInitMixin {
	@Shadow public abstract void setScreen(@Nullable Screen guiScreen);
	
	@Inject(at = @At("HEAD"), method = "setScreen", cancellable = true)
	public void redirScreen(Screen guiScreen, CallbackInfo ci) {
		if (guiScreen instanceof TitleScreen) {
			if (MCEFDownloadListener.INSTANCE.isDone()) {
				MCEF.initialize();
			} else {
				setScreen(new MCEFDownloaderMenu((TitleScreen) guiScreen, MCEFDownloadListener.INSTANCE));
				ci.cancel();
			}
		}
	}
}
