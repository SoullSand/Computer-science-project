package Tiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.computerscienceproject.R;

public class EmptyTile extends Tile {
    public EmptyTile(int x, int y, Context context) {
        super(x, y, context);
    }

    public void draw(Canvas canvas, int startingX, int startingY) {
        if (isHidden && isFlagged) {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.flaggedtile);
            canvas.drawBitmap(bmp, x * TILE_SIZE - startingX * TILE_SIZE,
                    y * TILE_SIZE - startingY * TILE_SIZE, null);
        } else if (isHidden) {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.hiddentile);
            canvas.drawBitmap(bmp, x * TILE_SIZE - startingX * TILE_SIZE,
                    y * TILE_SIZE - startingY * TILE_SIZE, null);
        }
        else {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.emptytile);
            canvas.drawBitmap(bmp, x * TILE_SIZE - startingX * TILE_SIZE,
                    y * TILE_SIZE - startingY * TILE_SIZE, null);
        }
    }
}
