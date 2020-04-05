package com.example.kubab;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class InformationActivity extends AppCompatActivity {

    String myJSON;

    private static final String TAG_RESULTS="result";
    //private static final String TAG_ID="id";
    private static final String TAG_RESTAURANT="restaurant";
    private static final String TAG_TERMTIME_SALETIME="termtime_saletime";
    private static final String TAG_VACATION_SALETIME="vacation_saletime";
    private static final String TAG_PRICE="price";
    private static final String TAG_ADDRESS="address";
    private static final String TAG_NOTICE="notice";

    JSONArray operations = null;

    ArrayList<HashMap<String, String>> operationList;

    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        list=(ListView)findViewById(R.id.listView);
        operationList = new ArrayList<HashMap<String, String>>();
        getData("http://13.125.170.131/timetable.php");
    }

    protected void showList(){
        try{
            JSONObject jsonObj = new JSONObject(myJSON);
            operations = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0; i<operations.length(); i++){
                JSONObject c = operations.getJSONObject(i);
                //String id = c.getString(TAG_ID);
                String restaurant = c.getString(TAG_RESTAURANT);
                String termtime_saletime = c.getString(TAG_TERMTIME_SALETIME);
                String vacation_saletime = c.getString(TAG_VACATION_SALETIME);
                String price = c.getString(TAG_PRICE);
                String address = c.getString(TAG_ADDRESS);
                String notice = c.getString(TAG_NOTICE);


                HashMap<String, String> Operations = new HashMap<String, String>();
                //Operations.put(TAG_ID, id);
                Operations.put(TAG_RESTAURANT, restaurant);
                Operations.put(TAG_TERMTIME_SALETIME, termtime_saletime);
                Operations.put(TAG_VACATION_SALETIME, vacation_saletime);
                Operations.put(TAG_PRICE, price);
                Operations.put(TAG_ADDRESS, address);
                Operations.put(TAG_NOTICE, notice);

                operationList.add(Operations);
            }

            ListAdapter adapter = new SimpleAdapter(
                    InformationActivity.this, operationList, R.layout.list_item_information, new String[]{TAG_RESTAURANT, TAG_TERMTIME_SALETIME, TAG_VACATION_SALETIME, TAG_PRICE, TAG_ADDRESS, TAG_NOTICE}, new int[]{R.id.restaurant, R.id.termtime_saletime, R.id.vacation_saletime, R.id.price, R.id.address, R.id.notice}
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
                        sb.append(json+'\n');
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
