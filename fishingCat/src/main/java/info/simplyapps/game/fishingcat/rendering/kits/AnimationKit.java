package info.simplyapps.game.fishingcat.rendering.kits;

import info.simplyapps.game.fishingcat.rendering.objects.FishAnimation;

public class AnimationKit extends info.simplyapps.gameengine.rendering.kits.AnimationKit {

	public static void addFishAnimation(FishAnimation animation, int gReference, int delay, int fishNumber) {
		addAnimation(animation, gReference, delay);
		animation.fishNumber = fishNumber;
	}
	
}
