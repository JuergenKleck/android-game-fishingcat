package info.simplyapps.game.fishingcat.rendering.objects;

public class FishAnimation extends info.simplyapps.gameengine.rendering.objects.Animation {

	public int fishNumber;
	public boolean rareFish;
	public boolean sharkFish;
	public long fishSpeed;
	// updated value
	public long moveTime;
	public boolean catched;
	// the click event
	public boolean init;
	// what happens on click
	public int clickAction;
	
	public FishAnimation() {
		super();
	}
	
}
