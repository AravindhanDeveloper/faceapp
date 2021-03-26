package com.babbangona.faceapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.babbangona.faceapp.BuildConfig;
import com.babbangona.faceapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FaceRecognitionHomePageActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbarr)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_version)
    TextView toolbar_version;


    @BindView(R.id.moveToInstruction)
    Button moveToInstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition_home_page);


        ButterKnife.bind(this);
        setToolBar("BABBANGONA");


        moveToInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FaceRecognitionHomePageActivity.this, InstructionsActivity.class);
                startActivity(intent);
            }
        });

    }


    private void setToolBar(String title){
        toolbar_title.setText(title);
        toolbar.setTitle("");
        toolbar_version.setText("v"+ BuildConfig.VERSION_NAME);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this,"Home Pressed",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuAbout:
                Toast.makeText(this,"Sync Pressed",Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 23);
            }
        }
    }
}