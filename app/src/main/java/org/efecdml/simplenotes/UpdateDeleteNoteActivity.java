package org.efecdml.simplenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateDeleteNoteActivity extends AppCompatActivity {

    private EditText et_title;
    private EditText et_content;
    private FloatingActionButton fltBtn_update;
    private FloatingActionButton fltBtn_delete;
    private DatabaseHelper databaseHelper = new DatabaseHelper(UpdateDeleteNoteActivity.this);
    private NotesModel notesModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_note);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        fltBtn_update = findViewById(R.id.fltBtn_update);
        fltBtn_delete = findViewById(R.id.fltBtn_delete);

        getIntentData();
        fillWidgets();

        fltBtn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy '-' HH:mm");
                String currentDateTime = simpleDateFormat.format(date);
                notesModel.setCreatedOrModifiedDate(currentDateTime);
                notesModel.setTitle(et_title.getText().toString());
                notesModel.setContent(et_content.getText().toString());
                databaseHelper.updateNote(notesModel);
                finish();
            }
        });

        fltBtn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.deleteNote(notesModel);
                finish();
            }
        });
    }

    private void getIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("content") && getIntent().hasExtra("date")) {
            notesModel = new NotesModel(getIntent().getIntExtra("id", -1),
                    getIntent().getStringExtra("title"),
                    getIntent().getStringExtra("content"),
                    getIntent().getStringExtra("date"));
        } else {
            Toast.makeText(this, "Error fetching note", Toast.LENGTH_SHORT).show();
        }
    }

    private void fillWidgets(){
        et_title.setText(notesModel.getTitle());
        et_content.setText(notesModel.getContent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_update_delete_note, menu);
        return true;
    }
}