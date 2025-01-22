package Tiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import com.example.computerscienceproject.R;

public class NumberTile extends Tile {

    private int number;

    public NumberTile(int x, int y, int color, int number, Context context) {
        super(x, y, color, context);
        this.number = number;
        this.context = context;
    }

    public void addOneToNumber() {
        number++;
        paint.setColor(Color.GREEN);
    }

    public int getNumber() {
        return number;
    }

    @Override
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
            Bitmap bmp = getCorrectTilePicture();
            canvas.drawBitmap(bmp, x * TILE_SIZE - startingX * TILE_SIZE,
                    y * TILE_SIZE - startingY * TILE_SIZE, null);
        }
    }

    private Bitmap getCorrectTilePicture() {

        switch (number) {
            case 1:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.onetile);
            case 2:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.twotile);
            case 3:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.threetile);
            case 4:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.fourtile);
            case 5:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.fivetile);
            case 6:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.sixtile);
            case 7:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.seventile);
            case 8:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.eighttile);
            default:
                throw new IllegalStateException("Unexpected value: " + number);
        }
    }
}
