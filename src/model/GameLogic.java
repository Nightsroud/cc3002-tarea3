package model;

import java.util.Observable;

import controller.IController;
import model.card.type.ICard;
import model.player.type.IPlayer;
import model.player.IPlayerListBuilder;
import model.player.IPlayerManager;
import model.player.PlayerManager;
import model.card.CardPilesManager;
import model.card.ICardPilesManager;
import model.card.deck.IDeckStrategy;

/**
 * Logica del juego hecha funcion
 * @author NICOLAS
 *
 */
public class GameLogic extends Observable implements IGameLogic {
	
	protected IPlayerManager pm;
	protected ICardPilesManager cpm;
	protected int DrawWell = 0;
	
	/**
	 * Constructor del juego
	 * @param playerlist Lista de jugadores para crear el manager
	 * @param deck estrategia usada para crear el deck
	 */
	public GameLogic(IPlayerListBuilder playerlist, IDeckStrategy deck) {
		this.pm = new PlayerManager(playerlist.buildPlayerList());
		this.cpm = new CardPilesManager(deck);
		for(IPlayer player : pm.getPlayers()) {
		      cpm.addCardsToPlayer(player, 7);
		    }
	}
	
	/**
	 * true si el juego termino, acorde a las reglas del UNO.
	 */
	@Override
	public boolean hasEnded() {
		return this.getCurrentPlayer().getHandSize() == 0;
	}
	
	/**
	 * Consigue al jugador actual
	 * @return jugador de turno
	 */
	@Override
	public IPlayer getCurrentPlayer() {
		return this.pm.getCurrentPlayer();
	}
	
	/**
	 * Consigue la ultima carta jugada, la primera en la pila de descartes.
	 * @return ultima carta jugada
	 */
	@Override
	public ICard getCurrentPlayedCard() {
		return this.cpm.getCurrentPlayedCard();
	}
	
	/**
	 * Si un jugador le queda una carta, aparece pop diciendo UNO!
	 */
	@Override
	public void autoShoutUNO(IController ctrl) {
		if(!this.getCurrentPlayer().hasSaidUNO() && this.getCurrentPlayer().hasOneCard()) {
			ctrl.showMessage("UNO!");
			this.getCurrentPlayer().setSaidUNO(true);
		}
	}
	
	/**
	 * Inicia el turno del siguiente jugador, cambia algunas variable de entorno.
	 */
	@Override
	public void startTurn(IController ctrl) {
		this.autoShoutUNO(ctrl);
		this.pm.startTurn();
	}
	
	/**
	 * Se salta al siguiente jugador.
	 */
	@Override
	public void skipPlayer(IController ctrl) {
		this.startTurn(ctrl);
	}
	
	/**
	 * Añade cartas al drawWell.
	 */
	@Override
	public void addToDrawWell(int number) {
		this.DrawWell += number;
	}
	
	/**
	 * Devuelve el drawWell a 0.
	 */
	@Override
	public void resetDrawWell() {
		this.DrawWell = 0;
	}

	/**
	 * Pregunta si el drawWell esta vacio
	 * @return true si si, false si no.
	 */
	@Override
	public boolean isDrawWellEmpty() {
		return this.DrawWell == 0;
	}
	
	/**
	 * player roba cartas del drawWell
	 */
	@Override
	public void drawCardsFromWell(IPlayer player, IController ctrl) {
		this.cpm.addCardsToPlayer(player, DrawWell);
		ctrl.showMessage(player + " ha robado " + DrawWell+ " cartas del mazo.");
	}

	/**
	 * Invierte la direccion de flujo del juego.
	 */
	@Override
	public void invertDirection() {
		this.pm.invertDirection();
	}

	/**
	 * Juega playedCard.
	 * @return si la carta cumplia con las condiciones de ser jugable retoran true, de lo contrario false.
	 */
	@Override
	public boolean playCard(ICard playedCard, IController ctrl) {
		if(playedCard.isPlayableOver(this.getCurrentPlayedCard())){
			this.getCurrentPlayer().removeCardFromHand(playedCard);
			cpm.discard(playedCard);
			playedCard.executeAction(this, ctrl);
			setChanged();
		    notifyObservers();
			return true;
		}
		return false;
	}

	/**
	 * player roba una carta
	 * @return ICard, la carta que roba player.
	 */
	@Override
	public ICard drawOneCard(IPlayer player) {
		return this.cpm.addCardsToPlayer(player, 1).get(0);
	}

	/**
	 * Anuncia quien es el ganador.
	 */
	@Override
	public void announceWinner(IController ctrl) {
		ctrl.showMessage("EL ganador es el " + this.getCurrentPlayer());
	}

}
