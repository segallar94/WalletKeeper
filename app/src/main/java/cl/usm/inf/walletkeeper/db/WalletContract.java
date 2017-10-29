package cl.usm.inf.walletkeeper.db;

import android.provider.BaseColumns;

public final class WalletContract {
    public static final String DBNAME = "Wallet.db";
    private WalletContract() {}

    /* Inner class that defines the table contents */
    public static class AccountEntries implements BaseColumns {
        public static final String _ID = "entryid";
        public static final String TABLE_NAME = "accountentries";
        public static final String NAME = "entryname";
        public static final String PRICE = "entryprice";
        public static final String CATEGORY_ID = "entrycatid";
        public static final String DATE = "entrydate";
    }

    public static class Categories implements BaseColumns {
        public static final String _ID = "catid";
        public static final String TABLE_NAME = "categories";
        public static final String NAME = "catname";
        public static final String RESOURCEID = "catresid";
    }
}
