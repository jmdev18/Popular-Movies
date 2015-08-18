package com.franktan.popularmovies.integration;

import android.content.res.Resources;
import android.database.Cursor;
import android.test.InstrumentationTestCase;

import com.franktan.popularmovies.data.MovieContract;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tan on 16/08/2015.
 */
public class RetrieveAndSaveDataTest extends InstrumentationTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        System.setProperty(
                "dexmaker.dexcache",
                getInstrumentation().getTargetContext().getCacheDir().getPath());
    }

    //TODO Test Retrieving data from Real Movie DB API
//    public void testMovieDBAPIReturn () {
//        MovieDbAPISyncService movieDbAPISyncService = MovieDbAPISyncService.getDbSyncService();
//        String jsonReturn = movieDbAPISyncService.getMovieInfoFromAPI("popularity", 1435708800L);
//        assertNotNull("API should return something", jsonReturn);
//        assertFalse("Returned Json should not be an empty string", jsonReturn.equals(""));
//    }
//
//    //TODO Test retrieving fake json from a mock service, parsing it and save to DB
//    public void testParsingAndSaving () throws IOException {
//        deleteAllRecordsFromProvider();
//
//        MovieDbAPISyncService mockService = mock(MovieDbAPISyncService.class);
//        when(mockService.getMovieInfoFromAPI("popularity", 1435708800L)).thenReturn(getTestingMovieJson());
//        MovieSyncAdapter.syncMovieList(mockService);
//
//        Cursor movieCursor = getInstrumentation().getContext().getContentResolver().query(
//                MovieContract.MovieEntry.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//        assertEquals("Should be 20 movie record in database", movieCursor.getCount());
//        movieCursor.moveToFirst();
//        assertEquals("First record should match the first movie in our list",
//                102899, movieCursor.getInt(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIEDB_ID)));
//
//        movieCursor.moveToLast();
//        assertEquals("Last record should match the last movie in our list",
//                260346, movieCursor.getInt(movieCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIEDB_ID)));
//        movieCursor.close();
//    }

    public void deleteAllRecordsFromProvider() {
        getInstrumentation().getContext().getContentResolver().delete(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = getInstrumentation().getContext().getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Should be 0 record in table after delete", 0, cursor.getCount());
        cursor.close();

    }

    public String getTestingMovieJson() throws IOException {
        Resources res = getInstrumentation().getContext().getResources();
        InputStream in = res.getAssets().open("mock_movies.json");
        byte[] b  = new byte[(int) in.available()];
        int len = b.length;
        int total = 0;

        while (total < len) {
            int result = in.read(b, total, len - total);
            if (result == -1) {
                break;
            }
            total += result;
        }
        return new String(b);
    }
}