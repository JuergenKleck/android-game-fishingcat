package info.simplyapps.game.fishingcat.rendering.kits;

import info.simplyapps.game.fishingcat.rendering.objects.FishObject;

public class Renderkit extends info.simplyapps.gameengine.rendering.kits.Renderkit {

	public static FishObject loadFishObject(int clickAction) {
		FishObject obj = new FishObject();
		obj.clickAction = clickAction;
		return obj;
	}
	
}
