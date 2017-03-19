package com.bok.gpacomputer.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;
import android.util.Log;

import com.bok.gpacomputer.entity.TranscriptLine;

import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by JerichoJohn on 3/18/2017.
 */

@RunWith(AndroidJUnit4.class)
public class GpaContentProviderTest extends ProviderTestCase2<GpaContentProvider> {

    SqlHelper helper;

    // Contains a reference to the mocked content resolver for the provider under test.
    private MockContentResolver mMockResolver;

    // Contains an SQLite database, used as test data
    private SQLiteDatabase db;

    public GpaContentProviderTest() {
        super(GpaContentProvider.class, GpaContentContract.AUTH);
        helper= new SqlHelper(getMockContext());
    }


    // Contains the test data, as an array of NoteInfo instances.
    private final TranscriptLine[] TRANSCRIPT_LINE = {
            new TranscriptLine("EN 11", "Communication Across the Curriculum I", "B", 3.0),
            new TranscriptLine("EN 13", "Introduction to Fiction", "C+", 3.0),
            new TranscriptLine("FIL 10", "Filipino For Beginners", "S", 1.0),
            new TranscriptLine("PS 1", "Introductory Physics I, Lecture", "B+", 3.0),
            new TranscriptLine("PS 2", "Introductory Physics I, Laboratory", "B", 1.0),
            new TranscriptLine("MA 11", "Modern Mathematics I", "B", 3.0),
            new TranscriptLine("CMT 11", "Citizens Military Training", "B", 1.5),
            new TranscriptLine("PE 112", "Basketball", "B+", 2.0)
    };

    @Before
    @Override
    public void setUp() throws Exception {
        // Calls the base class implementation of this method.
        setContext(InstrumentationRegistry.getTargetContext());

        super.setUp();
        // Gets the resolver for this test.
        mMockResolver = getMockContentResolver();

    }


    /*
     *  This method is called after each test method, to clean up the current fixture. Since
     *  this sample test case runs in an isolated context, no cleanup is necessary.
     */
    @After
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /*
     * Sets up test data.
     * The test data is in an SQL database. It is created in setUp() without any data,
     * and populated in insertData if necessary.
     */
    private void insertData() {

        // Sets up test data
        insertData(null);
    }

    private void insertData(Integer dataToInsert) {
        int maxData = TRANSCRIPT_LINE.length;
        if (dataToInsert != null && dataToInsert < TRANSCRIPT_LINE.length) {
            maxData = dataToInsert;
        }

        for (int index = 0; index < maxData; index++) {
            // Set the creation and modification date for the note
            // Adds a record to the database.
            getMockContentResolver().insert(GpaContentContract.TranscriptLine.CONTENT_URI, TRANSCRIPT_LINE[index].toContentValues());
        }
    }

    @Test
    public void testInsert() {
        Uri uri = GpaContentContract.TranscriptLine.CONTENT_URI;

        try (Cursor cursor = getMockContentResolver().query(uri, TranscriptLine.ALL_COLUMNS, null, null, null)) {
            assertThat(cursor, is(notNullValue()));

            //no entries expected yet
            assertThat(cursor.moveToFirst(), is(false));
            assertThat(cursor.moveToNext(), is(false));
        } catch (Exception e) {
            fail("No exception expected");
        }


        insertData(1); //insert 1 test data

        try (Cursor cursor = getMockContentResolver().query(uri, TranscriptLine.ALL_COLUMNS, null, null, null)) {
            assertThat(cursor, is(notNullValue()));

            //no entries expected yet
            int index = 0;
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    index++;
                    //new TranscriptLine("EN 11", "Communication Across the Curriculum I", "B", 3.0),
                    assertThat(cursor.getLong(0), IsInstanceOf.instanceOf(Long.class));
                    assertThat(cursor.getLong(0), is(greaterThan(0L)));
                    assertThat(cursor.getString(1), is("EN 11"));
                    assertThat(cursor.getString(2), is("Communication Across the Curriculum I"));
                    assertThat(cursor.getString(3), is("B"));
                    assertThat(cursor.getDouble(4), is(3.0));
                } while( cursor.moveToNext());
            }

            assertThat(index, is(1));


        } catch (Exception e) {
            fail("No exception expected");
        }

    }

    @Test
    public void testQuery1() {

        insertData();
        Uri uri = GpaContentContract.TranscriptLine.CONTENT_URI;

        try (Cursor cursor = getMockContentResolver().query(uri, TranscriptLine.ALL_COLUMNS, null, null, null)) {
            assertThat(cursor, is(notNullValue()));

            int index = 0;
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    index++;
                } while( cursor.moveToNext());
            }

            assertThat(index, is(TRANSCRIPT_LINE.length));

        } catch (Exception e) {
            fail("No exception expected");
        }
    }

    @Test
    public void testQuery2() {

        insertData(4);
        Uri uri = GpaContentContract.TranscriptLine.CONTENT_URI;

        try (Cursor cursor = getMockContentResolver().query(uri, TranscriptLine.ALL_COLUMNS, null, null, null)) {
            assertThat(cursor, is(notNullValue()));

            int index = 0;
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    index++;
                } while( cursor.moveToNext());
            }

            assertThat(index, is(4));

        } catch (Exception e) {
            fail("No exception expected");
        }
    }


}
