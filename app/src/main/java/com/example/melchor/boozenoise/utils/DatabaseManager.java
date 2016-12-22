package com.example.melchor.boozenoise.utils;

import android.os.AsyncTask;

import com.example.melchor.boozenoise.entities.Bar;
import com.example.melchor.boozenoise.entities.ListBars;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManager extends AsyncTask<String, Void, Void> {

    private static final String TAG = DatabaseManager.class.getSimpleName();
    private String databaseAction;
    private ListBars listBars;
    private ChildEventListener childEventListener;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public DatabaseManager(String databaseAction, ListBars listBars) {
        this.databaseAction = databaseAction;
        this.listBars = listBars;
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("bars");
    }

    @Override
    protected void onPreExecute() {
        /*childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG,"name : "+dataSnapshot.getValue(Bar.class).getName());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };*/
    }

    @Override
    protected Void doInBackground(String... param) {
        if (databaseAction.equals("write")) writeToDatabase();
        return null;
    }

    private void writeToDatabase() {
        /*Query query = databaseReference.child("place_id");
        //query.addChildEventListener(childEventListener);
        for (Bar bar : listBars.getResultsFromWebservice()) {
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d(TAG,"Même résultat ! " + dataSnapshot.getValue(Bar.class).getName());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }*/

        for (Bar bar : listBars.getResultsFromWebservice()) {
            Map<String, Object> updates = new HashMap<>();
            updates.put(bar.getPlace_id(), bar);
            databaseReference.updateChildren(updates);
        }

        //databaseReference.child(push().setValue(listBars.getResultsFromWebservice());

        //databaseReference.setValue(listBars.getResultsFromWebservice());
    }

    private void findByPlaceId(String place_id) {
        Query query = databaseReference.orderByChild("place_id").equalTo(place_id);
        query.addChildEventListener(childEventListener);
    }
}
