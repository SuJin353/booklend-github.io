package com.example.booklend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements OnParentClickListener{
    RecyclerView recyclerView;
    ArrayList<ParentModelClass> parentModelClassArrayList;
    ArrayList<ChildModelClass> fantasyArrayList;
    ArrayList<ChildModelClass> sci_fiArrayList;
    ArrayList<ChildModelClass> mysteryArrayList;
    ArrayList<ChildModelClass> romanceArrayList;
    ArrayList<ChildModelClass> adventureArrayList;
    String[] genres = {"Fantasy", "Science Fiction", "Mystery", "Romance", "Adventure"};
    ParentAdapter parentAdapter;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Books");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        Mapping();
        BottomNavigation();
        readData((childArrayList, genre) -> {
            switch (genre){
                case "Fantasy":
                {
                    fantasyArrayList = childArrayList;
                    parentModelClassArrayList.add(new ParentModelClass(genre, fantasyArrayList));
                    break;
                }
                case "Science Fiction":
                {
                    sci_fiArrayList = childArrayList;
                    parentModelClassArrayList.add(new ParentModelClass(genre, sci_fiArrayList));
                    break;
                }
                case "Mystery":
                {
                    mysteryArrayList = childArrayList;
                    parentModelClassArrayList.add(new ParentModelClass(genre, mysteryArrayList));
                    break;
                }
                case "Romance":
                {
                    romanceArrayList = childArrayList;
                    parentModelClassArrayList.add(new ParentModelClass(genre, romanceArrayList));
                    break;
                }
                case "Adventure":
                {
                    adventureArrayList = childArrayList;
                    parentModelClassArrayList.add(new ParentModelClass(genre, adventureArrayList));
                    break;
                }
            }
            parentAdapter.notifyDataSetChanged();
        });
    }
    void Mapping()
    {
        recyclerView = findViewById(R.id.rv_parent);
        parentModelClassArrayList = new ArrayList<>();
        fantasyArrayList = new ArrayList<>();
        sci_fiArrayList = new ArrayList<>();
        mysteryArrayList = new ArrayList<>();
        romanceArrayList = new ArrayList<>();
        adventureArrayList = new ArrayList<>();
        parentAdapter = new ParentAdapter(parentModelClassArrayList,Home.this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(parentAdapter);
    }
    @SuppressLint("NonConstantResourceId")
    void BottomNavigation()
    {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    return true;
                case R.id.bottom_serach:
                    startActivity(new Intent(getApplicationContext(), Search.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_user:
                    startActivity(new Intent(getApplicationContext(), User_Home.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_notification:
                    startActivity(new Intent(getApplicationContext(),Notification.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_logout:
                    promptLogoutConfirmation();
                    return true;
            }
            return false;
        });
    }
    private void promptLogoutConfirmation() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setMessage("Are you sure you want to logout ?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            dialogInterface.dismiss();
            finish();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void readData(CallBack myCallback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (String genre : genres) {
                    if (snapshot.hasChild(genre)) {
                        ArrayList<ChildModelClass> childModelClassArrayList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.child(genre).getChildren()) {
                            Book book = dataSnapshot.getValue(Book.class);
                            childModelClassArrayList.add(new ChildModelClass(book));
                        }
                        myCallback.onCallback(childModelClassArrayList, genre);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onChildItemClick(int parentPosition, int childPosition) {
        Intent intent = new Intent(Home.this, BookInfo.class);
        intent.putExtra("KEY", parentModelClassArrayList.get(parentPosition).getChildModelClassesList().get(childPosition).getBook().getKey());
        intent.putExtra("IMAGE", parentModelClassArrayList.get(parentPosition).getChildModelClassesList().get(childPosition).getBook().getImageUri());
        intent.putExtra("NAME",  parentModelClassArrayList.get(parentPosition).getChildModelClassesList().get(childPosition).getBook().getName());
        intent.putExtra("GENRE",  parentModelClassArrayList.get(parentPosition).getChildModelClassesList().get(childPosition).getBook().getGenre());
        intent.putExtra("AUTHOR",  parentModelClassArrayList.get(parentPosition).getChildModelClassesList().get(childPosition).getBook().getAuthor());
        intent.putExtra("PRICE",  parentModelClassArrayList.get(parentPosition).getChildModelClassesList().get(childPosition).getBook().getPrice());
        intent.putExtra("QUANTITY", parentModelClassArrayList.get(parentPosition).getChildModelClassesList().get(childPosition).getBook().getQuantity());
        intent.putExtra("DESCRIPTION",  parentModelClassArrayList.get(parentPosition).getChildModelClassesList().get(childPosition).getBook().getDescription());
        intent.putExtra("BORROWED", parentModelClassArrayList.get(parentPosition).getChildModelClassesList().get(childPosition).getBook().getBorrowed());
        startActivity(intent);
    }
}