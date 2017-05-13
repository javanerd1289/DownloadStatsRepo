package extremecode.downloadstatsmodel;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    private ListView listView;
    private TextView mytext;
    private ArrayList<MyListModel> models = new ArrayList<>();
    private MyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        mytext = (TextView) findViewById(R.id.mytxt);

        getDownloadData();
    }

    private void getDownloadData() {
        try {
            String POST_DATA_URL = "https://juni1289.000webhostapp.com/index.php";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, POST_DATA_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            listView.setVisibility(View.VISIBLE);
                            mytext.setVisibility(View.GONE);
                            Log.e("xxx", "response" + response.toString());
                            //get object
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray result = jsonObject.getJSONArray("data");

                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject myData = result.getJSONObject(i);
                                    String date = myData.getString("myDate");
                                    String appname = myData.getString("myAppName");
                                    String devicename = myData.getString("myDeviceName");
                                    String id = Integer.toString(i+1);
                                    String nowTime = myData.getString("myTime");
                                    models.add(new MyListModel(id, appname, date, devicename,nowTime));
                                }

                                adapter=new MyListAdapter(getApplicationContext(),models);
                                listView.setAdapter(adapter);

                                if(models.size()==0){
                                    mytext.setText("No Data");
                                    listView.setVisibility(View.GONE);
                                    mytext.setVisibility(View.VISIBLE);
                                }
                                else{
                                    listView.setVisibility(View.VISIBLE);
                                    mytext.setVisibility(View.GONE);
                                }
                            } catch (Exception exp) {
                                listView.setVisibility(View.GONE);
                                mytext.setVisibility(View.VISIBLE);
                                mytext.setText(exp.toString());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("xxx", "Error" + error.toString());

                            if (error instanceof NoConnectionError) {
                                listView.setVisibility(View.GONE);
                                mytext.setVisibility(View.VISIBLE);
                                mytext.setText("No Internet!");
                            } else {
                                listView.setVisibility(View.GONE);
                                mytext.setVisibility(View.VISIBLE);
                                mytext.setText(error.toString());
                            }

                        }
                    }) {


            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    (int) TimeUnit.SECONDS.toMillis(30),
                    1,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } catch (Exception exp) {
            Log.e("XCEPTION", "post" + exp.toString());
            listView.setVisibility(View.GONE);
            mytext.setVisibility(View.VISIBLE);
            mytext.setText(exp.toString());
        }
    }
}
