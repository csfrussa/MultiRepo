package br.com.icp.pong;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import br.com.icp.R;

public class AndPongActivity extends Activity {
	
	private GameView gameView;
	private Handler guiRefresher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_and_pong);

		gameView = (GameView) findViewById(R.id.gameView);
		 
		guiRefresher = new Handler(){
		    public void handleMessage(Message msg){
		        if(msg.what == 10001)//Numero arbitrario
		            gameView.invalidate();
		        super.handleMessage(msg);
		    }
		};
		gameView.setCallbackHandler(guiRefresher);
				
		Thread t = new Thread(gameView);
		t.setDaemon(true);
		t.start();
		//t.run();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.and_pong, menu);
		return true;
	}

}
