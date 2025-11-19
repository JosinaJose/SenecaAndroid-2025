package com.bookInfo.assignment_01;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class BookData extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;

    private TextView titleLabel, authorLabel, dateLabel;
    private ImageView bookImageView;
    private Button takePhotoButton, nextButton;
    private Books book;
    private Bitmap photoBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_data);

        // Initialize views
        titleLabel = findViewById(R.id.titleLabel);
        authorLabel = findViewById(R.id.authorLabel);
        dateLabel = findViewById(R.id.dateLabel);
        bookImageView = findViewById(R.id.bookImageView);
        takePhotoButton = findViewById(R.id.takePhotoButton);
        nextButton = findViewById(R.id.nextButton);

        // Get the Books object from the intent
        if (android.os.Build.VERSION.SDK_INT >= 33) {
            book = getIntent().getParcelableExtra("book", Books.class);
        } else {
            book = getIntent().getParcelableExtra("book");
        }

        if (book != null) {
            // Display the book information
            titleLabel.setText("Title of the book: " + book.getTitle());
            authorLabel.setText("Author of the book: " + book.getAuthor());
            dateLabel.setText("Book Details " + book.getDate());
        } else {
            Toast.makeText(this, "Error: No book data received", Toast.LENGTH_SHORT).show();
        }

        // Take photo button click listener
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraPermissionAndTakePhoto();
            }
        });

       // Next button click listener
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to third activity
                Intent intent = new Intent(BookData.this, Email_Web.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });
    }

    // Check camera permission before taking photo
    private void checkCameraPermissionAndTakePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            // Permission already granted, launch camera
            captureImage();
        }
    }

    // Handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, launch camera
                captureImage();
            } else {
                // Permission denied
                Toast.makeText(this, "Camera permission is required to take photos",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    // Launch camera intent
    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Check if there's a camera app available
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "No camera app available", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle camera result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                if (imageBitmap != null) {
                    // Display the photo in ImageView
                    bookImageView.setImageBitmap(imageBitmap);
                    bookImageView.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Photo captured successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Photo capture cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle","BookData Activity - on Resume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle","BookData Activity - on Destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle","BookData Activity - on Pause");
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (photoBitmap != null) {
            outState.putParcelable("photo", photoBitmap);
        }
        Log.d("lifecycle", "BookData Activity - Data Saved");
    }
}