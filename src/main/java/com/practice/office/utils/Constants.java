package com.practice.office.utils;

public class Constants {
    public static String SELECT_SQL_QUERY_PATH = "sql/select.sql";

    public static String INSERT_INTO_CLIENTS_SQL = "INSERT INTO clients VALUES(uuid_in(?), ?, ?, ?, ?, ?);";
    public static String INSERT_INTO_REALTIES_SQL = "INSERT INTO realties VALUES(uuid_in(?), ?, ?, ?, ?, ?, ?);";
    public static String INSERT_INTO_REQUESTS_SQL = "INSERT INTO requests VALUES(uuid_in(?), ?, ?, ?, ?);";
    public static String INSERT_INTO_DEALS_SQL = "INSERT INTO deals VALUES(uuid_in(?), ?, ?, ?, ?);";

    public static String UPDATE_CLIENTS_SQL = "UPDATE clients SET name = ?, surname = ?, fathername = ?," +
            " phone = ?, email = ? WHERE id = uuid_in(?);";
    public static String UPDATE_REALTIES_SQL = "UPDATE realties SET neighbourhood = ?, address = ?, square = ?," +
            " room_number = ?, price = ?, cadastral_number = ? WHERE id = uuid_in(?);";
    public static String UPDATE_REQUESTS_SQL = "UPDATE requests SET purpose = ?, client = uuid_in(?)," +
            " realty = uuid_in(?), dm = ? WHERE id = uuid_in(?);";
    public static String UPDATE_DEALS_SQL = "UPDATE deals SET seller = uuid_in(?), buyer = uuid_in(?)," +
            " realty = uuid_in(?), dm = ? WHERE id = uuid_in(?);";

    public static String DELETE_FROM_SQL = "DELETE FROM :table WHERE id = uuid_in(?);";
    public static String TABLE = ":table";
    public static String QUESTION = "?";
    public static String DASH = "-";

    public static String TIMESTAMP_PATTERN = "yyyy MM dd";
}
