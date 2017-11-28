package view;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import controller.CardController;
import controller.ConsoleController;
import model.GameLogic;
import model.IGameLogic;
import model.card.deck.NormalStrategy;
import model.card.type.*;
import model.player.IPlayerListBuilder;
import model.player.PlayerListBuilder;
import model.player.type.ComputerPlayer;
import model.player.type.HumanPlayer;
import model.player.type.IPlayer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * View of the game. It uses the console output for showing information.
 * 
 * @author eriveros
 *
 */
public class ConsoleView extends Application implements Observer {

  private GameLogic game;
  private ConsoleController ctrl;
  private Node deck;
  private Node hand;
  
  /**
   * Constructor of the view. Associates a game to it.
   * 
   * @param game
   */
  @Override
  public void start(Stage stage){
	  IPlayerListBuilder playerbase = new PlayerListBuilder();
	  HumanPlayer jugador1 = new HumanPlayer();
	  ComputerPlayer jugador2 = new ComputerPlayer();
	  ComputerPlayer jugador3 = new ComputerPlayer();
	  ComputerPlayer jugador4 = new ComputerPlayer();
	  playerbase.addPlayer(jugador1);
	  playerbase.addPlayer(jugador2);
	  playerbase.addPlayer(jugador3);
	  playerbase.addPlayer(jugador4);
	  game = new GameLogic(playerbase, new NormalStrategy());
	  game.addObserver(this);
	  ctrl = new ConsoleController(game, this);
	  stage.setTitle("UNO!");
	  BorderPane borderpane = new BorderPane();
	  borderpane.setTop(makePlayers());
	  deck = deckndiscardImage(game.getCurrentPlayedCard().toString(), "file:assets/UnoCards/none/back.png");
	  borderpane.setCenter(deck);
	  hand = showHand();
	  borderpane.setBottom(hand);
	  stage.setScene(new Scene(borderpane, 900, 600));
	  stage.show();
  }

  private Node deckndiscardImage(String disdir, String deckdir) {
	  GridPane grid = new GridPane();
	  grid.setGridLinesVisible(false);
	  grid.setHgap(150.0);
	  ImageView deck = new ImageView(deckdir);
	  ImageView discard = new ImageView(disdir);
	  deck.setFitWidth(77);
	  deck.setPreserveRatio(true);
	  discard.setFitWidth(77);
	  discard.setPreserveRatio(true);
	  Button deckButton = new Button();
	  deckButton.setGraphic(deck);
	  deckButton.setText("-1");
	  deckButton.setOnAction(ctrl);
	  grid.addColumn(1,discard);
	  grid.addColumn(2,deckButton);
	  grid.setAlignment(Pos.CENTER);
	  return grid;
  }

public Node showHand() {
	  GridPane grid = new GridPane();
	  grid.setGridLinesVisible(false);
	  grid.setHgap(10);
	  int i = 0;
	  for(ICard icard: game.getCurrentPlayer().getHand()) {
		  if(game.getCurrentPlayer().instanceOf()) 
			  grid.add(cartaABoton(icard, i), i, 0);
		  else {
			  ImageView cpu = new ImageView("file:assets/UnoCards/none/back.png");
			  cpu.setSmooth(true);
		      cpu.setCache(true);
		      cpu.setFitHeight(150); 
		      cpu.setFitWidth(250);
			  cpu.setPreserveRatio(true);;
			  grid.add(cpu, i, 0);
		  }
		  i++;
	  }
	  ScrollPane flow = new ScrollPane(grid);
	  return flow;
}

private Node makePlayers() {
	StringBuilder count = new StringBuilder();
	count.append(game.getCurrentPlayer().getHandSize());
	count.append(" ");
	count.append("card(s)");
    BorderPane borderpane = new BorderPane();
    GridPane current = new GridPane();
    current.setHgap(70.0);
    Text indicador = new Text();
    indicador.setFont(new Font(20));
    indicador.setText("Current Player");
    indicador.setStyle("-fx-underline: true;");
    current.addColumn(1, indicador);
    borderpane.setTop(current);
    GridPane players = new GridPane();
    players.setHgap(50.0);
    for (int i = 1; i <= 4; i++) {
      BorderPane player = new BorderPane();
      Text text = new Text();
      text.setFont(new Font(20));
      text.setText("Player " + i);
      player.setTop(text);
      Text cards = new Text();
      cards.setFont(new Font(20));
      cards.setText(count.toString());
      player.setBottom(cards);
      players.addColumn(2 * i, player);
      if (i != 4) {
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(new Double[]{
            120.0 + i * 200, 15.0,
            140.0 + i * 200, 25.0,
            120.0 + i * 200, 35.0 });
        players.addColumn(2 * i + 1, polygon);
      }
      game.startTurn(ctrl);
    }
    borderpane.setBottom(players);
    return borderpane;
  }

  /**
   * Shows a player's hand.
   * 
   * @param player player with the hand you need to show.
   */

  private Node cartaABoton(ICard iCard, int num) {
	  StringBuilder numer = new StringBuilder();
	  numer.append(num);
	  Image card = new Image(iCard.toString());
      ImageView cardView = new ImageView(card);
      cardView.setSmooth(true);
      cardView.setCache(true);
      cardView.setFitHeight(150); 
      cardView.setFitWidth(250);
      cardView.setPreserveRatio(true);
      Button btn = new Button();
      btn.setText(numer.toString());
      btn.setGraphic(cardView);
      btn.setOnAction(new CardController(iCard, ctrl, game));
      return btn;
  }



/**
   * Shows a custom message.
   * 
   * @param message custom message
   */
  public void showMessage(String message) {
    System.out.println(message);
  }

  /**
   * Shows the last card played.
   * 
   * @param player
   * @param card
   */
  public void updatePlayedCard() {
    System.out.println("[Carta Descartada]: " + game.getCurrentPlayedCard().toString());
  }

  @Override
  public void update(Observable o, Object arg) {
	  updateDiscardPile(((GameLogic) o).getCurrentPlayedCard());
	  updateCurrentPlayerHand();
	  try {
		TimeUnit.SECONDS.sleep(10);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	  ctrl.playTurn();
	  
  }
  
  private void updateCurrentPlayerHand() {
	  hand = showHand();
  }

  private void updateDiscardPile(ICard currentPlayedCard) {
	  deck = deckndiscardImage(currentPlayedCard.toString(), "file:assets/UnoCards/none/back.png");
  }

public static void main(String[] args) {
	  launch(args);
  }

}
