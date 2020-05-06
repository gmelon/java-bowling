package bowling;

import bowling.domain.Player;
import bowling.domain.Players;
import bowling.dto.PlayersDto;
import bowling.ui.InputView;
import bowling.ui.OutputView;

public class BowlingGame {
    private final InputView inputView;
    private final OutputView outputView;

    public BowlingGame() {
        inputView = new InputView();
        outputView = new OutputView();
    }

    public void start() {
        Players players = Players.ofNames(inputView.getPlayers());
        outputView.printFrame(new PlayersDto(players));

        while (!players.isGameSet()) {
            try {
                Player currentPlayer = players.getCurrentPlayer();
                currentPlayer.shot(inputView.getShot(currentPlayer.name()));
                outputView.printFrame(new PlayersDto(players));
            } catch (Exception e) {
                System.out.println(String.format("input Error!! exception=%s", e.getMessage()));
            }
        }
    }

    public static void main(String[] args) {
        new BowlingGame().start();
    }
}