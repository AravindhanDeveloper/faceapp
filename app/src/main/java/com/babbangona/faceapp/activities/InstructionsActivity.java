package com.babbangona.faceapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.babbangona.faceapp.BuildConfig;
import com.babbangona.faceapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionsActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbarr)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_version)
    TextView toolbar_version;
    @BindView(R.id.continueButton)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        ButterKnife.bind(this);
        setToolBar("BABBANGONA");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructionsActivity.this, FaceRecognitionActivity.class);
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
}