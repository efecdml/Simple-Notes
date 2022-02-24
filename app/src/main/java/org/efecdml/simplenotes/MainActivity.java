package org.efecdml.simplenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fltBtn_add;
    private RecyclerView rv_mainActivity;
    private TextView tv_emptyMessage;
    private RecyclerView.LayoutManager layoutManager;
    private List<NotesModel> notesModelList;
    private MainRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notes");

        fltBtn_add = findViewById(R.id.fltBtn_add);
        rv_mainActivity = findViewById(R.id.rv_mainActivity);
        tv_emptyMessage = findViewById(R.id.tv_emptyMessage);

        rv_mainActivity.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        fillNotesModelList();

        mAdapter = new MainRecyclerViewAdapter(notesModelList, this, MainActivity.this);
        rv_mainActivity.setAdapter(mAdapter);
        rv_mainActivity.setLayoutManager(layoutManager);

        fltBtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fillNotesModelList() {
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        notesModelList = databaseHelper.getNote();

        if (notesModelList.isEmpty()) {
            tv_emptyMessage.setVisibility(View.VISIBLE);
        } else {
            tv_emptyMessage.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        MenuItem sortItem = menu.findItem(R.id.action_sort);

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.subaction_alphabetical:
                //TODO Sort Recyclerview by alphabetical
                return true;
            case R.id.subaction_date:
                //TODO Sort Recyclerview by date created or last time modified
                return true;
            case R.id.action_deleteAll:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Delete all?");
                builder.setMessage("Are you sure you want to delete all the notes?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

                        databaseHelper.deleteEveryNote();
                        recreate();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}