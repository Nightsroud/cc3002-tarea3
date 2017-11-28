package model.player;

import java.util.ArrayList;

import model.player.type.IPlayer;

/**
 * Class that builds a player list following a FIFO pattern.
 * 
 */
public class PlayerListBuilder implements IPlayerListBuilder {
	
	protected ArrayList<IPlayer> players = new ArrayList<>();
	
	public PlayerListBuilder() {
		this.players.clear();
	}
	
	/**
	  * Adds the player to the queue
	  * @param player The player to be added
	  */
	@Override
	public void addPlayer(IPlayer player) {
		this.players.add(player);
	}
	
	/**
	 * Empties the queue into an ArrayList, then returns the ArrayList.
	 * @return An ArrayList with the players in the queue
	 */
	@Override
	public ArrayList<IPlayer> buildPlayerList() {
		return this.players;
	}

}
