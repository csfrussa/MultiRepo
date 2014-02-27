package br.com.icp.pieces;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import br.com.icp.math.Vector2D;

public class Pad extends BlockableEntity {
    private Paint wallPaint;
 
    private int defaultDistanceFromAxis;
 
    public Pad(Vector2D normal, int defaultDistance) {
        super(new Rect(), normal);
        wallPaint = new Paint();
        wallPaint.setColor(Color.GRAY);
 
        this.defaultDistanceFromAxis = defaultDistance;
    }
 
    public void notifyMotionEvent(float x, float y) {
                setBounds(new Rect((int) x - 150,
                        defaultDistanceFromAxis, (int) x + 150,
                        defaultDistanceFromAxis + 10));
    }
 
    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.drawRect(getBounds(), wallPaint);
        canvas.restore();
    }
 
    @Override
    public void processAI() {
        // Do nothing, position is handled by events
    }
}
