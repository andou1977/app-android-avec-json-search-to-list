package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.net.ssl.SSLEngineResult;

public class MainActivity extends AppCompatActivity {

    public EditText search;
    public Button btnname, btnlongilat, btnlist, btnlistview;
    public TextView txtresult;
    public ListView lister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.search);
        btnname = findViewById(R.id.btnname);
        btnlongilat = findViewById(R.id.btnlatilongi);

        btnlist = findViewById(R.id.btnlist);
        txtresult = findViewById(R.id.txtresult);
        lister = findViewById(R.id.lister);
        btnlistview = findViewById(R.id.btnlistview);


        btnname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchbyname();
                //   isNetworkAvailable();

            }
        });

        btnlongilat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logiandlati();

            }
        });

        btnlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lister();
            }
        });

        btnlistview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listviewer();
            }
        });

        listviewbyname();
    }


//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null;
//    }

//    public boolean isInternetAvailable() {
//        try {
//            InetAddress address = InetAddress.getByName("www.google.com");
//            return !address.equals("");
//        } catch (UnknownHostException e) {
//            txtresult.setText(e.getMessage());
//        }
//        return false;
//    }

//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//
//        txtresult.setText(activeNetworkInfo != null && activeNetworkInfo.isConnected());
//    }


    public void searchbyname() {


        String searchname = search.getText().toString();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.metaweather.com/api/location/search/?query=" + searchname;

// Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String searchname = search.getText().toString();
                String byname = "https://www.metaweather.com/api/location/search/?query" + searchname;
                JSONObject andouinfo = null;
                try {

                    andouinfo = response.getJSONObject(0);
                    if (andouinfo.has("title"))
                        byname = andouinfo.getString("woeid");
                    txtresult.setText("The Woeid is:" + byname.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    txtresult.setText("doesn't exist");
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (searchname.equals(""))
                    txtresult.setText("Search must not be empty");
                else {
                    //isNetworkAvailable();
                    // txtresult.setText("That didn't work!");
                }

            }
        });

// Add the request to the RequestQueue.
        queue.add(request);
    }


    public void logiandlati() {


        String searchname = search.getText().toString();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.metaweather.com/api/location/search/?lattlong=" + searchname;

// Request a string response from the provided URL.
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String searchname = search.getText().toString();
                String byname = "https://www.metaweather.com/api/location/search/?lattlong=" + searchname;
                JSONObject andouinfo = null;
                try {

                    andouinfo = response.getJSONObject(0);
                    if (andouinfo.has("latt_long"))
                        byname = andouinfo.getString("title");
                    txtresult.setText("The State  is: \t" + byname);
                } catch (JSONException e) {
                    e.printStackTrace();

                    txtresult.setText("doesn't exist");
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // isInternetAvailable();

                if (searchname.equals(""))
                    txtresult.setText("Search must not be empty");
                else {

                    txtresult.setText("That didn't work!");
                }

            }
        });

// Add the request to the RequestQueue.
        queue.add(request);
    }


    public void lister() {
        final ArrayList<String> tubeLines = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tubeLines);
        lister.setAdapter(arrayAdapter);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.metaweather.com/api/location/search/?query=san";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.optJSONObject(i);
                                String line = object.optString("title");
//                                String line1 = object.optString("latt_long");
                                if (line != null) {
                                    tubeLines.add(line);
//                                    tubeLines.add(line1);
                                }
                            }
                            // Once we added the string to the array, we notify the arrayAdapter
                            arrayAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);

    }


    public void listviewer() {

        String searchname = search.getText().toString();
        final ArrayList<String> tubeLines = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tubeLines);
        lister.setAdapter(arrayAdapter);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.metaweather.com/api/location/search/?query=" + searchname;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.optJSONObject(i);
                                String line = object.optString("title");
                                String line1 = object.optString("latt_long");
                                String line2 = object.optString("woeid");
                                String line3 = object.optString("location_type");
                                if (line != null) {
                                    tubeLines.add("Title : \t" + line);
                                    tubeLines.add("Lattitude and Longitude : \t" + line1);
                                    tubeLines.add("The woeid : \t" + line2);
                                    tubeLines.add("Location: \t" + line3);
                                }
                            }
                            // Once we added the string to the array, we notify the arrayAdapter
                            arrayAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);


    }
//    public void listviewbyname(){
//        lister.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position,
//                                    long id) {
//
//
//
//
//
//                final ArrayList<String> tubeLines = new ArrayList<>();
//                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, tubeLines);
//                lister.setAdapter(arrayAdapter);
//
//
//
//                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//                String url ="https://www.metaweather.com/api/location/search/?query=san" ;
//
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try {
//                                    JSONArray array = new JSONArray(response);
//
//                                    for (int i = 0; i < array.length(); i++) {
//                                        JSONObject object = array.getJSONObject(i);
//                                    //    JSONObject object = array.optJSONObject(i);
//                                        //code pam
//                                        String name=object.getString("title");
//                                        String line = object.getString("title");
//
////                                        String line = object.optString("title");
////                                        String line1 = object.optString("latt_long");
////                                        String line2 = object.optString("woeid");
////                                        String line3 = object.optString("location_type");
//                                        if (name != null) {
//
//                                            tubeLines.add("Title : \t"+line);
////                                            tubeLines.add("Lattitude and Longitude : \t"+line1);
////                                            tubeLines.add("The woeid : \t"+line2);
////                                            tubeLines.add("Location: \t"+line3);
//                                        }
//                                    }
//                                    // Once we added the string to the array, we notify the arrayAdapter
//                                    arrayAdapter.notifyDataSetChanged();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                    }
//                });
//                queue.add(stringRequest);
//
//
//            }
//        });
//    }


    public void listviewbyname() {


        lister.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {




              //  String searchname = search.getText().toString();
                final ArrayList<String> tubeLines = new ArrayList<>();
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, tubeLines);
                lister.setAdapter(arrayAdapter);


                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://www.metaweather.com/api/location/search/?query=" ;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONArray jsonArray = null;
                                try {
                                    jsonArray = new JSONArray(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = null;
                                    try {
                                        jsonObject = (JSONObject) jsonArray.get(i);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    String id = null;
                                    try {
                                        id = (String) jsonObject.get("title");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                   // System.out.println(id);
                                    Toast.makeText(MainActivity.this,id,Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                queue.add(stringRequest);









            }
        });
    }
}










