package ca.mcgill.ecse321.passengerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ca.mcgill.ecse321.passengerapp.adapters.TripAdapter;
import ca.mcgill.ecse321.passengerapp.model.Registration;
import ca.mcgill.ecse321.passengerapp.model.Role;
import ca.mcgill.ecse321.passengerapp.model.Trip;
import ca.mcgill.ecse321.passengerapp.model.User;
import ca.mcgill.ecse321.passengerapp.util.HttpRequest;
import cz.msebera.android.httpclient.Header;


public class MyTripsActivity extends AppCompatActivity implements TripAdapter.ItemClickListener {
    private RecyclerView myTripsView;
    private TripAdapter adapter;

    private List<Trip> trips = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // toolbar.setTitle("My Trips"); // i have no idea why this is may have to just delete the toolbar

        myTripsView = (RecyclerView) findViewById(R.id.myTripsView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        populateMyTripsView();
    }


    private void populateMyTripsView(){
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        adapter = new TripAdapter(this, trips);

        myTripsView.setLayoutManager(lm);
        myTripsView.setAdapter(adapter);
        adapter.setClickListener(this);

        htmlGetMyTrips();
    }

    private void htmlGetMyTrips() {
        String userUrl = "api/users/name/" + MainActivity.mainUser.getUsername();

        HttpRequest.withToken(MainActivity.token).get(userUrl, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Gson gson = new GsonBuilder().create();
                User user = (User) gson.fromJson(response.toString(), User.class);

                MainActivity.mainUser = user;

                String tripsUrl = "api/users/" + MainActivity.mainUser.getId() + "/trips/";

                HttpRequest.withToken(MainActivity.token).get(tripsUrl, new RequestParams(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        Gson gson = new GsonBuilder().create();
                        Set<Registration> userRegs = MainActivity.mainUser.getRegistrations();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Trip trip = (Trip) gson.fromJson(obj.toString(), Trip.class);

                                Set<Registration> tripRegs = trip.getRegistrations();


                                for (Iterator<Registration> tripIt = tripRegs.iterator(); tripIt.hasNext(); ) {
                                    Registration tripReg = tripIt.next();

                                    if (userRegs.contains(tripReg)) {
                                        if (tripReg.getRole() == Role.PASSENGER) {
                                            trips.add(trip);
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        try {
                            throw (throwable);
                        } catch (Throwable throwable1) {
                            throwable1.printStackTrace();
                        }
                    }

                });
            }
        });
    }


    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, viewDeregisterTripActivity.class);
        intent.putExtra("TRIP_ID", adapter.getItem(position));
        startActivity(intent);
    }
}
