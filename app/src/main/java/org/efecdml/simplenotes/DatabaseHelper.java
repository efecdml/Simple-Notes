package org.efecdml.simplenotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String NOTES_TABLE = "NOTES_TABLE";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TITLE = "TITLE";
    private static final String COLUMN_CONTENT = "CONTENT";
    private static final String COLUMN_CREATED_OR_MODIFIED_DATE = "CREATED_OR_MODIFIED_DATE";
    private Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, NOTES_TABLE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + NOTES_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_CONTENT + " TEXT, " +
                COLUMN_CREATED_OR_MODIFIED_DATE + " TEXT)";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean addNote(NotesModel notesModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TITLE, notesModel.getTitle());
        contentValues.put(COLUMN_CONTENT, notesModel.getContent());
        contentValues.put(COLUMN_CREATED_OR_MODIFIED_DATE, notesModel.getCreatedOrModifiedDate());

        long addNoteResult = sqLiteDatabase.insert(NOTES_TABLE, null, contentValues);
        if (addNoteResult == -1) {
            Toast.makeText(context, "Error adding note", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public List<NotesModel> getNote() {
        List<NotesModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + NOTES_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);
                String createdOrModifiedDate = cursor.getString(3);

                NotesModel newNotesModel = new NotesModel(id, title, content, createdOrModifiedDate);
                returnList.add(newNotesModel);
            } while (cursor.moveToNext());
        } else {
            // Failure, don't add anything to the list.
        }
        cursor.close();
        sqLiteDatabase.close();

        System.out.println("getNote");
        return returnList;
    }

    public boolean deleteNote(NotesModel notesModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String queryString = "DELETE FROM " + NOTES_TABLE + " WHERE " + COLUMN_ID + " = " + notesModel.getId();

        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            //Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            //Toast.makeText(context, "Error deleting note", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void deleteAllTheNotes() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + NOTES_TABLE);
    }

    public boolean updateNote(NotesModel notesModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, notesModel.getTitle());
        contentValues.put(COLUMN_CONTENT, notesModel.getContent());
        contentValues.put(COLUMN_CREATED_OR_MODIFIED_DATE, notesModel.getCreatedOrModifiedDate());
        long updateNoteResult = sqLiteDatabase.update(NOTES_TABLE, contentValues, "ID=?", new String[]{Integer.toString(notesModel.getId())});
        if (updateNoteResult == -1) {
            Toast.makeText(context, "Error updating note", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
