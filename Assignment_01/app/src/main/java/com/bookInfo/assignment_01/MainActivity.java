package com.bookInfo.assignment_01;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText titleText, authorText, dateText;
    private ImageButton dateDropdown;
    private Button nextButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
// Initialize views
        titleText = findViewById(R.id.titleText);
        authorText = findViewById(R.id.authorText);
        dateText = findViewById(R.id.dateText);
        dateDropdown = findViewById(R.id.dateDropdown);
        nextButton = findViewById(R.id.nextButton);

        // Date dropdown click listener
        dateDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMonthYearPicker();
            }
        });
        // Next button click listener
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleText.getText().toString().trim();
                String author = authorText.getText().toString().trim();
                String date = dateText.getText().toString().trim();

                // Validate inputs
                if (title.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter book title", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (author.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter author name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (date.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please select a date", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create Books object
                Books book = new Books(title, author, date);

                // Start BookData activity with book object
                Intent intent = new Intent(MainActivity.this, BookData.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    // show date picker- MM/YYYY and display on the text field
    public void showMonthYearPicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int dayOfMonth) {
                String formattedDate = String.format("%02d/%04d", selectedMonth + 1, selectedYear);
                dateText.setText(formattedDate);
            }
        };

        DatePickerDialog dpd = new DatePickerDialog(this, listener, year, month, 1);

        // Hide the day spinner
        try {
            java.lang.reflect.Field[] datePickerFields = dpd.getDatePicker().getClass().getDeclaredFields();
            for (java.lang.reflect.Field field : datePickerFields) {
                if (field.getName().equals("mDaySpinner") || field.getName().equals("mDayPicker")) {
                    field.setAccessible(true);
                    Object dayPicker = field.get(dpd.getDatePicker());
                    ((View) dayPicker).setVisibility(View.GONE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        dpd.show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle","Main Activity - on Resume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle","Main Activity - on Destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle","Main Activity - on Pause");
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putString("Book Title", titleText.getText().toString());
        outState.putString("Author", authorText.getText().toString());
        outState.putString("Date", dateText.getText().toString());
        super.onSaveInstanceState(outState);
        Log.d("lifecycle","Main Activity - in onSaveInstanceState");

    }
}