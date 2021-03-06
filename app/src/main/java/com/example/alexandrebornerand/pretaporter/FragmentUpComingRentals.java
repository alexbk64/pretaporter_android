package com.example.alexandrebornerand.pretaporter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alexandrebornerand.pretaporter.Adapter.RecyclerViewAdapter;
import com.example.alexandrebornerand.pretaporter.Model.Rental;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentUpComingRentals extends Fragment {

    final private String TAG = "FragmentActiveRentals";

    private View v;
    private RecyclerView recyclerView;
    private ArrayList<Rental> upComingRentalsArrayList;
    private RecyclerViewAdapter recyclerViewAdapter;

    //date format for parsing
    final private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");


    //Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    //empty constructor needed for firebase
    public FragmentUpComingRentals() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.upcoming_rentals_fragment, container, false);
        recyclerView = v.findViewById(R.id.recycler_upcoming_rentals);
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), upComingRentalsArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialise Firebase variables
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        //set root references for now, can get more specific later
        databaseReference = firebaseDatabase.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        //set root ref like database ref
        storageReference = firebaseStorage.getReference();

        //initialise other variables
        upComingRentalsArrayList = new ArrayList<>();
        //set query to match on rentals in which the user is involved
        Query query = databaseReference
                .child("rentals")
                .orderByChild("_renter")
                .equalTo(firebaseUser.getUid());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: ");
                //get rental objects
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    //save only rentals which are NOT active and have yet to start.
                    Rental temp = ds.getValue(Rental.class);
                    String strStartDate = temp.get_fromDate();
                    Date today = new Date();
                    Date startDate = new Date();
                    try {
                        startDate = simpleDateFormat.parse(strStartDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //If NOT active, and start date is after this moment
                    if (!temp.isActive() && startDate.after(today))
                        upComingRentalsArrayList.add(ds.getValue(Rental.class));
                }
                recyclerViewAdapter.setList(upComingRentalsArrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: databaseError"+databaseError);
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
