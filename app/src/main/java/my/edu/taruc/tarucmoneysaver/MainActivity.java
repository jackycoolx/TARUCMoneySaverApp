package my.edu.taruc.tarucmoneysaver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Declaration for add button
        Button addButton;
        addButton = (Button) findViewById(R.id.addContentButton);
        addButton.setOnClickListener(this);

        //Declaration for list button
        Button listButton;
        listButton = (Button) findViewById(R.id.ListButton);
        listButton.setOnClickListener(this);

        //Declaration for record button
        Button recordButton;
        recordButton = (Button) findViewById(R.id.RecordButton);
        recordButton.setOnClickListener(this);
    }

    private void goAddPage()
    {
        startActivity(new Intent("my.edu.taruc.tarucmoneysaver.addActivity"));
    }

    private void goListPage()
    {
        startActivity(new Intent("my.edu.taruc.tarucmoneysaver.listActivity"));
    }

    private void goRecordPage()
    {
        startActivity(new Intent("my.edu.taruc.tarucmoneysaver.recordActivity"));
    }

    //Go to add content activity page
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.addContentButton:
                goAddPage();
                break;

            case R.id.ListButton:
                goListPage();
                break;

            case R.id.RecordButton:
                goRecordPage();
                break;
        }
    }
}
