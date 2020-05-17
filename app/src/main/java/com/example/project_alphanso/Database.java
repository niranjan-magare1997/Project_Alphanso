package com.example.project_alphanso;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Database {
    private String TAG = "Dream_Team";
    FirebaseFirestore db;
    DocumentSnapshot loginDb;
    MainActivity main;

    Database() {
        db = FirebaseFirestore.getInstance();
        main = new MainActivity();
    }

    /**
     *
     * @param id
     * @param pswd
     * @param callBackInterface "1 = ERROR" & "0 = LOGIN SUCCESS"
     */

    public void loginDb(String id, String pswd, final CallBackInterface callBackInterface) {
        try {
            Log.d(TAG, "loginDb | In loginDb function ");
            db
                    .collection("AlphansoCreds")
                    .whereEqualTo("ID", id)
                    .whereEqualTo("PASSWORD", pswd)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if(task.getResult().isEmpty()){
                                    Log.d(TAG, "loginDb | onComplete | User with this cred is not available");
                                    callBackInterface.callbackMethod(1);
                                }else{
                                    Log.d(TAG, "loginDb | onComplete | Valid User");
                                    callBackInterface.callbackMethod(0);
                                }
                            } else {
                                Log.d(TAG, "loginDb | onComplete | User not found");
                                callBackInterface.callbackMethod(1);
                            }
                        }
                    });
        }catch (Exception e){
            Log.e(TAG, "loginDb | Exception While Logging In " + e.getMessage() );
            callBackInterface.callbackMethod(1);
        }
    }
}
