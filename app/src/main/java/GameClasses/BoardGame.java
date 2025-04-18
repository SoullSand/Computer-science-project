package GameClasses;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import Settings.Difficulties;
import com.example.computerscienceproject.UpdateFirebaseService;

import Tiles.BombTile;
import Tiles.EmptyTile;
import Tiles.NumberTile;
import Tiles.Tile;

public class BoardGame extends View {
    private Context context;
    private Tile[][] tiles;
    private Map map;
    private int xMapSize, yMapSize, numberOfBombs, numberOfFlags = 0;
    private int firstShownTileXIndex = 0, firstShownTileYIndex = 0;
    private int lastShownTileXIndex = 0, lastShownTileYIndex = 0;
    private int clickDownX, clickDownY, clickUpX, clickUpY;
    private Difficulties difficulty;
    private GameActivity gameActivity;
    private GameButtons selectedButton;
    private final int SHOWN_X_TILES = 8, SHOWN_Y_TILES = 11;

    public BoardGame(Context context, Difficulties difficulty) {
        super(context);
        this.context = context;

        this.difficulty = difficulty;

         selectedButton = GameButtons.CLICK;

        SetMapPropertiesByDifficulty();
        map = new Map(yMapSize, xMapSize, context);
        map.generateMap(numberOfBombs);
        tiles = map.getMap();

        gameActivity = (GameActivity) context;
        updateFlagCount();

    }

    private void SetMapPropertiesByDifficulty() {
        if (difficulty == Difficulties.EASY) {
            xMapSize = 16;
            yMapSize = 16;
            numberOfBombs = 20;
        }
        else if(difficulty == Difficulties.MEDIUM) {
            xMapSize = 16;
            yMapSize = 16;
            numberOfBombs = 40;
        }
        else if(difficulty == Difficulties.HARD) {
            xMapSize = 16;
            yMapSize = 16;
            numberOfBombs = 99;
        }
    }

    public void updateFlagCount() {
        gameActivity.updateFlagCountView(numberOfBombs - numberOfFlags);
    }

    // draws the canvas
    private void drawBoard(Canvas canvas) {
        // if the first tile index shown is less than the total tiles shown
        for (int i = firstShownTileYIndex; i < SHOWN_Y_TILES + firstShownTileYIndex; i++) {
            for (int j = firstShownTileXIndex; j < SHOWN_X_TILES + firstShownTileXIndex; j++) {
                tiles[j][i].draw(canvas, firstShownTileXIndex, firstShownTileYIndex);
            }
        }
    }
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
    }
    // sets the action button selected
    public void updateSelectedButton(GameButtons selectedButton) {
        this.selectedButton = selectedButton;
    }

    // restarts the game
    public void restart() {
        map.generateMap(numberOfBombs);
        tiles = map.getMap();
        gameActivity.resetTimer();
        invalidate();
    }
    // returns if player won
    public boolean isWin() {
        int sum = 0;
        for (int i = 0; i < yMapSize; i++) {
            for (int j = 0; j < xMapSize; j++) {
                if (!tiles[i][j].getIsHidden() && !(tiles[i][j] instanceof BombTile)) {
                    sum++;
                }
            }
        }
        if ((yMapSize * xMapSize) - numberOfBombs == sum) {
            return true;
        }
        return false;
    }
    // checks if the clicked tile is a bomb
    private boolean isLoss(Tile tile) {
        return (tile instanceof BombTile && !(tile.getIsFlagged()));
    }

    // game win or lose dialog
    private void createDialog(String winLose) {
        CustomDialog customDialog = new CustomDialog(context, winLose);
        customDialog.show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            clickDownX = (int) event.getX();
            clickDownY = (int) event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {

        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            clickUpX = (int) event.getX();
            clickUpY = (int) event.getY();

            lastShownTileXIndex = firstShownTileXIndex + clickUpX / Tile.TILE_SIZE;
            lastShownTileYIndex = firstShownTileYIndex + clickUpY / Tile.TILE_SIZE;
            validateMapPositioning();
            doButtonAction();
            invalidate();
        }
        return true;
    }

    // does the action according to the selected button
    private void doButtonAction() {
        if (selectedButton == GameButtons.MOVE) {
            moveBoard();
        }
        if (selectedButton == GameButtons.CLICK) {
            clickTile(tiles[lastShownTileXIndex][lastShownTileYIndex]);

            if (isLoss(tiles[lastShownTileXIndex][lastShownTileYIndex])) {
                gameActivity.timer.stopTimer();
                createDialog("Lost :(");
            }
            if (isWin()) {
                gameActivity.timer.stopTimer();
                Intent intent = new Intent(context, UpdateFirebaseService.class);
                intent.putExtra("action", "SetRecord");
                intent.putExtra("difficulty", difficulty.toString());
                intent.putExtra("time", gameActivity.getTime());
                context.startService(intent);

                createDialog("Won!!!");
            }
        }
        if (selectedButton == GameButtons.FLAG) {
            tiles[lastShownTileXIndex][lastShownTileYIndex].flag();
            numberOfFlags++;
            updateFlagCount();
        }
    }

    // clicks a tile
    private void clickTile(Tile tile) {
        // recursively revealing all tiles around the empty tiles
        if (tile instanceof EmptyTile) {
            clickEmptyTile(tile);
        } else if (tile instanceof NumberTile) {
            clickNumberTile(tile);
        } else {
            tiles[lastShownTileXIndex][lastShownTileYIndex].click();
            if (isLoss(tiles[lastShownTileXIndex][lastShownTileYIndex])) {
                gameActivity.timer.stopTimer();
                createDialog("Lost :(");
            }
        }
    }
    // clicks an empty tile and all tiles around it
    private void clickEmptyTile(Tile tile) {
        if (tile instanceof EmptyTile) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (tile.getX() - i >= 0 && tile.getX() - i < yMapSize
                            && tile.getY() - j >= 0 && tile.getY() - j < xMapSize) {
                        // the current tile the loops are on
                        Tile newTile = tiles[tile.getX() - i][tile.getY() - j];
                        if (newTile.getIsHidden()) {
                            newTile.click();
                            clickEmptyTile(newTile);
                        }
                    }
                }
            }
        }
    }
    // clicks a number tile
    private void clickNumberTile(Tile tile) {
        tile.click();
        int tileNumber = ((NumberTile) tile).getNumber();
        int flaggedBombCount = getSurroundingFlaggedTiles(tile);

        /* enables the option to reveals the non-bomb tiles around the tile
         if all the number on the tile equals to the number of the bombs flagged*/
        if (flaggedBombCount == tileNumber) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    // makes sure checked tile isn't out of bounds
                    if (tile.getX() - i >= 0 && tile.getX() - i < yMapSize
                            && tile.getY() - j >= 0 && tile.getY() - j < xMapSize) {
                        // the current tile the loops are on
                        Tile newTile = tiles[tile.getX() - i][tile.getY() - j];
                        // if tile is an empty tile and is hidden reveal it and redo the process
                        if (newTile.getIsHidden()
                                && newTile instanceof EmptyTile) {
                            clickEmptyTile(newTile);
                        }
                        else if (isLoss(newTile))
                        {
                            gameActivity.timer.stopTimer();
                            createDialog("Lost :(");
                        }
                        // if tile isn't an empty tile reveal it normally
                        else if (newTile.getIsHidden()) {
                            newTile.click();
                        }
                    }
                }
            }
        }
    }

    // Gets the amount of flagged bombs around the tile
    private int getSurroundingFlaggedTiles(Tile tile) {
        int flaggedTileCount = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                // if the checked tiles aren't out of bounds
                if (tile.getX() - i >= 0 && tile.getX() - i < yMapSize
                        && tile.getY() - j >= 0 && tile.getY() - j < xMapSize) {
                    // adds the number of bombs
                    if (tiles[tile.getX() - i][tile.getY() - j].getIsFlagged()) {
                        flaggedTileCount++;
                    }
                }
            }
        }
        return flaggedTileCount;
    }

    // moves the shown board around
    private void moveBoard() {
        //Move right and left
        firstShownTileXIndex += (clickDownX - clickUpX) / Tile.TILE_SIZE;
        //Move up and down
        firstShownTileYIndex += (clickDownY - clickUpY) / Tile.TILE_SIZE;
        validateMapPositioning();
    }

    // makes sure shown map indexes don't go out of bounds
    private void validateMapPositioning() {
        // makes sure first shown tile doesn't go out of bounds
        if (firstShownTileXIndex < 0) {
            firstShownTileXIndex = 0;
        }
        if (firstShownTileYIndex < 0) {
            firstShownTileYIndex = 0;
        }
        //makes sure last shown tile doesn't go out of bounds because of the first shown tile
        if (firstShownTileXIndex > xMapSize - 8) {
            firstShownTileXIndex = xMapSize - 8;
        }
        if (firstShownTileYIndex > yMapSize - 11) {
            firstShownTileYIndex = yMapSize - 11;
        }
        // makes sure last shown tile doesn't go out of bounds
        if (lastShownTileXIndex >= xMapSize) {
            lastShownTileXIndex = xMapSize - 1;
        }
        if (lastShownTileYIndex >= yMapSize) {
            lastShownTileYIndex = yMapSize - 1;
        }
    }
}
