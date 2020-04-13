package info.simplyapps.game.fishingcat.storage.dto;

import info.simplyapps.appengine.storage.dto.BasicTable;

import java.io.Serializable;

public class Inventory extends BasicTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6974204243261183587L;
	
	public int gameSystem;
	public boolean catMode;
	public boolean migrated;
	
}
