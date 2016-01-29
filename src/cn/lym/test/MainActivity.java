package cn.lym.test;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{
	
	private EditText ipEt;
	private EditText serverNameEt;
	private Button confirmBt;
	private TextView contentTv;
	
	private String http = "http://";
	private String ip;
	private String serverName;
	private String other = ":8080/RemoteBuilderService/";
	private String url ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ipEt = (EditText) findViewById(R.id.ip_address);
		serverNameEt = (EditText) findViewById(R.id.server_name);
		confirmBt = (Button) findViewById(R.id.confirm);
		contentTv = (TextView) findViewById(R.id.content);
		confirmBt.setOnClickListener(this);
	}
	
	private void getUrlContent(){
		ip = ipEt.getText().toString();
		serverName = serverNameEt.getText().toString();
		if(ip == null | serverName == null | "".equals(ip) | "".equals(serverName)){
			Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
		}else{
			url = http+ip+other+serverName;
			RequestQueue requestQueue = Volley.newRequestQueue(this);
			MyRequest myRequest = new MyRequest(Method.POST, url, new MyRequestListener(), new MyRequestErrorListener());
			requestQueue.add(myRequest);
		}
	}
	
	private class MyRequest extends StringRequest{

		public MyRequest(int method, String url, Listener<String> listener, ErrorListener errorListener) {
			super(method, url, listener, errorListener);
		}
		
	}
	
	private class MyRequestListener implements Listener<String>{

		@Override
		public void onResponse(String response) {
			contentTv.setText(response);
		}
	}
	
	private class MyRequestErrorListener implements ErrorListener{

		@Override
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(MainActivity.this, "网络错误", Toast.LENGTH_LONG).show();
			contentTv.setText(error.toString());
		}
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.confirm){
			getUrlContent();
		}
	}
}
