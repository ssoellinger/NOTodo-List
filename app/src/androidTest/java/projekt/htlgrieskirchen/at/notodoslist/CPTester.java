package projekt.htlgrieskirchen.at.notodoslist;

import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

/**
 * Created by ssoellinger on 07.05.2015.
 */
public class CPTester extends ProviderTestCase2<TodoContentProvider> {

    private static MockContentResolver resolve;

    /**
     * Constructor.
     *
     * @param providerClass     The class name of the provider under test
     * @param providerAuthority The provider's authority string
     */
    public CPTester(Class<TodoContentProvider> providerClass, String providerAuthority) {
        super(providerClass, providerAuthority);
    }

    public CPTester()
    {
        super(TodoContentProvider.class, "projekt.htlgrieskrichen.at.notodoslist.TodoContentProvider");
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        resolve = this.getMockContentResolver();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testen()
    {
        String [] projection = new String[] {TodosTbl.Title, TodosTbl.Description,TodosTbl.Priority,TodosTbl.Deadline};
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;

        //mit richtiger URI
        Cursor result = resolve.query(Uri.parse("content://projekt.htlgrieskirchen.at.notodoslist.TodoContentProvider/todos"), projection, selection, selectionArgs, sortOrder);
        assertNotNull(result);


    }
}
