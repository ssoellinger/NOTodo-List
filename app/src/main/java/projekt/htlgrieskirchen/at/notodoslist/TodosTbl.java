package projekt.htlgrieskirchen.at.notodoslist;

/**
 * Created by droithmayr on 11.06.2015.
 */
public class TodosTbl {
    public static final String TABLE_NAME = "Todos";
    public final static String TodoId = "TodoId";
    public final static String Title = "Title";
    public final static String Description = "Description";
    public final static String Priority = "Priority";
    public final static String Deadline = "Deadline";


    public static final String[] ALL_COLUMNS = new String[] {TodoId + "AS_id", Title, Description, Priority, Deadline};

    public static final String SQL_DROP = "DROP TABLE IF EXIST" + TABLE_NAME;
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME +
                    "(" +
                    TodoId + "INTEGER PRIMARY KEY," +
                    Title  + "TEXT NOT NULL," +
                    Description + "TEXT NOT NULL," +
                    Priority    + "TEXT NOT NULL," +
                    Deadline    + "DATE NOT NULL," +
                    ")";

    public static final String STMT_DELETE = "DELETE FROM" + TABLE_NAME;
    public static final String STMT_INSERT =
            "INSERT INTO" + TABLE_NAME +
                    "(" + Title + "," + Description + "," + Priority + "," + Deadline + ")" +
                    "VALUES (?,?,?,?,?)";
}
