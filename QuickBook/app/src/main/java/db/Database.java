package db;

import android.provider.BaseColumns;

/**
 * Created by tankul on 13/04/17.
 */

public class Database {
    public static final String DB_NAME = "QuickbookDB";
    public static final int DB_VERSION = 1;

    public class Book implements BaseColumns {
        public static final String TABLE = "Book";

        public static final String PAGE_CONTENTS = "Contents";
        public static final String TEXT_TYPE = "Zero";
    }
}
