package my.edu.taruc.tarucmoneysaver;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView captureImageView;

    Button captureButton;
    Button submitButton;
    Button cancelButton;

    EditText editFoodname;
    EditText editRestaurantname;
    EditText editFoodprice;
    EditText editFoodcomment;

    RequestQueue requestQueue;
    String insertURL = "http://tarucmoneysaverdb.esy.es/insertFood.php";

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        captureButton = (Button)findViewById(R.id.captureImageButton);
        captureImageView = (ImageView)findViewById(R.id.captureImageView);

        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this);

        editFoodname = (EditText) findViewById(R.id.foodNameEditText);
        editRestaurantname = (EditText) findViewById(R.id.restaurantNameEditText);
        editFoodprice = (EditText) findViewById(R.id.foodPriceEditText);
        editFoodcomment = (EditText) findViewById(R.id.commentEditText);

        //Disable the captureImageButton if the user's phone no camera
        if(!hasCamera())
            captureButton.setEnabled(false);

        submitButton = (Button) findViewById(R.id.submitButton);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                StringRequest request = new StringRequest(Request.Method.POST,insertURL,new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        //System.out.println(response.toString());
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                    }
                }) {
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<>();
                        parameters.put("name",editFoodname.getText().toString());
                        parameters.put("restaurant",editRestaurantname.getText().toString());
                        parameters.put("price",editFoodprice.getText().toString());
                        parameters.put("comment",editFoodcomment.getText().toString());

                        return parameters;
                    }
                };
                requestQueue.add(request);
                Toast.makeText(getApplicationContext(),"Submit Successfully",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    //Check if the user has a camera
    private boolean hasCamera()
    {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //launch Camera
    public void launchCamera(View view)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Take picture and pass results to onActivityResult
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    //If want to return image taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            //Get the picture
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            captureImageView.setImageBitmap(photo);
        }
    }

    //Cancel button back to main page function
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
            case R.id.cancelButton:
                backMain();
                break;
        }
    }
}
