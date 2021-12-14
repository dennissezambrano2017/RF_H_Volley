 package com.example.rf_h_volley;

 import androidx.appcompat.app.AppCompatActivity;

 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;
 import android.widget.TextView;

 import com.android.volley.AuthFailureError;
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
 import java.util.HashMap;
 import java.util.Map;

 public class MainActivity extends AppCompatActivity {

     private TextView textView;
     private RequestQueue requeque;
     String url="https://api-uat.kushkipagos.com/transfer-subscriptions/v1/bankList";

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

         textView = findViewById(R.id.text_view_result);
         requeque= Volley.newRequestQueue(this);

         GetApiData();

     }
     private void GetApiData(){
         ArrayList<String> lstDatos = new ArrayList<String>();
         JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                 new Response.Listener<JSONObject>() {
                     @Override
                     public void onResponse(JSONObject response) {
                         try {
                             JSONArray jsonArray = response.getJSONArray("data");


                             for (int i=0; i <jsonArray.length();i++){
                                 JSONObject data = jsonArray.getJSONObject(i);
                                 String name = data.getString("name");

                                 lstDatos.add(name +"\n\n");
                             }
                             textView.setKeyListener(null);
                             textView.setText(lstDatos.toString());
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }
                 }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 error.printStackTrace();
             }
         })
         {    //this is the part, that adds the header to the request
             @Override
             public Map getHeaders() throws AuthFailureError {
                 HashMap param = new HashMap();
                 param.put("Public-Merchant-Id", "84e1d0de1fbf437e9779fd6a52a9ca18");
                 return param;
             }
         };
         requeque.add(request);
     }
 }