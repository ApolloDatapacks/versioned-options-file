package dev.worldgen.options.mixin.client;

import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {

	@Inject(
		method = "<init>(Lnet/minecraft/client/MinecraftClient;Ljava/io/File;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/option/GameOptions;load()V"
		)
	)
	private void versionedOptionsFile$setNewConfigFileName (MinecraftClient client, File optionsFile, CallbackInfo ci) throws Exception {
		optionsFile = new File(optionsFile, "options");
		try {
			optionsFile.mkdir();
		} catch (Exception e) {
			throw new Exception(e);
		}
		((GameOptions)(Object)this).optionsFile = new File(optionsFile, SharedConstants.getGameVersion().getName()+".txt");
	}
}
