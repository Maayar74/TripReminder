package com.example.tripreminder;


import static com.example.tripreminder.R.id.autocomplete_fragment;
import static com.example.tripreminder.R.id.place_autocomplete;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class AddTrip extends AppCompatActivity {
    TextView txtTime;
    TextView txtDate;
    TextView notes;
    Button btn;
    Calendar c;
    Spinner trip;
    Spinner repeat;
    ImageView calendar,clock;
    DatePickerDialog dpd;
    TimePickerDialog tpd;
    int hour , minut;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = findViewById(R.id.calendar);
        txtTime = findViewById(R.id.txtTime);
        txtDate = findViewById(R.id.txtDate);

        notes = findViewById(R.id.notes);
        notes.setOnClickListener(v -> {
//            setContentView(R.layout.activity_note);
//            Uri gmmIntentUri = Uri.parse("google.navigation:q="+start.getText().toString());
//            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//            mapIntent.setPackage("com.google.android.apps.maps");
//            startActivity(mapIntent);

        });
            if (!Places.isInitialized()) {
                Places.initialize(getApplicationContext(), "AIzaSyCW_X5xn8RMYkRpAnZt_OEx1UAZgCpOVMM");
                }
//            PlacesClient placesClient = Places.createClient(this);


        AutocompleteSupportFragment autocompleteSupportFragment =(AutocompleteSupportFragment)getSupportFragmentManager().findFragmentById(autocomplete_fragment);
        Objects.requireNonNull(autocompleteSupportFragment).setTypeFilter(TypeFilter.GEOCODE);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.NAME));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
//                Log.i("AddTrip", "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });


        AutocompleteSupportFragment autocompleteSupportFragmentStart =(AutocompleteSupportFragment)getSupportFragmentManager().findFragmentById(place_autocomplete);
        Objects.requireNonNull(autocompleteSupportFragmentStart).setTypeFilter(TypeFilter.ADDRESS);
        autocompleteSupportFragmentStart.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.NAME));
        autocompleteSupportFragmentStart.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
//                Log.i("AddTrip", "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });



        trip = findViewById(R.id.trip);
        List<String> listTrip = new ArrayList<>();
        listTrip.add("One way Trip");
        listTrip.add("Round Trip");
        ArrayAdapter<String> dataAdapterTrip = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listTrip);
        dataAdapterTrip.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trip.setAdapter(dataAdapterTrip);

        repeat = findViewById(R.id.repeat);
        List<String> listRepeat = new ArrayList<>();
        listRepeat.add("No Repeated");
        listRepeat.add("Daily");
        listRepeat.add("Weekly");
        listRepeat.add("Monthly");
        ArrayAdapter<String> dataAdapterRepeat = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listRepeat);
        dataAdapterRepeat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeat.setAdapter(dataAdapterRepeat);

        calendar.setOnClickListener(v -> {
            c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);
            dpd = new DatePickerDialog(AddTrip.this, (view, cYear, cMonth, dayOfMonth) -> txtDate.setText(dayOfMonth + "/" + (cMonth +1) + "/" + cYear), day,month,year);
            dpd.show();

        });

        clock = findViewById(R.id.clock);
        clock.setOnClickListener(v -> {
            @SuppressLint("SetTextI18n") TimePickerDialog.OnTimeSetListener onTimeSetListener = (view, hourOfDay, minute) -> {
                hour = hourOfDay;
                minut = minute;
                txtTime.setText(hour + ":" + minut);
            };
            tpd = new TimePickerDialog(AddTrip.this , onTimeSetListener , hour ,minut ,true);
            tpd.show();
        });

        btn = findViewById(R.id.addTrip);
        btn.setOnClickListener(v -> {

        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(Objects.requireNonNull(data));
                System.out.println("Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                assert data != null;
                Status status = Autocomplete.getStatusFromIntent(data);
                System.out.println( status.getStatusMessage());
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
