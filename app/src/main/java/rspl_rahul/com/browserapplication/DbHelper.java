package rspl_rahul.com.browserapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by rspl-aman on 12/10/17.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Db";
    private static final int DATABASE_VERSION = 1;
    private static String DB_PATH = "/data/data/madhaviproject.com.browserapplication/databases/";
    private static volatile DbHelper instance;
    private final Context mycontext;
    private SQLiteDatabase myDataBase;

    public DbHelper(Context context) {

        super(context, DB_PATH + DATABASE_NAME, null, DATABASE_VERSION);
        this.mycontext = context;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
        //This is the change

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS browser(Id NVARCHAR(30) ,Name NVARCHAR(30),Website NVARCHAR(30),PRIMARY KEY(Id ASC))");
    }

    public void insertNew(String id, String website, String name) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Id", id);
            contentValues.put("Name", name);
            contentValues.put("Website", website);
            db.insert("browser", null, contentValues);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Info> getId(String id) {

        ArrayList<Info> information = new ArrayList<Info>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] params = new String[1];
        params[0] = id + "%";
        Cursor cursor = db.rawQuery("select * from browser  where"
                + " Id like ? ", params);
        if (cursor.moveToFirst()) {
            do {
                Info pm = new Info();
                pm.setName(cursor.getString(cursor.getColumnIndex("Name")));
                pm.setWebsite(cursor.getString(cursor.getColumnIndex("Website")));
                information.add(pm);

            } while (cursor.moveToNext());
        }
        return information;


    }

    public ArrayList<Info> getAll() {

        ArrayList<Info> information = new ArrayList<Info>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from browser", null);

        if (cursor.moveToFirst()) {
            do {
                Info pm = new Info();
                pm.setName(cursor.getString(cursor.getColumnIndex("Name")));
                pm.setWebsite(cursor.getString(cursor.getColumnIndex("Website")));
                pm.setId(cursor.getString(cursor.getColumnIndex("Id")));
                information.add(pm);

            } while (cursor.moveToNext());
        }
        return information;


    }

    public boolean renameTab(String id, String name, String website) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("Name", name);
        contentValues.put("Website", website);

        int sqlUpdateRetval = db.update("browser", contentValues, "Id = ? "
                , new String[]{id.toString()});

        if (sqlUpdateRetval < 1) {
            return false;
        }
        Log.e("Database Operation", "row inserted...");
        db.close(); // Closing database connection
        return true;
    }

}
