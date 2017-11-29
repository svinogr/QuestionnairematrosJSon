package info.upump.questionnairematros.db;

import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;

/**
 * Created by explo on 26.09.2017.
 */

public class DBDAO {
    protected SQLiteDatabase database;
    private DataBaseHelper dataBaseHelper;
    private Context context;

    public DBDAO(Context context) {
        this.context = context;
        dataBaseHelper = DataBaseHelper.getHelper(context);
        open();
    }

    public void open() {
        if (dataBaseHelper == null)
            dataBaseHelper = DataBaseHelper.getHelper(context);
        database = dataBaseHelper.getWritableDatabase("123");

    }

    public void close() {
        dataBaseHelper.close();
        database = null;
    }
}
