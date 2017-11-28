package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.GameLogic;
import model.card.type.ICard;

/**
 * Handles the actions triggered by the user.
 *
 */
public class CardController implements EventHandler<ActionEvent> {
	
	private ConsoleController ctrl;
	private GameLogic game;
	private ICard iCard;
	
	/**
	 * The controller has access to the model
	 * @param c card
	 * @param ct game controller
	 * @param g game logic
	 */
	public CardController(ICard c, ConsoleController ct, GameLogic g) {
		this.iCard = c;
		this.ctrl = ct;
		this.game = g;
	}

	@Override
	public void handle(ActionEvent event) {
		game.playCard(iCard, ctrl);
	}

}
