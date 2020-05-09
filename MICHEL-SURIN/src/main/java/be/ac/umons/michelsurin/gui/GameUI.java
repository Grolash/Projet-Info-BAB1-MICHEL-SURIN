package be.ac.umons.michelsurin.gui;

import be.ac.umons.michelsurin.engine.Action;
import be.ac.umons.michelsurin.controller.PawnController;
import be.ac.umons.michelsurin.engine.Game;
import be.ac.umons.michelsurin.engine.Rules;
import be.ac.umons.michelsurin.engine.SaverLoader;
import be.ac.umons.michelsurin.tools.Coord;
import be.ac.umons.michelsurin.world.Board;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * given game settings, run a GUI game.
 */
public class GameUI {

    /**
     * Standard cell sprite
     */
    private final Image cellImg = new Image("tile.png");

    /**
     * Array containing all the player sprite
     */
    private final Image[] playerSprite = {new Image("neo.png")
                                        , new Image("agent.png")
                                        , new Image("morpheus.png")
                                        , new Image("trinity.png")};
    /**
     * Horizontal wall sprite
     */
    private final Image wallHImg = new Image("wallH.png");
    /**
     * Vertical wall sprite
     */
    private final Image wallVImg = new Image("wallV.png");
    /**
     * Empty sprite used to "hide" the shadow wall used to give player an indication of where they will place a wall
     */
    private final Image empty = new Image("wallEmpty.png");
    /**
     * Horizontal gap between cell
     */
    public final int Hspace = 52;
    /**
     * Vertical gap between cell
     */
    public final int Vspace = 52;
    /**
     * Effect that highlight the sprite attach to it. Used to show possible move for players
     */
    private final ColorAdjust colorCell = new ColorAdjust(0.1, 0, 0.5, 0.5);
    /**
     * Effect used to show a preview of the wall a player might place
     */
    private final Shadow wallShadow = new Shadow(1, Color.DARKRED);

    /**
     * The game running
     */
    private Game game;
    /**
     * Total of player in the game
     */
    private int playerTotal;
    /**
     * Index of the player who's turn it is
     */
    private int currentPlayer;
    /**
     * Array containing all the PawnController in the game
     */
    private PawnController[] playerArray;
    /**
     * The board
     */
    private Board board;
    /**
     * Size of the board
     */
    private int boardSize;
    /**
     * The pane containing all the scene
     */
    private BorderPane mainPane;
    /**
     * Group containing all the game sprite
     */
    private Group gameContent;
    /**
     * Used for the pause menu
     */
    private VBox pauseMenu;
    /**
     * Pane used for the victory screen
     */
    private VBox victoryPane;
    /**
     * Scene where the game takes place
     */
    private Scene gameScene;
    /**
     * Scene for the victory screen
     */
    private Scene victoryScene;
    /**
     * scene for the pause menu
     */
    private Scene pauseScene;
    /**
     * The stage of the application (the window)
     */
    private Stage appStage;
    /**
     * Save the game.
     */
    private Button saveButton;
    /**
     * Allow to return to the gameScene
     */
    private Button backToGameButton;
    /**
     * In game button used to get back to the main menu while a game is still in progress
     */
    private Button backToMenuInGameButton;
    /**
     * Button used to go back to the menu AFTER a game has been won
     */
    private Button backToMenu;
    /**
     * Save and load games.
     */
    private SaverLoader saverLoader = new SaverLoader();

    /**
     * This constructor creates a game with the settings given by the menu and display it.
     * Then it will start the game with the player 0 (1st player) and given it's type, will listen for mouse event if it's
     * a human else, it will get through the AnimationTimer and call the actionHandler that will play according to the
     * AI type.
     * @param appStage {@link #appStage}
     * @param menuScene The scene containing the main menu
     * @param game the game created with the menu settings
     */
    public GameUI(Stage appStage, Scene menuScene, Game game){
        this.appStage = appStage;

        this.game = game;
        this.currentPlayer = game.getCurrentPlayer();
        this.playerArray = game.getPlayerArray();
        this.playerTotal = game.getPlayerArray().length;
        this.board = game.getBoard();
        this.boardSize = board.getSize();

        this.mainPane = new BorderPane();
        mainPane.setBackground(Background.EMPTY);
        mainPane.setMinSize(600, 600);

        this.victoryPane = new VBox();
        victoryPane.setBackground(Background.EMPTY);
        victoryPane.setMinSize(600, 600);

        this.gameContent = new Group();

        this.pauseMenu = new VBox();
        pauseMenu.setAlignment(Pos.CENTER);
        pauseMenu.setMinSize(600, 600);
        pauseMenu.setBackground(Background.EMPTY);
        pauseMenu.setSpacing(15);

        this.gameScene = new Scene(mainPane);
        this.victoryScene = new Scene(victoryPane);
        this.pauseScene = new Scene(pauseMenu);

        this.appStage.setScene(gameScene);


        //pauseScene --------------------------------------
        //save
        saveButton = new Button("Save Game");
        saveButton.setBackground(Background.EMPTY);
        saveButton.setTextFill(Color.GREEN);
        saveButton.setOnAction(e -> {
            try {
                SaverLoader.save(game);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //back to the game
        backToGameButton = new Button("Back to game");
        backToGameButton.setBackground(Background.EMPTY);
        backToGameButton.setTextFill(Color.GREEN);
        backToGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                appStage.setScene(gameScene);
            }
        });
        //back to menu
        backToMenuInGameButton = new Button("Back to main menu");
        backToMenuInGameButton.setBackground(Background.EMPTY);
        backToMenuInGameButton.setTextFill(Color.GREEN);
        backToMenuInGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //TODO confirm box for save
                appStage.setScene(menuScene);
            }
        });
        pauseMenu.getChildren().addAll(saveButton, backToGameButton, backToMenuInGameButton);
        pauseScene.getStylesheets().add("Viper.css");

        //victoryScene --------------------------------------
        Separator separator = new Separator();
        backToMenu = new Button("BACK TO THE MENU");
        backToMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                appStage.setScene(menuScene);
            }
        });
        victoryPane.setSpacing(15);
        victoryPane.setAlignment(Pos.CENTER);
        victoryScene.getStylesheets().add("Viper.css");
        //gameScene --------------------------------------
        mainPane.setCenter(gameContent);
        gameScene.setFill(Color.BLACK);

        //pauseScene access
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                    appStage.setScene(pauseScene);
                }
            }
        });

        //board drawing
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                ImageView cell = new ImageView();
                cell.setImage(cellImg);
                cell.setY(i * Vspace);
                cell.setX(j * Hspace);
                cell.setEffect(new Glow(0));
                gameContent.getChildren().add(cell);
            }
        }
        //pawn initialization
        for (int i=0; i<playerTotal; i++) {
            gameContent.getChildren().add(new ImageView());
        }
        //wall highlight
        ImageView wallHighlight = new ImageView();
        wallHighlight.setEffect(this.wallShadow);
        gameContent.getChildren().add(wallHighlight);

        updateWall();
        updatePawn();

        //TURN SYSTEM ------------------------------------------------------------------
        final boolean[] dragActive = {false}; //used to know if a dragEvent has been triggered
        final Coord[] wantedWallCoord = new Coord[1]; //used to transfer coord through events
        //CLICK HANDLING
        gameContent.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Coord clickedCell = getCoordFromPos(event.getX(), event.getY());
                PawnController ctrl = playerArray[currentPlayer];
                Coord[] possibleCell = Action.whereCanIGo(ctrl);
                Coord playerCoord = ctrl.getDependency().getCoord();
                int size = gameContent.getChildren().size();
                ImageView ghostWall = (ImageView) gameContent.getChildren().get((boardSize*boardSize) + playerTotal);

                if (dragActive[0] && event.getButton().compareTo(MouseButton.PRIMARY) == 0) {
                    //we are left-clicking on a ghost wall, we need to place it
                    resetGlowing(); //if cells are glowing the need to be reset
                    if (clickedCell.compareTo(wantedWallCoord[0]) == 0) {
                        //it's a click on a ghost wall, it means that we want it to be placed
                        if (ghostWall.getImage().equals(wallHImg)
                                && Rules.canPlaceWall(playerArray, ctrl, wantedWallCoord[0], Game.directions.get("RIGHT"))) {
                            //Hwall
                            ctrl.placeWall(clickedCell, Game.directions.get("RIGHT"));
                            ghostWall.setImage(empty);
                            updateWall();
                            currentPlayer = (currentPlayer + 1) % playerTotal;
                        } else if (ghostWall.getImage().equals(wallVImg)
                                && Rules.canPlaceWall(playerArray, ctrl, wantedWallCoord[0], Game.directions.get("UP"))) {
                            //Vwall
                            ctrl.placeWall(clickedCell, Game.directions.get("UP"));
                            ghostWall.setImage(empty);
                            updateWall();
                            currentPlayer = (currentPlayer + 1) % playerTotal;
                            game.setCurrentPlayer(currentPlayer);
                        }
                    } else {
                        //click somewhere else, we reset the ghost wall
                        ghostWall.setImage(empty);
                    }
                    dragActive[0] = false; //reset the drag
                } else {
                    //we want to move
                    if (event.getButton().compareTo(MouseButton.PRIMARY) == 0 && clickedCell.getY() < boardSize && clickedCell.getX() < boardSize
                            && ctrl.getType().equals("Human")) {
                        //click is on the current player
                        ImageView clickedCellImage = (ImageView) gameContent.getChildren().get(clickedCell.getX() + boardSize * clickedCell.getY());
                        if (playerCoord.compareTo(clickedCell) == 0) {
                            //click on pawn --> we make the reachable cell glowing
                            for (Coord coord : possibleCell) {
                                gameContent.getChildren().get(coord.getX() + (9 * coord.getY())).setEffect(colorCell);
                            }
                        } else if (clickedCell.isIn(possibleCell)
                                && clickedCellImage.getEffect().equals(colorCell)) {
                            //if click on a glowing cell (a cell where the player can go), we mote the player to it
                            int deltaY = clickedCell.getY() - playerCoord.getY();
                            int deltaX = clickedCell.getX() - playerCoord.getX();
                            Coord dir = new Coord(deltaY, deltaX);
                            ctrl.move(dir);
                            updatePawn();
                            resetGlowing();
                            //Check win
                            if (ctrl.hasWon()) {
                                Label whoWon = new Label("Congratulation ! Player " + (currentPlayer+1) + " has won !");
                                victoryPane.getChildren().add(whoWon);
                                victoryPane.getChildren().add(separator);
                                victoryPane.getChildren().add(backToMenu);
                                appStage.setScene(victoryScene);
                            } else {
                                currentPlayer= (currentPlayer + 1) % playerTotal;
                                game.setCurrentPlayer(currentPlayer);
                            }
                        } else {
                            //click is somewhere else, we reset the cell glow
                            resetGlowing();
                        }
                    }
                }
            }
        });
        //enter the wall highlight mode
        gameContent.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //we need a way to choose between H wall and V wall
                Coord currentCellCoord = getCoordFromPos(event.getX(), event.getY());
                wantedWallCoord[0] = currentCellCoord;
                int size = gameContent.getChildren().size();
                if (currentCellCoord.getY() < boardSize
                        && currentCellCoord.getY() > 0
                        && currentCellCoord.getX() < boardSize-1
                        && currentCellCoord.getX() >= 0
                        && event.getButton().compareTo(MouseButton.SECONDARY) == 0
                        && playerArray[currentPlayer].getNumbWall() > 0) {
                    //we are at a correct place for a wall to be placed
                    //now check whether we are on top of the cell or on the bottom
                    dragActive[0] = true; //tells that a drag has been triggered
                    resetGlowing(); //in case we were looking for a cell
                    //the following lines will determines if the mouse is on the upper or lower half of the cell
                    //if upper -> Hwall, if lower -> Vwall
                    if (event.getY() > currentCellCoord.getY()*(Vspace)
                            && event.getY() < (currentCellCoord.getY()*(Vspace))+(Vspace/2)
                            && Rules.canPlaceWall(playerArray, playerArray[currentPlayer],
                            currentCellCoord, Game.directions.get("RIGHT"))) {
                        //if we can place a Hwall, we display a ghost Hwall, waiting for click release
                        ImageView wallHighlight = (ImageView) gameContent.getChildren().get((boardSize*boardSize) + playerTotal);
                        wallHighlight.setImage(wallHImg);
                        wallHighlight.setY(currentCellCoord.getY() * Vspace - 18);
                        wallHighlight.setX(currentCellCoord.getX() * Hspace - 9);
                    } else if (Rules.canPlaceWall(playerArray, playerArray[currentPlayer],
                            currentCellCoord, Game.directions.get("UP"))){
                        //if we can place a Vwall, we display a ghost Vwall, waiting for click release
                        ImageView wallHighlight = (ImageView) gameContent.getChildren().get((boardSize*boardSize) + playerTotal);
                        wallHighlight.setImage(wallVImg);
                        wallHighlight.setY(currentCellCoord.getY() * Vspace - 54);
                        wallHighlight.setX(currentCellCoord.getX() * Hspace + 33);
                    } else {
                        wallHighlight.setImage(empty);
                    }
                } else {
                    wallHighlight.setImage(empty);
                }
            }
        });
        //AI handling

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!playerArray[currentPlayer].getType().equals("Human")) {
                    //current player is an AI, we call it's actionHandler
                    Action.getAction(playerArray, playerArray[currentPlayer]);
                    updatePawn();
                    updateWall();
                    if (playerArray[currentPlayer].hasWon()) {
                        //we stop the "loop" and show victory screen
                        this.stop();
                        Label whoWon = new Label("Congratulation ! Player " + (currentPlayer+1) + " has won !");
                        victoryPane.getChildren().add(whoWon);
                        victoryPane.getChildren().add(separator);
                        victoryPane.getChildren().add(backToMenu);
                        appStage.setScene(victoryScene);
                    } else {
                        //next player
                        currentPlayer = (currentPlayer + 1) % playerTotal;
                        game.setCurrentPlayer(currentPlayer);
                    }
                }
            }
        }.start();

        appStage.show();
    }


    /**
     * will reset all the glowing effect of all cell in the board
     */
    private void resetGlowing() {
        for (int i = 0; i < (int) Math.pow(game.getBoard().getSize(), 2); i++) {
            gameContent.getChildren().get(i).setEffect(new Glow(0));
        }
    }

    /**
     * will take each pawn sprite in the scene and update their position with their actual position
     */
    private void updatePawn() {
        for (int i=0; i<playerTotal; i++) {
            //We know that all the pawns are in this interval [boardSize², boardSize²+playerTotal[
            ImageView pawn = (ImageView) gameContent.getChildren().get( (boardSize*boardSize)+i );
            Coord playerCoord = playerArray[i].getDependency().getCoord();
            if (pawn.getImage() == null) { //if there is already an image set, it's not necessary to set it again
                pawn.setImage(playerSprite[i]);
            }
            pawn.setX(playerCoord.getX() * Hspace);
            pawn.setY(playerCoord.getY() * Vspace);
        }
    }

    /**
     * update all walls
     */
    private void updateWall() {
        ArrayList<Coord[]> wallList = board.getWallList();

        for (Coord[] wallCoord : wallList) {
            ImageView wall = new ImageView();
            if (wallCoord[0].getY() == wallCoord[1].getY()) {
                //horizontal wall
                wall.setImage(wallHImg);
                wall.setX(wallCoord[0].getX() * Hspace - 9);
                wall.setY(wallCoord[0].getY() * Vspace - 18);
            } else {
                //vertical wall
                wall.setImage(wallVImg);
                wall.setX(wallCoord[0].getX() * Hspace + 33);
                wall.setY(wallCoord[0].getY() * Vspace - 54);
            }
            gameContent.getChildren().add(wall);
        }

    }

    /**
     * Given a position in the canvas, will return the coordinates of the sprite the position is pointing to.
     * It's used to make a transition from the GUI to the game logic system.
     *
     * @param x
     * @param y
     * @return a coordinates instance for a Game object to use.
     */
    private Coord getCoordFromPos(double x, double y) {
        int game_y = (int) y / Vspace;
        int game_x = (int) x / Hspace;
        return new Coord(game_y, game_x);
    }


}
