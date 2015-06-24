package projekt.htlgrieskirchen.at.notodoslist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by droithmayr on 11.06.2015.
 */
public class MyTodoOpenHelper extends SQLiteOpenHelper
{
    private final static String DB_NAME = "todo.db";
    private final static int DB_VERSRION = 1;

    public MyTodoOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSRION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TodosTbl.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TodosTbl.SQL_DROP);
        onCreate(db);
    }
}
