package my.edu.taruc.tarucmoneysaver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class listActivity extends AppCompatActivity implements View.OnClickListener{

    TextView result;
    RequestQueue requestQueue;
    String showURL = "http://tarucmoneysaverdb.esy.es/showFoodList.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Back button on list page
        Button backButtonList = (Button) findViewById(R.id.backButtonList);
        backButtonList.setOnClickListener(this);

        Button searchButtonList = (Button) findViewById(R.id.searchButtonList);

        result = (TextView) findViewById(R.id.showFoodView);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Show Food List
        searchButtonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                System.out.println("ww");
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        showURL,new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        System.out.println(response.toString());
                        try {
                            JSONArray foodlists = response.getJSONArray("TMSfoodlist");
                            for (int x = 0; x < foodlists.length(); x++){
                                JSONObject foodlist = foodlists.getJSONObject(x);

                                String foodname = foodlist.getString("name");
                                String restname = foodlist.getString("restaurant");
                                String foodprice = foodlist.getString("price");
                                String foodcomment = foodlist.getString("comment");

                                result.append("Food : " + foodname + "\n" +
                                        "Restaurant : " + restname + "\n" +
                                        "Food Price : RM" + foodprice+ "\n" +
                                        "Comment : " + foodcomment + "\n\n");
                            }
                            result.append("====================================\n");
                            result.setMovementMethod(new ScrollingMovementMethod());
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        System.out.append(error.getMessage());
                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });
    }

    private void backMain()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.backButtonList:
                backMain();
                break;
        }
    }
}
