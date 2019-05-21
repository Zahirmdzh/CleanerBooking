package sg.edu.rp.c300.cleanerbooking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "member.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "member";
    private static final String COL_ID = "_id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PHONE = "phone";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";




    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
           String createTable = "CREATE TABLE " + TABLE_NAME + "("
                   + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_USERNAME + " TEXT,"
                   + COL_PHONE + " TEXT,"
                   + COL_EMAIL + " TEXT,"
                   + COL_PASSWORD + " TEXT )";
                db.execSQL(createTable);
//        db.execSQL("DELETE FROM " + TABLE_NAME);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
    }

    public long registerUser(String user, String phone, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, user);
        values.put(COL_PHONE, phone);
        values.put(COL_EMAIL, email);
        values.put(COL_PASSWORD, password);

        long result = db.insert(TABLE_NAME,null,values);
        return result;
    }

    public boolean checkUser(String email, String password) {
        String[] columns = {COL_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_EMAIL + "= ?" + " and " + COL_PASSWORD + "= ?";
        String[] args = {email,password};
        Cursor cursor = db.query(TABLE_NAME,columns,selection,args,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count > 0) {
          return true;
        } else {
            return false;
        }
    }

    public int deleteMember(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COL_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_NAME, condition, args);
        db.close();
        return result;
    }



}
