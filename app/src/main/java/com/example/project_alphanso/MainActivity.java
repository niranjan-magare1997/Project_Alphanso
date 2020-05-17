package com.example.project_alphanso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "Dream_Team";
    EditText id_EditText, pswd_EditText;
    Button logIn_Btn, signUp_Btn;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setComponents();
    }

    public void setComponents(){
        logIn_Btn = findViewById(R.id.logIn);
        signUp_Btn = findViewById(R.id.signUp);

        id_EditText = findViewById(R.id.Id_EditText);
        pswd_EditText = findViewById(R.id.Password_EditText);

        database = new Database();

        logIn_Btn.setOnClickListener(this);
        signUp_Btn.setOnClickListener(this);

        signUp_Btn.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logIn:
                loginUser();
                break;
            case R.id.signUp:
                break;
        }
    }

    private void loginUser(){
        try{
            Log.d(TAG, "loginUser | In loginUser function ");
            String id = id_EditText.getText().toString();
            String pswd = pswd_EditText.getText().toString();

            database.loginDb(id,pswd, new CallBackInterface() {
                @Override
                public void callbackMethod(Integer result) {
                    if(result == 0){
                        Log.d(TAG, "loginUser | callbackMethod | Login Successful");
                    }else if(result == 1){
                        Log.d(TAG, "loginUser | callbackMethod | Login Failed");
                    }else {
                        Log.d(TAG, "loginUser | callbackMethod | Unknown Error Occurred");
                    }
                }
            });
        }catch (Exception e){
            Log.e(TAG, "loginUser | Login Failed ERROR: " + e.getMessage() );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == 10){
                String result = data.getStringExtra("Return Value");
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
