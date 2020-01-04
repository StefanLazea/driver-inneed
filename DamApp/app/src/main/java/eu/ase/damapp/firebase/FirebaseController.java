package eu.ase.damapp.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import eu.ase.damapp.util.Form;

public class FirebaseController {
    private DatabaseReference database;
    private FirebaseNotifier firebaseNotifier;
    private FirebaseDatabase controller;
    private static FirebaseController firebaseController;

    public FirebaseController(FirebaseNotifier firebaseNotifier) {
        this.firebaseNotifier = firebaseNotifier;
        this.controller = FirebaseDatabase.getInstance();
    }

    private void open() {
        database = controller.getReference("driver-inneed");
    }

    public static FirebaseController getInstance(FirebaseNotifier firebaseNotifier) {
        if (firebaseController == null) {
            synchronized (FirebaseController.class) {
                if (firebaseController == null) {
                    firebaseController = new FirebaseController(firebaseNotifier);
                }
            }
        }
        return firebaseController;
    }
    public String upsert(final Form form) {
        if (form == null) {
            return null;
        }
        open();
        if (form.getId() == null || form.getId().trim().isEmpty()) {
            form.setId(database.push().getKey());
        }
        database.child(form.getId()).setValue(form);
        database.child(form.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Form temp = dataSnapshot.getValue(Form.class);
                if (temp != null) {
                    Log.i("FireController", "Form is updated " + temp.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseController", "Coach is not saved");
            }
        });
        return form.getId();
    }
}
