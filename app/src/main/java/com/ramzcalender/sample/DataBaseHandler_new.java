package com.ramzcalender.sample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataBaseHandler_new extends SQLiteOpenHelper {
    //database name initialising
    public static final String DATABASE_NAME = "crm_table.db";
    //databse version initialisng
    public static final String DATABASE_VERSION = "1";
    public static final String TABLE_NAME = "crm_table";

//creating ID's/columns to table
    public static final String ID = "SLNO";
    public static final String TASK = "TASK";
    public static final String DATE = "DATE";
    public static final String TIME = "TIME";
    public static final String NOTIFICATION= "NOTIFICATION";
    public static final String ALERT= "ALERT";
    public static final String PHONENUMBER= "PHONENUMBER";
    public static final String NAME= "NAME";
    public static SQLiteDatabase db = null;

    //creating table
   private String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY , " +
            TASK + " TEXT ," + DATE + " TEXT ," + TIME + " TEXT ,"+ NOTIFICATION + " TEXT ,"+ ALERT + " TEXT ,"+ PHONENUMBER + " TEXT ," + NAME + " TEXT " + ");";


//default constructor
    public DataBaseHandler_new(Context context) {
        super(context, DATABASE_NAME, null, 1);

      //  mcontext = context;
      //  DATABASE_PATH = "/data/data/" + mcontext.getPackageName() + "/databases/";

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
     //   db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,PASSWORD TEXT,MOBILENO TEXT,LOGIN_TYPE TEXT)");

        db.execSQL(CREATE_TABLE);
        Log.d("TAG","DATABASE TABLE1 CREATED");

    }
//updating the database to new version if old version exist
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


//inserting data into table
   public  boolean insertData(String slno, String task, String datee, String timee, String noti, String alert, String phno,String nam) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(ID, slno);
        contentValues1.put(TASK, task);
        contentValues1.put(DATE, datee);
         contentValues1.put(TIME, timee);
       contentValues1.put(NOTIFICATION, noti);
       contentValues1.put(ALERT, alert);
       contentValues1.put(PHONENUMBER, phno);
       contentValues1.put(NAME, nam);
     //  long result = db.insertWithOnConflict(TABLE_NAME, null, contentValues1,SQLiteDatabase.CONFLICT_REPLACE);
        boolean result = db.insert(TABLE_NAME, null, contentValues1)>0;
       return  result;
        /*if (result == -1)
            return false;
        else
            return true;*/
    }




//getting number of rows from table
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db,TABLE_NAME);
        return numRows;
    }


//getting data basedon username
    public Cursor getData(String user) {
        user="'"+user+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where USERNAME="+user, null );
        if (res != null)
            res.moveToFirst();

        return res;
    }

    public Cursor getDataa(int id) {
        id= Integer.parseInt("'"+id+"'");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where ID="+id, null );
        if (res != null)
            res.moveToFirst();

        return res;
    }


    //getting data based on logintype
    public Cursor getData1(String mobil) {
        mobil="'"+mobil+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where LOGIN_TYPE="+mobil, null );
        if (res != null)
            res.moveToFirst();

        return res;
    }

    public Cursor getDatalogintype(String type) {
        type="'"+type+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where LOGIN_TYPE="+type, null );
        if (res != null)
            res.moveToFirst();

        return res;
    }



//updating database
    /*public boolean updateData(String name, String pasword, String mobilenumber, String usertype) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
       // contentValues.put(COL_1, id);
        contentValues.put(USERNAME, name);
        contentValues.put(PASSWORD, pasword);
        contentValues.put(MOBILE_NUMBER, mobilenumber);
        contentValues.put(LOGIN_TYPE, usertype);
     //   long result1 = db.insertWithOnConflict(TABLE_NAME, null, contentValues,SQLiteDatabase.CONFLICT_REPLACE);
        db.update(TABLE_NAME, contentValues, "LOGIN_TYPE = ?", new String[]{usertype});
        return true;
    }
*/
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }




    public static String getSinlgeEntry(String userName)
    {
        Cursor cursor=db.query("kaapi_table", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }
    public static String getSinlgeEntry1(String id)
    {
        Cursor cursor=db.query("kaapi_table", null, " PASSWORD=?", new String[]{id}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String unam= cursor.getString(cursor.getColumnIndex("ID"));
        cursor.close();
        return unam;
    }







}