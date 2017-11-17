package com.example.bozana.restaurant2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    ArrayList<String> myArray = new ArrayList<>() ;

    ListView theListView;
    JSONArray jarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        theListView = findViewById(R.id.TheListView);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://resto.mprog.nl/categories";
        Intent intent = getIntent();



        ListAdapter theAdapter = new ArrayAdapter<String>(this, R.layout.row_layout, R.id.textView1,
                myArray);

        // Request a string response from the provided URL.
        JsonObjectRequest jsonobject = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            jarray = response.getJSONArray("categories");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        for (int i = 0; i < jarray.length(); i++) {
                            try {
                                myArray.add(jarray.getString(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        ArrayAdapter<String> myAdapter =
                                new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, myArray);

                        theListView.setAdapter(myAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getApplicationContext(), "This didn't work", Toast.LENGTH_SHORT);
                toast.show();
            }
        }) ;

        // Add the request to the RequestQueue.
        queue.add(jsonobject);



       theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               String menuPicked = "You picked " + String.valueOf(parent.getItemAtPosition(position));

               Toast.makeText(MainActivity.this, menuPicked, Toast.LENGTH_SHORT).show();

               String choice =null;
               switch (position){
                   case 0 :
                       choice = new String();
                       choice = "appetizers";
                       break;
                   case 1:
                       choice = new String();
                       choice = "entrees";
                       break;
                   default:
                       break;
               }


               Intent intent = new Intent(view.getContext(), Main2Activity.class);
               intent.putExtra("Chosen", choice);
               startActivity(intent);
               //finish();
           }
       });



        }



    }



