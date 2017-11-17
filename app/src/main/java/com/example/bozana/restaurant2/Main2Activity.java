package com.example.bozana.restaurant2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Main2Activity extends AppCompatActivity {

    ListView LV2;
    ArrayList<String> arrlist = new ArrayList<>();
    ArrayList choice = new ArrayList();
    Button btnYO ;
    JSONArray larray;
    int aantal;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://resto.mprog.nl/menu?category=";
        final JSONObject json = new JSONObject();
        Intent intent = getIntent();
        final String Chosen = intent.getStringExtra("Chosen");
        loadToSharedPrefs();





        LV2 = findViewById(R.id.LV2);
        ListAdapter theAdapter = new ArrayAdapter<String>(this, R.layout.row_layout, R.id.textView1,
                arrlist);

        //Log.d("resto", "onCreate: " + url + Chosen);
        JsonObjectRequest jsonobject = new JsonObjectRequest(Request.Method.GET, url + Chosen, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("resto", "onResponse: " + response);
                        try {
                            larray = response.getJSONArray("items");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < larray.length(); i++) {
                            try {
                                arrlist.add(larray.getJSONObject(i).getString("name") +  "      €" + larray.getJSONObject(i).getString("price") );
                                //arrlist.add(larray.getJSONObject(i).getString("price"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        ArrayAdapter<String> myAdapter =
                                new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_list_item_1, arrlist);
                        //Log.d("resto", "onResponse: "+ arrlist.size());
                        LV2.setAdapter(myAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getApplicationContext(), "This didn't work", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonobject);

        LV2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String menuPicked = "You picked " + String.valueOf(parent.getItemAtPosition(position));

                Toast.makeText(Main2Activity.this, menuPicked, Toast.LENGTH_SHORT).show();


                if (Chosen.equals("appetizers") ){
                    switch (position) {
                        case 0:

                            choice.add("Chicken Noodle Soup");
                            break;
                        case 1:
                            choice.add("Italian Salad");
                            break;
                        default:
                            break;
                    }
                }if (Chosen.equals("entrees") ) {
                    switch (position) {
                        case 0:

                            choice.add("Spaghetti and Meatballs");
                            break;
                        case 1:
                            choice.add("Margherita Pizza");
                            break;
                        case 2:
                            choice.add("Grilled Steelhead Trout Sandwich");
                            break;
                        case 3:
                            choice.add("Pesto Linguini");
                        default:
                            break;
                    }}
                Log.d("RESTORANT", "onItemClick: " + choice);
                saveToSharedPrefs();
                }});





    }

    public void saveToSharedPrefs(){
        Log.d("RESTOO", "saveToSharedPrefs: " + choice);
        String keuzen = choice.toString();
        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("keuzen", keuzen);
        editor.commit();
    }

    public void loadToSharedPrefs() {
        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);

        String keuzenrestored = prefs.getString("keuzen", null);
        Log.d("RESTAURANT", "loadToSharedPrefs: " + keuzenrestored);

        if (keuzenrestored != null) {
            //keuzelijst = keuzenrestored.split(",");
            choice = new ArrayList<String>(Arrays.asList(keuzenrestored.split(",")));
        }
    }



        public void BackToMenu(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
    }
    public int getCount(){

        return aantal = choice.size();
    }

    public void goToYourOderd(View view) {

        Intent intent = new Intent(view.getContext(), yourOrder.class);
        startActivity(intent);
    }
}





