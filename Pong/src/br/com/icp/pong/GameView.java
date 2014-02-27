package br.com.icp.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import br.com.icp.PiecesManager;
import br.com.icp.math.Vector2D;
import br.com.icp.pieces.Ball;
import br.com.icp.pieces.Pad;
import br.com.icp.pieces.Wall;

public class GameView extends View implements Runnable {
	
	// Galaxy S3
	//private int width = 720, height = 1280;
	
	// Emulator
	private int width = 320, height = 480;

	private Paint background;
	private Handler handler;
	private PiecesManager manager;
	
	private Pad northPad;
	private Pad southPad;
	
	public GameView(Context context) {
		super(context);
		init();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
        background = new Paint();
        background.setColor(Color.BLACK);

        manager = new PiecesManager();
        
        manager.addPiece(new Ball(new Vector2D(20, 20), new Vector2D(1,1), 2, manager));
        
        manager.addPiece(new Wall(new Rect(0, 0, 10, height), new Vector2D(1,0)));//Left
        manager.addPiece(new Wall(new Rect(width - 10, 0, width, height), new Vector2D(-1,0)));//Right
        manager.addPiece(new Wall(new Rect(0, 0, width, 10), new Vector2D(0,-1)));// Top
        manager.addPiece(new Wall(new Rect(0, height - 10, width, height),new Vector2D(0,1)));//Bottom
        
        northPad = new Pad(new Vector2D(0, -1), 80); //80 é a distancia default do eixo, no caso desenhar no 80 de y
        manager.addPiece(northPad);
        southPad = new Pad(new Vector2D(0, 1), height-80);//944 do y
        manager.addPiece(southPad);
	}

	public void onDraw(Canvas canvas) {
	    canvas.save();
	    canvas.drawRect(0, 0, getWidth(), getHeight(), background);
	    manager.draw(canvas);
	    canvas.restore();
	}

	@Override
	public void run() {
        while (true) {
            try {
                manager.processAI();
 
                Message msg = new Message();
                msg.what = 10001;//Mesmo id que o Handler espera para atualizar
                handler.sendMessage(msg);
 
                Thread.sleep(10);
            } catch (Exception e) {
            }
        }
    }
 
    public void setCallbackHandler(Handler guiRefresher) {
        this.handler = guiRefresher;
    }
    
    public boolean onTouchEvent(MotionEvent evt) {
        southPad.notifyMotionEvent(evt.getX(), evt.getY());
        northPad.notifyMotionEvent(evt.getX(), evt.getY());
        return true;
    }
}

