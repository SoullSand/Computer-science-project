package Tiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.computerscienceproject.R;

public abstract class Tile {
    public static final int TILE_SIZE = 130;
    protected int x;
    protected int y;
    protected boolean isHidden;
    protected boolean isFlagged;

    protected Context context;

    public Tile(int x, int y, Context context) {
        this.x = x;
        this.y = y;
        isFlagged = false;
        isHidden = true;

        this.context = context;
    }

    public void click() {
        if (!isFlagged) {
            isHidden = false;
        }
    }

    public void flag() {
        isFlagged = !isFlagged;
    }


    public void draw(Canvas canvas, int startingX, int startingY) {
    }

    public boolean getIsFlagged() {
        return isFlagged;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getIsHidden() {
        return isHidden;
    }
}
