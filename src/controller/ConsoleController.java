package controller;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.IGameLogic;
import model.card.type.Color;
import model.card.type.ICard;
import model.player.type.IPlayer;
import view.ConsoleView;
import view.PopUpView;

/**
 * A controller which use standard input from console.
 * @author eriveros
 *
 */
public class ConsoleController implements IController,EventHandler<ActionEvent> {

  private IGameLogic game;
  private ConsoleView view;
  private Scanner in;
  private String valor = "-2";
  private int valornum = Integer.parseInt(valor);
  

  /**
   * Controller constructor. Initializes model, view, and input method.
   * Also, it plays the card in discard pile.
   * @param game
   * @param viewand
   */
  public ConsoleController(IGameLogic game, ConsoleView view) {
    this.view = view;
    this.game = game;
    this.in = new Scanner(System.in);
    game.getCurrentPlayedCard().executeAction(game, this);
  }

  @Override
  public void playTurn() {
    game.startTurn(this);
    IPlayer currentPlayer = game.getCurrentPlayer();
    boolean cardPlayed = false;
    try {
		TimeUnit.MINUTES.sleep(3);
    } catch (InterruptedException e) {
		e.printStackTrace();
	}
    while (!cardPlayed) {
      ICard card = currentPlayer.getCardToPlay(game, this);
      cardPlayed = game.playCard(card, this);
      if(cardPlayed == false && currentPlayer.instanceOf()) {
    	  PopUpView naaa = new PopUpView();
    	  naaa.WrongCard();
      }
    }
  }

  @Override
  public Color askForColor() {
	Color elegido = Color.NONE;
	String col = "-2";
	PopUpView sel = new PopUpView();
	sel.colorSelect(this);
	col = valor;
	for(Color sele: elegido.getColors()) {
		if(sele.getName().equals(col)) {
			elegido = sele;
			break;
		}
	}
	valor = "-2";
	return elegido;
  }

  @Override
  public int AskForCardFromHand(IPlayer player) {
    int num = -2;
    while (num < -1 || num > player.getHandSize()) {
    	num = valornum;
    }
    valor = "-2";
    return num;
  }

  @Override
  public void handle(ActionEvent event) {
	valor = ((Button)event.getSource()).getText();
  }

@Override
public void showMessage(String message) {
	// TODO Auto-generated method stub
	
}

@Override
public void updatePlayedCard() {
	// TODO Auto-generated method stub
	
}

}
