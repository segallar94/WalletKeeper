package cl.usm.inf.walletkeeper.db;

import android.provider.BaseColumns;

public final class WalletContract {
    public static final String DBNAME = "Wallet.db";
    private WalletContract() {}

    /* Inner class that defines the table contents */
    public static class AccountEntries implements BaseColumns {
        public static final String TABLE_NAME = "accountentries";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_CATEGORY = "cat";
    }

    public static class Categories implements BaseColumns {
        public static final String TABLE_NAME = "categories";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_RESOURCEID = "resourceid";
    }
}
