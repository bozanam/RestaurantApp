package com.example.bozana.restaurant2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Queue;

public class yourOrder extends AppCompatActivity {

    ListView LV3;
    ArrayList<String> finalorder = new ArrayList<>();
    String[] keuzelijst;
    RequestQueue queue;
    String url = "https://resto.mprog.nl/order";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_order);
        Intent intent = getIntent();
        LV3 = findViewById(R.id.LV3);
        loadToSharedPrefs();



    }


    public void loadToSharedPrefs(){
        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);

        String keuzenrestored = prefs.getString("keuzen", null);
        Log.d("RESTAURANT", "loadToSharedPrefs: " + keuzenrestored);

        if (keuzenrestored != null) {
            keuzelijst = keuzenrestored.split(",");

            ArrayAdapter<String> myAdapter =
                    new ArrayAdapter<String>(yourOrder.this, android.R.layout.simple_list_item_1, keuzelijst);

            LV3.setAdapter(myAdapter);
        }

//        } else{
//            String faul = " Something went wrong ";
//
//            Toast.makeText(yourOrder.this, faul, Toast.LENGTH_SHORT).show();
//        }




    }
    public void BackToMenu(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        startActivity(intent);
    }

    public void ClearAll(View view) {
        //deleteSharedPreferences()
        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
        //String[] keuzelijst = {};

        Intent intent = new Intent(view.getContext(), yourOrder.class);
        startActivity(intent);
          }


    public void SubmitIt(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast toast = Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(getApplicationContext(), "This didn't work", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);



    }
}
