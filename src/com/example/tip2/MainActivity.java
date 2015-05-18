package com.example.tip2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
 
public class MainActivity extends Activity {
 
    
    ImageView imView;
    TextView txtView;
  //  String imgUrl = "http://�����ּ�/appimg/";
    Bitmap bmImg;
    //back task;
    phpDown task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        task = new phpDown();    
        txtView = (TextView)findViewById(R.id.txtView);
        
        //imView = (ImageView) findViewById(R.id.imageView1);
        
        task.execute("http://www.feering.zc.bz/test2.php");
        
        
    }
 
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
            txtView.setText(str);
        }
        
}
}

 
 
