package google.codeforindia.haiyya;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Srujan Jha on 17-01-2015.
 */
public class PContactsDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Haiyya-PS";
    private static final String TABLE_TASKS = "Contacts";
    private static final String KEY_ID = "_id";
    private static final String NAME = "Name";
    private static final String NUMBER = "Number";
    private static final String LAT = "LAT";
    private static final String LON = "LON";
    public PContactsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME+ " TEXT, "
                + NUMBER+ " TEXT,"
                + LAT + " INTEGER,"
                + LON + " INTEGER);";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        // Create tables again
        onCreate(db);
    }
    public void addContact(Contact task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, task.Name); // task name
        values.put(NUMBER, task.Number);
        values.put(LAT, task.Lat);
        values.put(LON, task.Lon);
        // Inserting Row
        db.insert(TABLE_TASKS, null, values);
        db.close(); // Closing database connection
    }
    public List<Contact> getAllTasks() {
        List<Contact> taskList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact task = new Contact();
                task.Id=(cursor.getInt(0));
                task.Name=(cursor.getString(1));
                task.Number=(cursor.getString(2));
                task.Lat=(cursor.getDouble(3));
                task.Lon=(cursor.getDouble(4));

                taskList.add(task);
            } while (cursor.moveToNext());
        }
        // return task list
        return taskList;
    }
    public void updateTask(Contact task) {
        // updating row
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, task.Name);
        values.put(NUMBER, task.Number);
        values.put(LAT, task.Lat);
        values.put(LON, task.Lon);
        db.update(TABLE_TASKS, values, KEY_ID + " = ?",new String[]{String.valueOf(task.Id)});
        db.close();
    }
    public void deleteTask(Contact task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?",new String[]{String.valueOf(task.Id)});
        db.close();
    }

}