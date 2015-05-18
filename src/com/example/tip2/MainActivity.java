package com.example.tip2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.TabActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;


public class MainActivity extends TabActivity 


 {

	 // ���������� �����Ѵ� 
    TabHost mTabHost = null; 
    String myId, myPWord, myTitle, mySubject, myResult; 
    private TextView mTextView = null;
    phpDown task;
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
    	
    	 if (android.os.Build.VERSION.SDK_INT > 9) {

	         StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()

	                 .detectDiskReads()

	                 .detectDiskWrites()

	                 .detectNetwork()

	                 .penaltyLog()

	                 .build());

	     }
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_main); 
        
        mTabHost = getTabHost();          // Tab ����� 
        mTabHost.addTab(mTabHost.newTabSpec("tab_1").setIndicator("������ ����").setContent(R.id.page01)); 
        mTabHost.addTab(mTabHost.newTabSpec("tab_2").setIndicator("�������� ����").setContent(R.id.page02)); 
        findViewById(R.id.button_submit).setOnClickListener(buttonClick);  
        findViewById(R.id.button_submit2).setOnClickListener(buttonClick2);  
    } 
    
    //------------------------------ 
    //    button Click 
    //------------------------------ 
    Button.OnClickListener buttonClick = new Button.OnClickListener() { 
        public void onClick(View v) { 
           // ����ڰ� �Է��� ������ ���������� �����Ѵ� 
           myId = ((EditText)(findViewById(R.id.edit_Id))).getText().toString();  
           myPWord = ((EditText)(findViewById(R.id.edit_pword))).getText().toString();  
           myTitle = ((EditText)(findViewById(R.id.edit_title))).getText().toString();  
           mySubject = ((EditText)(findViewById(R.id.edit_subject))).getText().toString();  
         
          new SendPost().execute();    // ������ �ڷ� �ְ�ޱ� 
       } 
    };  
    
    Button.OnClickListener buttonClick2 = new Button.OnClickListener() { 
        public void onClick(View v) { 
           // ����ڰ� �Է��� ������ ���������� �����Ѵ� 
        	   
        	mTextView = (TextView) findViewById(R.id.text_result);
        	task = new phpDown(); 
        	task.execute("http://www.feering.zc.bz/test2.php");
        	
       } 
    };  
    

    private class phpDown extends AsyncTask<String, Integer,String>{
        
        @Override
            protected String doInBackground(String... urls) {
                StringBuilder jsonHtml = new StringBuilder();
                try{
                          // ���� url ����
                          URL url = new URL(urls[0]);
                          // Ŀ�ؼ� ��ü ����
                          HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                          // ����Ǿ�����.
                          if(conn != null){
                             conn.setConnectTimeout(10000);
                             conn.setUseCaches(false);
                             // ����Ǿ��� �ڵ尡 ���ϵǸ�.
                             if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                                for(;;){
                                    // ���� �������� �ؽ�Ʈ�� ���δ����� �о� ����.  
                                    String line = br.readLine();
                                    if(line == null) break;
                                    // ����� �ؽ�Ʈ ������ jsonHtml�� �ٿ�����
                                    jsonHtml.append(line + "\n");
                                 }
                              br.close();
                           }
                            conn.disconnect();
                         }
                       } catch(Exception ex){
                          ex.printStackTrace();
                       }
                       return jsonHtml.toString();
                
            }
            
            protected void onPostExecute(String str){
            	mTextView.setText(str);
            }
            
    }
    

    
    
    


	private class SendPost extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... params) {
			
		String content = executeClient();
			return content;
		}

		 protected void onPostExecute(String result) {
			

		 }
		
		// ���� �����ϴ� �κ�
		public String executeClient() {
			  try {

			        HttpClient client = new DefaultHttpClient();

			        String postURL = "http://www.feering.zc.bz/test4.php";

			        HttpPost post = new HttpPost(postURL);

			        List<NameValuePair> params = new ArrayList<NameValuePair>();
			        
			        params.add(new BasicNameValuePair("id",myId));

			        params.add(new BasicNameValuePair("pword", myPWord));
			        
			        params.add(new BasicNameValuePair("title", myTitle));
			        
			        params.add(new BasicNameValuePair("subject", mySubject));

			        //�߰��ϰ� ������ add�� �Ἥ �߰��ϸ�ǰ� 

			             //BasicNameValuePair�� key�� value�� �Ǿ� �־ �ٷ� ��ó�� ������ָ�ȴ�.
			        UrlEncodedFormEntity ent;

			        ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);

			            post.setEntity(ent);

			            HttpResponse responsePOST = client.execute(post);

			            HttpEntity resEntity = responsePOST.getEntity();

			                         //POST�� ���� ������ ��û�Ͽ� Ȯ���۾�

			            if(resEntity !=null){

			                Log.i("RESPONSE",EntityUtils.toString(resEntity));

			                                     //���� ������ �α׸� �� Ȯ�����ش�.

			            }

			            

			        } catch (UnsupportedEncodingException e) {

			            // TODO Auto-generated catch block

			            e.printStackTrace();

			        } catch (IOException e) {

			            // TODO Auto-generated catch block

			            e.printStackTrace();

			     }

			        

			        return null;

			      }

			}
    
 }
 

