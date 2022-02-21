package org.efecdml.simplenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {

    private EditText et_title;
    private EditText et_content;
    private FloatingActionButton fltBtn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        fltBtn_save = findViewById(R.id.fltBtn_save);

        fltBtn_save.setOnClickListener(new View.OnClickListener() {
            NotesModel notesModel;

            @Override
            public void onClick(View view) {
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy '-' HH:mm");
                String currentDateTime = simpleDateFormat.format(date);

                try {
                    notesModel = new NotesModel(-1, et_title.getText().toString(), et_content.getText().toString(), currentDateTime);
                } catch (Exception e) {
                    Toast.makeText(AddNoteActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

                DatabaseHelper databaseHelper = new DatabaseHelper(AddNoteActivity.this);
                databaseHelper.addNote(notesModel);
                finish();
            }
        });
    }
}