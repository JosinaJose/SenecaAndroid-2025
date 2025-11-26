package com.bookInfo.assignment_01;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookListActivity extends AppCompatActivity {

    private RecyclerView recyclerBooks;
    private BookAdapter bookAdapter;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        recyclerBooks = findViewById(R.id.recyclerBooks);
        btnBack = findViewById(R.id.btnBack);

        // Set up RecyclerView
        recyclerBooks.setLayoutManager(new LinearLayoutManager(this));

        // Get books from BookManager
        List<Books> books = BookManager.getInstance().getAllBooks();

        if (books.isEmpty()) {
            Toast.makeText(this, "No books saved yet", Toast.LENGTH_SHORT).show();
        }

        // Set up adapter
        bookAdapter = new BookAdapter(books);
        recyclerBooks.setAdapter(bookAdapter);

        // Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}