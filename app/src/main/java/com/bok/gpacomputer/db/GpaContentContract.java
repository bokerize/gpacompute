package com.bok.gpacomputer.db;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by JerichoJohn on 3/18/2017.
 */

public final class GpaContentContract {

    //authorify of the alarm
    public static final String AUTH = "com.bok.gpacomputer";

    //content URI for top-level
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTH);


    public static final class TranscriptLine {
        public static final String BASE = "transcriptLine";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(GpaContentContract.CONTENT_URI, BASE);
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/com.bok.gpacomputer_transcriptlines";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/com.bok.gpacomputer_transcriptlines";
        public static final String SORT_ORDER_DEFAULT = " asc";

    }
}
