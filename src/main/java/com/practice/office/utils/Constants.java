package com.practice.office.utils;

public class Constants {
    public static String SELECT_SQL_QUERY_PATH = "sql/select.sql";

    public static String INSERT_INTO_CLIENTS_SQL = "INSERT INTO clients VALUES(?, ?, ?, ?, ?, ?);";
    public static String INSERT_INTO_REALTIES_SQL = "INSERT INTO realties VALUES(?, ?, ?, ?, ?, ?, ?);";
    public static String INSERT_INTO_REQUESTS_SQL = "INSERT INTO requests VALUES(?, ?, ?, ?, ?);";
    public static String INSERT_INTO_DEALS_SQL = "INSERT INTO deals VALUES(?, ?, ?, ?, ?);";

    public static String UPDATE_CLIENTS_SQL = "UPDATE clients SET name = ?, surname = ?, fathername = ?," +
            " phone = ?, email = ? WHERE id = ?;";
    public static String UPDATE_REALTIES_SQL = "UPDATE realties SET neighbourhood = ?, address = ?, square = ?," +
            " room_number = ?, price = ?, cadastral_number = ? WHERE id = ?;";
    public static String UPDATE_REQUESTS_SQL = "UPDATE requests SET purpose = ?, client = ?," +
            " realty = ?, dm = ? WHERE id = ?;";
    public static String UPDATE_DEALS_SQL = "UPDATE deals SET seller = ?, buyer = ?," +
            " realty = ?, dm = ? WHERE id = ?;";

    public static String DELETE_FROM_SQL = "DELETE FROM :table WHERE id = ?;";
    public static String TABLE = ":table";
    public static String QUESTION = "?";
}
