package projekt.htlgrieskirchen.at.notodoslist;

/**
 * Created by droithmayr on 11.06.2015.
 */
public class TodosTbl {
    public static final String TABLE_NAME = "Todos";
    public static final String Todoid = "Todoid";
    public static final String Title = "Title";
    public final static String Description = "Description";
    public final static String Priority = "Priority";
    public final static String Deadline = "Deadline";
    public final static String Done ="Done";
    public static final int Wichtig = 0;
    public static final int Normal = 1;
    public static final int Keine = 2;

    public static final String[] ALL_COLUMNS = new String[] {Todoid + " AS _id", Title, Description, Priority, Deadline,Done};

    public static final String SQL_DROP = "DROP TABLE IF EXIST " + TABLE_NAME;
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME +
                    "(" +
                    Todoid + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    Title  + " TEXT NOT NULL," +
                    Description + " TEXT ," +
                    Priority    + " TEXT ," +
                    Deadline    + "  TEXT ," +
                    Done +" Boolean Not null"+
                    ")";

    public static final String STMT_DELETE = "DELETE FROM" + TABLE_NAME;
    public static final String SORT_ORDER= Deadline;
    public static final String STMT_INSERT =
            "INSERT INTO" + TABLE_NAME +
                    "(" + Title + "," + Description + "," + Priority + "," + Deadline +","+Done +")" +
                    "VALUES (?,?,?,?,?)";
}
