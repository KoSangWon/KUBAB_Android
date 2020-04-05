package com.example.kubab;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MillenniumhallActivity extends AppCompatActivity {

    String myJSON;

    private static final String TAG_RESULTS="result";
    //private static final String TAG_ID="id";
    //private static final String TAG_DATE="date";
    private static final String TAG_CORNER="corner";
    private static final String TAG_DIVISION="division";
    private static final String TAG_SALETIME="saletime";
    private static final String TAG_MENU="menu";

    JSONArray menus = null;

    ArrayList<HashMap<String, String>> menuList;

    ListView list;



    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdfNow = new SimpleDateFormat("MM월 dd일 E요일");
    String formatDate = sdfNow.format(date);

    TextView dateNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_millenniumhall);
        list=(ListView)findViewById(R.id.listView);
        menuList = new ArrayList<HashMap<String, String>>();
        getData("http://13.125.170.131/milleniumhall.php");

        dateNow = (TextView)findViewById(R.id.dateNow);
        dateNow.setText(formatDate);
    }

    protected void showList(){
        try{
            JSONObject jsonObj = new JSONObject(myJSON);
            menus = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0; i<menus.length(); i++){
                JSONObject c = menus.getJSONObject(i);
                //String id = c.getString(TAG_ID);
                //String date = c.getString(TAG_DATE);
                String corner = c.getString(TAG_CORNER);
                String division = c.getString(TAG_DIVISION);
                String saletime = c.getString(TAG_SALETIME);
                String menu = c.getString(TAG_MENU);

                HashMap<String, String> Menus = new HashMap<String, String>();
                //Menus.put(TAG_ID, id);
                //Menus.put(TAG_DATE, date);
                Menus.put(TAG_CORNER, corner);
                Menus.put(TAG_DIVISION, division);
                Menus.put(TAG_SALETIME, saletime);
                Menus.put(TAG_MENU, menu);

                menuList.add(Menus);
            }

            ListAdapter adapter = new SimpleAdapter(
                    MillenniumhallActivity.this, menuList, R.layout.list_item, new String[]{TAG_CORNER, TAG_DIVISION, TAG_SALETIME, TAG_MENU}, new int[]{R.id.corner, R.id.division, R.id.saletime, R.id.menu}
            );

            list.setAdapter(adapter);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    public void getData(String url){
        class GetDataJson extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];

                BufferedReader bufferedReader = null;
                try{
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!=null){
                        sb.append(json);
                    }
                    return sb.toString().trim();
                }
                catch(Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJson g = new GetDataJson();
        g.execute(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



}
