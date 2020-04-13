package info.simplyapps.game.fishingcat.sprites;


public class Fish {

	public long speedMin;
	public long speedMax;
	public float screenWidthScaling;
	
	public int fishReference;
	public boolean bad;
	public boolean rare;
	
	public Fish(long speedMin, long speedMax, float screenWidthScaling, int fishReference, boolean bad, boolean rare) {
		this.speedMin = speedMin;
		this.speedMax = speedMax;
		this.screenWidthScaling = screenWidthScaling;
		this.fishReference = fishReference;
		this.bad = bad;
		this.rare = rare;
	}
	
}
