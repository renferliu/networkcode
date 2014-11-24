package com.example.clientsocketone;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import android.net.*;

public class MainActivity extends Activity implements OnClickListener{
	private Button shutdown = null;
	private Button restart = null;
	private Button logoff = null;
	public static Socket clientSocket = null;
	public static DataInputStream dataInputStream = null;
	public static DataOutputStream dataOutputStream = null;
	private String txt = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		shutdown = (Button) findViewById(R.id.shutdown);
		shutdown.setOnClickListener(this);
		restart = (Button) findViewById(R.id.restart);
		restart.setOnClickListener(this);
		logoff = (Button) findViewById(R.id.logoff);
		logoff.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
				new Thread(new MyThread()).start();
		//	clientSocket = new Socket("10.11.3.214",8888);
			dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
//			dataInputStream = new DataInputStream(clientSocket.getInputStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (v.getId()) {
		case R.id.shutdown:
			txt = "shutdown";
			Toast.makeText(MainActivity.this, txt, 1).show();
			break;
		case R.id.restart:
			txt = "restart";
			Toast.makeText(MainActivity.this, txt, 1).show();
			break;
		case R.id.logoff:
			txt = "logoff";
			Toast.makeText(MainActivity.this, txt, 1).show();
			break;
		default:
			break;
		}
		try {
			if((dataOutputStream != null) && (!txt.equals(""))){
				dataOutputStream.writeUTF(txt);
				dataOutputStream.close();
				clientSocket.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public class MyThread implements Runnable{

		@Override
		public void run() {
			try {
				clientSocket = new Socket("10.11.3.214",8888);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
