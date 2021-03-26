package com.babbangona.faceapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.babbangona.faceapp.R;
import com.babbangona.faceapp.util.SharedPreference;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FaceRecognitionActivity extends AppCompatActivity {

    @BindView(R.id.iv_member_picture)
    public ImageView iv_member_picture;

    //Scan Successful
    @BindView(R.id.linear_layout_bgrf_scan_success)
    public LinearLayout linear_layout_scan_success;

    //No Blink
    @BindView(R.id.linear_layout_noBlink)
    public LinearLayout linear_layout_no_blink;

    //Is there a face in the picture ?
    @BindView(R.id.linear_layout_face_in)
    public LinearLayout linear_layout_face_in;

    //Is this the person you want to register ?
    @BindView(R.id.linear_layout_want_to)
    public LinearLayout linear_layout_want_to_register;

    //No face in picture Registration is invalid
    @BindView(R.id.linear_layout_noFace)
    public LinearLayout linear_layout_noFace;

    //Wrong person Registration is invalid
    @BindView(R.id.linear_layout_wrongface)
    public LinearLayout linear_layout_wrongface;

    //Verification Failed : Bad lighting
    @BindView(R.id.linear_layout_Bad_lighting)
    public LinearLayout linear_layout_Bad_lighting;

    //Is this the person in front of you ?
    @BindView(R.id.linear_layout_in_front)
    public LinearLayout linear_layout_in_front;

    //Do you want to recapture ?
    @BindView(R.id.linear_layout_recapture)
    public LinearLayout linear_layout_recapture;

    //Verification Successful
    @BindView(R.id.linear_layout_bgrf_verifi_success)
    public LinearLayout linear_layout_bgrf_verifi_success;

    //Blink Cancel
    @BindView(R.id.bgfr_no_blink_cancel)
    TextView bgfr_no_blink_cancel;

    //Blink Retry
    @BindView(R.id.bgfr_no_blink_retry)
    TextView bgfr_no_blink_retry;

    //Face in No
    @BindView(R.id.bgfr_face_in_no)
    TextView bgfr_face_in_no;

    //Face in Yes
    @BindView(R.id.bgfr_face_in_yes)
    TextView bgfr_face_in_yes;

    //No Face Retry
    @BindView(R.id.bgfr_no_face_retry)
    TextView bgfr_no_face_retry;

    //Want to No
    @BindView(R.id.bgfr_want_to_no)
    TextView bgfr_want_to_no;

    //want to Yes
    @BindView(R.id.bgfr_want_to_yes)
    TextView bgfr_want_to_yes;

    //want to Register Done
    @BindView(R.id.bgfr_wrong_face_done)
    TextView bgfr_wrong_face_done;

    //Scan Success Button
    @BindView(R.id.bgfr_scan_success)
    ImageView bgfr_scan_success;




    int state=3;
    private SharedPreference sharedPreference;
    private boolean tries=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition);
        ButterKnife.bind(this);
        sharedPreference=new SharedPreference(this);
        startActivityForResult(new Intent(this,VerifyActivity.class),101);
        showLogic(state);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 101) {
            Log.d("Face","Result Check");
            if (data != null) {
                if (data.getIntExtra("RESULT", 0) == 1) {
                    //Image image;
                    String image  =sharedPreference.getValue(String.class,"CurrentStaffPicture");
                    setCapturedImage(iv_member_picture,image);//setCapturedImage(iv_member_picture,ccOfficerData.getStaffID(),this,image.getLargeImage());
                    state=4;
                }
                else {
                    state=3;
                }
                if(data.getIntExtra("noblink",0)==51){
                    state=2;
                }
            }
        }

        if (requestCode == 201) {
            Log.d("Face","Result Check");
            if (data != null) {
                if (data.getIntExtra("RESULT", 0) == 1) {
                    //Image image;
                    String image  =sharedPreference.getValue(String.class,"CurrentStaffPicture");
                    setCapturedImage(iv_member_picture,image);//setCapturedImage(iv_member_picture,ccOfficerData.getStaffID(),this,image.getLargeImage());
                    state=4;
                }
                else {
                    state=3;
                }
                if(data.getIntExtra("noblink",0)==51){
                    tries=true;
                    state=2;
                }
            }
        }

        if (requestCode == 301) {
            Log.d("Face","Result Check");
            if (data != null) {
                if (data.getIntExtra("RESULT", 0) == 1) {
                    //Image image;
                    String image  =sharedPreference.getValue(String.class,"CurrentStaffPicture");
                    setCapturedImage(iv_member_picture,image);//setCapturedImage(iv_member_picture,ccOfficerData.getStaffID(),this,image.getLargeImage());
                    state=4;
                }
                else {
                    state=3;
                }
                if(data.getIntExtra("noblink",0)==51){
                    tries=true;
                    state=2;
                }
            }
        }
        showLogic(state);
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void setCapturedImage(ImageView iv_member_picture,String new_tracker) {
        this.runOnUiThread(()->{
            if (new_tracker != null){
                Bitmap myBitmap = getBitmap(new_tracker);
                Matrix matrix = new Matrix();
                matrix.preRotate(90);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(myBitmap, 400, 300, true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                iv_member_picture.setImageBitmap(rotatedBitmap);
            }
        });
    }

    private Bitmap getBitmap(String member_picture_byte_array){
        byte[] imageAsBytes = new byte[0];
        if (member_picture_byte_array != null) {
            imageAsBytes = Base64.decode(member_picture_byte_array.getBytes(), Base64.DEFAULT);
        }
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    private void setPicture(ImageView iv_member_picture) {
        File imgFile = new File("staffID.jpg");
        if(imgFile.exists()){
            this.runOnUiThread(()-> {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                iv_member_picture.setImageBitmap(myBitmap);
            });
        }else {
            this.runOnUiThread(()-> {
                iv_member_picture.setImageResource(R.drawable.mask_group);
            });
        }


    }

    public void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }

    public void hideView(View view) {
        view.setVisibility(View.GONE);
    }

    private void showLogic(int state) {
        if (state == 1){
           //Scan Success
            hideView(linear_layout_Bad_lighting);
            hideView(linear_layout_bgrf_verifi_success);
            hideView(linear_layout_face_in);
            hideView(linear_layout_in_front);
            hideView(linear_layout_no_blink);
            hideView(linear_layout_noFace);
            showView(linear_layout_scan_success);
            hideView(linear_layout_wrongface);
            hideView(linear_layout_want_to_register);
            hideView(linear_layout_recapture);

        }else if (state == 2) {
            //No Blink
            iv_member_picture.setImageResource(R.drawable.bgfrface);
            hideView(linear_layout_Bad_lighting);
            hideView(linear_layout_bgrf_verifi_success);
            hideView(linear_layout_face_in);
            hideView(linear_layout_in_front);
            showView(linear_layout_no_blink);
            hideView(linear_layout_noFace);
            hideView(linear_layout_scan_success);
            hideView(linear_layout_wrongface);
            hideView(linear_layout_want_to_register);
            hideView(linear_layout_recapture);
        }
        else if (state == 3) {
            //Temporary
            hideView(linear_layout_Bad_lighting);
            hideView(linear_layout_bgrf_verifi_success);
            hideView(linear_layout_face_in);
            hideView(linear_layout_in_front);
            hideView(linear_layout_no_blink);
            hideView(linear_layout_noFace);
            hideView(linear_layout_scan_success);
            hideView(linear_layout_wrongface);
            hideView(linear_layout_want_to_register);
            hideView(linear_layout_recapture);

        }
        else if (state == 4) {
            //Is there a face in this picture
            hideView(linear_layout_Bad_lighting);
            hideView(linear_layout_bgrf_verifi_success);
            showView(linear_layout_face_in);
            hideView(linear_layout_in_front);
            hideView(linear_layout_no_blink);
            hideView(linear_layout_noFace);
            hideView(linear_layout_scan_success);
            hideView(linear_layout_wrongface);
            hideView(linear_layout_want_to_register);
            hideView(linear_layout_recapture);

        }
        else if (state == 5) {
            //No Face
            hideView(linear_layout_Bad_lighting);
            hideView(linear_layout_bgrf_verifi_success);
            hideView(linear_layout_face_in);
            hideView(linear_layout_in_front);
            hideView(linear_layout_no_blink);
            showView(linear_layout_noFace);
            hideView(linear_layout_scan_success);
            hideView(linear_layout_wrongface);
            hideView(linear_layout_want_to_register);
            hideView(linear_layout_recapture);
        }
        else if(state==6){
            //Want to Register
            hideView(linear_layout_Bad_lighting);
            hideView(linear_layout_bgrf_verifi_success);
            hideView(linear_layout_face_in);
            hideView(linear_layout_in_front);
            hideView(linear_layout_no_blink);
            hideView(linear_layout_noFace);
            hideView(linear_layout_scan_success);
            hideView(linear_layout_wrongface);
            showView(linear_layout_want_to_register);
            hideView(linear_layout_recapture);
        }

        else if(state==7){
            //Want to Register
            hideView(linear_layout_Bad_lighting);
            hideView(linear_layout_bgrf_verifi_success);
            hideView(linear_layout_face_in);
            hideView(linear_layout_in_front);
            hideView(linear_layout_no_blink);
            hideView(linear_layout_noFace);
            hideView(linear_layout_scan_success);
            showView(linear_layout_wrongface);
            hideView(linear_layout_want_to_register);
            hideView(linear_layout_recapture);
        }
    }

    @OnClick(R.id.bgfr_no_blink_cancel)
    public void setBgfr_no_blink_cancel(){
        startActivity(new Intent(this,FaceRecognitionHomePageActivity.class));
    }

    @OnClick(R.id.bgfr_no_blink_retry)
    public void setBgfr_no_blink_retry(){
        if(!tries) {
            startActivityForResult(new Intent(this, VerifyActivity.class), 201);
        }
        else {
            showDialogueBoxContactProductSupport();
        }
    }

    //Face No
    @OnClick(R.id.bgfr_face_in_no)
    public void setBgfr_face_in_no(){
        state=5;
        iv_member_picture.setImageResource(R.drawable.no_face);
        showLogic(state);
    }

    //Face Retry
    @OnClick(R.id.bgfr_no_face_retry)
    public void setBgfr_no_face_retry(){
        //Popup
        finish();
    }

    //Face Yes
    @OnClick(R.id.bgfr_face_in_yes)
    public void setBgfr_face_in_yes(){
       state=6;
       setPicture(iv_member_picture);
       showLogic(state);
    }

    //Want to register Yes and No and Done
    @OnClick(R.id.bgfr_want_to_yes)
    public void setBgfr_want_to_yes(){
        showDialogueBoxAlert();

    }
    @OnClick(R.id.bgfr_want_to_no)
    public void setBgfr_want_to_no(){
        state=7;
        showLogic(state);
    }
    @OnClick(R.id.bgfr_wrong_face_done)
    public void setBgfr_wrong_face_done(){
        //Back to Face recognition Activity
        startActivity(new Intent(FaceRecognitionActivity.this,FaceRecognitionHomePageActivity.class));
    }

    //Scan Success Button
    @OnClick(R.id.bgfr_scan_success)
    public void setBgfr_scan_success(){
        //Back to Face recognition Activity
        startActivity(new Intent(FaceRecognitionActivity.this,FaceRecognitionHomePageActivity.class));
    }



    private void showDialogueBoxContactProductSupport() {
        Dialog openDialog = new Dialog(FaceRecognitionActivity.this);
        openDialog.setContentView(R.layout.popup_contact_product_support);
        Button btn=openDialog.findViewById(R.id.txtContent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(FaceRecognitionActivity.this, VerifyActivity.class), 301);
            }
        });

        Button contact_product_support=openDialog.findViewById(R.id.contact_product_support);

        contact_product_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogueBoxCallIssueCenter();
            }
        });


        if(!openDialog.isShowing()) {
            openDialog.show();
        }

    }

    private void showDialogueBoxCallIssueCenter() {
        Dialog openDialog = new Dialog(FaceRecognitionActivity.this);
        openDialog.setContentView(R.layout.popup_issue_center);
        Button call_product_support=openDialog.findViewById(R.id.call_product_support);
        call_product_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FaceRecognitionActivity.this,FaceRecognitionHomePageActivity.class));
            }
        });

        Button issue_center=openDialog.findViewById(R.id.issue_center);
        issue_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FaceRecognitionActivity.this,FaceRecognitionHomePageActivity.class));
            }
        });
        if(!openDialog.isShowing()) {
            openDialog.show();
        }

    }

    private void showDialogueBoxAlert() {
        Dialog openDialog = new Dialog(FaceRecognitionActivity.this);
        openDialog.setContentView(R.layout.popup_alert);
        openDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button alert_cancel=openDialog.findViewById(R.id.alert_cancel);
        alert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FaceRecognitionActivity.this,FaceRecognitionHomePageActivity.class));
            }
        });

        Button alert_confirm=openDialog.findViewById(R.id.alert_confirm);
        alert_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state=1;
                showLogic(state);
                openDialog.cancel();
            }
        });
        if(!openDialog.isShowing()) {
            openDialog.show();
        }

    }
}
