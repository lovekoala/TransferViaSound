package com.lovekoala.transferviasound;

import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.lovekoala.transferviasound.fm.Receiver;
import com.lovekoala.transferviasound.fm.Sender;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void onSend(View view) {
    	try {
    		String text = ((TextView)MainActivity.this.findViewById(R.id.text)).getText().toString();
			Sender.getSender().send(text.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void onRecv(final View view) {
    	view.setEnabled(false);
    	new Thread() {
    		@Override
    		public void run() {
    			final byte [] data = new Receiver().receive();
    			view.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							((TextView)MainActivity.this.findViewById(R.id.text)).setText(new String(data,0,data.length,"utf-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						view.setEnabled(true);
					}
    				
    			});
    		}
    	}.start();
    	
    }
    
}
