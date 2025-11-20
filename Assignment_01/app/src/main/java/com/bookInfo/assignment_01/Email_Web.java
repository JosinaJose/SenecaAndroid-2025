package com.bookInfo.assignment_01;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Email_Web extends AppCompatActivity {

    EditText editTextSubject, editTextContent, editTextToEmail, webSearch;
    Button button, searchBtn;
    private Books book;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_web);

        button = findViewById(R.id.sendEmail);
        editTextSubject = findViewById(R.id.subject);
        editTextContent = findViewById(R.id.content);
        editTextToEmail = findViewById(R.id.to_email);
        webSearch = findViewById(R.id.searchInput);
        searchBtn = findViewById(R.id.searchButton);

        book = getIntent().getParcelableExtra("book");

        if (book != null) {
            displayBookSummary();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = editTextSubject.getText().toString().trim();
                String content = editTextContent.getText().toString().trim();
                String to_email = editTextToEmail.getText().toString().trim();

                if (subject.isEmpty() || content.isEmpty() || to_email.isEmpty()) {
                    Toast.makeText(Email_Web.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    sendEmail(subject, content, to_email);
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = webSearch.getText().toString().trim();

                // If URL field is empty, search for the book
                if (url.isEmpty()) {


                        Toast.makeText(Email_Web.this, "Please enter a web address", Toast.LENGTH_SHORT).show();
                        return;
                    }

                 else {
                    // Open the custom URL entered by user
                    openWebPage(url);
                }
            }
        });
    }

    private void displayBookSummary() {
        String greeting = "Dear,\n\n";
        String emailIntro = "I would like to suggest the book:\n\n";

        String summary = greeting +
                emailIntro +
                "Book Information\n" +
                "----------------\n" +
                "Title: " + book.getTitle() + "\n" +
                "Author: " + book.getAuthor() + "\n" +
                "Published: " + book.getDate() + "\n\n" +
                "Thank you,\n" +
                "Josina Jose";

        editTextContent.setText(summary);
        editTextSubject.setText("Book Info: " + book.getTitle());
    }
    // Send email using ACTION_SENDTO
    public void sendEmail(String subject, String content, String to_email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{to_email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, content);
        startActivity((Intent.createChooser(emailIntent, "Choose email client")) );

    }

    // Open custom web page
    public void openWebPage(String url) {
        // Add "https://" automatically if missing
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }

        try {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(webIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Cannot open browser: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Email_Web", "Error opening browser", e);
        }
    }


    // Lifecycle logs (optional for assignment)
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle", "Email_Web Activity - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle", "Email_Web Activity - onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle", "Email_Web Activity - onDestroy");
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("subject", editTextSubject.getText().toString());
        outState.putString("content", editTextContent.getText().toString());
        outState.putString("to_email", editTextToEmail.getText().toString());
        outState.putString("web search", webSearch.getText().toString());
        super.onSaveInstanceState(outState);
    }

}
