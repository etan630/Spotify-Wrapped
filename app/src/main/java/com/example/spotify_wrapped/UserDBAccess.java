package com.example.spotify_wrapped;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.spotify_wrapped.data_collection.Wrapped;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class UserDBAccess {



    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public FirebaseFirestore getFireBaseObj(){
        return db;
    }

    private String userID;
    //private List<Wrapped> pastWraps = new ArrayList<>();
    private Map<String, Object> dbData = new HashMap<>();
    private boolean deletedMarker = false;

    public UserDBAccess(String userID) {
        this.userID = userID;

        /*
        DocumentReference docRef = db.collection("users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("UserDBAccess", "DocumentSnapshot data: " + document.getData());
                        UserDBAccess.this.setLocalData((HashMap) document.getData());
                    } else {
                        System.out.println("adding a dude");
                        UserDBAccess.this.dbData = new HashMap<String, Object>() {{
                            put("username", "");
                            put("wraps", "");
                        }};
                        db.collection("users").document(userID).set(UserDBAccess.this.getDbData());
                        Log.d("UserDBAccess", "No such document; Added new element");
                    }
                } else {
                    Log.d("UserDBAccess", "get failed with ", task.getException());
                }

            }
        });
        */
    }

    private void setLocalData(Map<String, Object> d) {
        this.dbData = d;
    }

    public void setUsername(String username) throws Exception {
        if (isDeleted()) {
            throw new Exception("User has been deleted");
        }

        dbData.put("username", username);
        db.collection("users").document(this.userID).set(this.dbData);
    }

    public interface callbackUsername  {
        void actionsToDo(String username);
    }
    public void getUsername(callbackUsername toRun) {
        DocumentReference docRef = db.collection("users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("UserDBAccess", "DocumentSnapshot data: " + document.getData());
                        UserDBAccess.this.setLocalData((HashMap) document.getData());
                        String username = (String) document.getData().get("username");
                        System.out.println("Inside getUsername, username=" + username);
                        toRun.actionsToDo(username);
                    } else {
                        if (deletedMarker == false) {
                            System.out.println("adding a dude");
                            UserDBAccess.this.dbData = new HashMap<String, Object>() {{
                                put("username", "");
                                put("wraps", new ArrayList<>());
                            }};
                            db.collection("users").document(userID).set(UserDBAccess.this.getDbData());
                            Log.d("UserDBAccess", "No such document; Added new element");
                            toRun.actionsToDo("");
                        }
                    }
                } else {
                    Log.d("UserDBAccess", "get failed with ", task.getException());
                }
            }
        });
    }

    public boolean isUsernameSet() {
        if (isDeleted()) {
            return false;
        }

        return dbData.get("username").equals("") ? false : true;
    }


    public void addWrapped(Wrapped toAdd){
        DocumentReference docRef = db.collection("users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("UserDBAccess", "DocumentSnapshot data: " + document.getData());
                        List<Wrapped> wraps = (List<Wrapped>) document.getData().get("wraps");
                        wraps.add(toAdd);
                        UserDBAccess.this.setLocalData((HashMap) document.getData());
                        UserDBAccess.this.getDbData().put("wraps", wraps);
                        db.collection("users").document(userID).set(UserDBAccess.this.getDbData());
                    } else {

                    }
                } else {
                    Log.d("UserDBAccess", "get failed with ", task.getException());
                }
            }
        });
    }

    public interface callbackPastWraps {
        void afterGet(List<HashMap<String, Object>> pastWraps);
    }
    public void getPastWraps(callbackPastWraps toGet) {
        DocumentReference docRef = db.collection("users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("UserDBAccess", "DocumentSnapshot data: " + document.getData());
                        UserDBAccess.this.setLocalData((HashMap) document.getData());
                        List<HashMap<String, Object>> pastWraps = (List<HashMap<String, Object>>) document.getData().get("wraps");
                        //System.out.println(pastWraps);
                        if (pastWraps != null) {
                            toGet.afterGet(pastWraps);
                        }
                    }
                } else {
                    Log.d("UserDBAccess", "get failed with ", task.getException());
                }
            }
        });
    }

    public void deleteAccount() {
        db.collection("users").document(userID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UserDBAccess", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("UserDBAccess", "Error deleting document", e);
                    }
                });
        dbData = null;
        userID = null;
        deletedMarker = true;
    }

    public boolean isDeleted() {
        return deletedMarker;
    }

    public Map<String, Object> getDbData(){
        return this.dbData;
    }

}