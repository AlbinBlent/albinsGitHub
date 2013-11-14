package com.example.ping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.ping.MESSAGE";

	EditText edit;
	TextView text;
	String host;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	public void sendPing(View view) {
		// Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.ping_adress);
		host = editText.getText().toString();
		// intent.putExtra(EXTRA_MESSAGE, message);
		// startActivity(intent);
		
//		new LongOperation().execute(host);
		new RequestHttp().execute(host);
		
		
		
	}

	private class LongOperation extends AsyncTask<String, Void, Long> {

		protected void onPostExecute(Long result) {
			String message = String.valueOf(result);

			System.out.println("result " + message);
			
			TextView text = (TextView) findViewById(R.id.textView1);
			text.append("IP: " + host + " ms: " + message + "\n ");
			
			
		}

		@Override
		protected Long doInBackground(String... host) {
			CheckPing pingcheck = new CheckPing();

			long timeToPing = pingcheck.checkPing(host[0], 30000);
			System.out.println("doInBackground " + timeToPing);
			return timeToPing;
		}
	}
	class RequestHttp extends AsyncTask<String, String, Long>{

	    @Override
	    protected Long doInBackground(String... uri) {
	        HttpClient httpclient = new DefaultHttpClient();
	        
	        HttpResponse response;
	        String responseString = null;
	        long pingTime = 0;
	        final long startTime = System.currentTimeMillis();
			
	        
	        try {
	            response = httpclient.execute(new HttpGet(uri[0]));
	            StatusLine statusLine = response.getStatusLine();
	            
	            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	                ByteArrayOutputStream out = new ByteArrayOutputStream();
	                response.getEntity().writeTo(out);
	                out.close();
	                responseString = out.toString();
	                
	                final long endTime = System.currentTimeMillis();
	    	        pingTime = endTime - startTime;
	    			System.out.println("http: " + pingTime);
	    			
	            } else{
	                //Closes the connection.
	                response.getEntity().getContent().close();
	                throw new IOException(statusLine.getReasonPhrase());
	            }
	        } catch (ClientProtocolException e) {
	            //TODO Handle problems..
	        } catch (IOException e) {
	            //TODO Handle problems..
	        }
	        
	        return pingTime;
	    }

	    @Override
	    protected void onPostExecute(Long result) {
	        super.onPostExecute(result);
	        String message = String.valueOf(result);

			System.out.println("result " + message);
			
			TextView text = (TextView) findViewById(R.id.textView1);
			text.append("IP: " + host + " ms: " + message + "\n ");
			
	        //Do anything with response..
	    }
	}
}