package info.upump.questionnairematros.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import android.util.Log;



/**
 * Created by Сергей on 17.08.2017.
 */

public class DB {
    public static final int DATA_BASE_VERSION = 1;
    public static final String DATA_BASE_NAME = "questionnaire";
    public static final String TABLE_MAIN_QUESTION = "QUESTION";
    public static final String TABLE_SUB_MENU = "ANSWERS";

    public static final String TABLE_KEY_ID = "_id";
    public static final String TABLE_KEY_BODY = "body";
    public static final String TABLE_KEY_IMG = "img";
    public static final String TABLE_KEY_CATEGORY = "category";
    public static final String TABLE_KEY_COMMENT = "comment";
 //   public static final String TABLE_KEY_PARENTID = "parentId";
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;
    private final Context mCtx;
    private static final String CREATE_QUESTION_TABLE =
            "create table " + TABLE_MAIN_QUESTION +
                    "(" +
                    TABLE_KEY_ID + " integer primary key autoincrement, " +
                    TABLE_KEY_BODY + " text, " +
                    TABLE_KEY_CATEGORY +" text," +
                    TABLE_KEY_IMG+ " text," +
                    TABLE_KEY_COMMENT +" text)";


  /*  private static final String CREATE_SUB_MENU_TABLE =
            "create table " + TABLE_SUB_MENU +
                    "(" + TABLE_KEY_ID + " integer primary key autoincrement, " +
                    TABLE_KEY_NAME + " text," + TABLE_KEY_PARENTID + " integer)";
    private static final String CREATE_TABLE_DESCRIPTION =
            "create table " + TABLE_DESCRIPTION +
                    "(" + TABLE_KEY_ID + " integer primary key autoincrement, " +
                    TABLE_KEY_NAME + " text)";*/


    public DB(Context mCtx) {
        this.mCtx = mCtx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        Log.d("OPEN", "OPEN");
        mDB = mDBHelper.getWritableDatabase("123");
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

    // получить все данные из таблицы DB_TABLE
    public Cursor getAllData() {
        Log.d("DB", "getAll");
        return mDB.query(TABLE_MAIN_QUESTION, null, null, null, null, null, null);
    }

    public Cursor getAllData(String table) {
        Log.d("DB", "getAll");
        return mDB.query(table, null, null, null, null, null, null);
    }

    public void insert(String table, ContentValues contentValues){
        Log.d("DB", "insert");
        mDB.insert(table,null, contentValues );
    }

    // удалить запись из DB_TABLE
    public void delRec(long id) {
        mDB.delete(TABLE_MAIN_QUESTION, TABLE_KEY_ID + " = " + id, null);
    }

/*   public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MAIN_MENU_TABLE);
     //   db.execSQL("create table " + TABLE_SUB_MENU + "(" + TABLE_KEY_ID
            //    + " integer primary key," + TABLE_KEY_NAME + " text," + TABLE_KEY_PARENTID + " integer" + ")");
       // db.execSQL("create table " + TABLE_DESCRIPTION + "(" + TABLE_KEY_ID
//                + " integer primary key," + TABLE_KEY_NAME + " text," + TABLE_KEY_PARENTID + " integer" + ")");

    }*/



    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_QUESTION_TABLE);
                Log.d("CREATE_QUESTION_TABLE", "CREATE_QUESTION_TABLE");


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAIN_QUESTION);

            onCreate(db);
        }



    }
}
