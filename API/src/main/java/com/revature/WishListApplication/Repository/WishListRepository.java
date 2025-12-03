package com.revature.WishListApplication.Repository;

import java.sql.*;

public class WishListRepository{
    private static final String Postgre_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String Postgre_User = "postgres";
    private static final String Postgre_PW = "secret";
    private Connection connection;
    
    public WishListRepository() throws SQLException{
        try{
            connection = DriverManager.getConnection(Postgre_URL, Postgre_User, Postgre_PW);
            try (Statement stmt = connection.createStatement()) {
                String sql =
                    "CREATE TABLE User (\n" +
                    "    userID      SERIAL PRIMARY KEY,\n" +
                    "    username    VARCHAR(50) NOT NULL UNIQUE,\n" +
                    "    password    VARCHAR(50) NOT NULL\n" +
                    ");\n" +

                    "CREATE TABLE Wishlist (\n" +
                    "    wishlistID  SERIAL PRIMARY KEY,\n" +
                    "    userID      INT NOT NULL,\n" +
                    "    FOREIGN KEY (userID)\n" +
                    "        REFERENCES User(userID)\n" +
                    "        ON DELETE CASCADE\n" +
                    ");\n" +

                    "CREATE TABLE Brands (\n" +
                    "    brandID     SERIAL PRIMARY KEY,\n" +
                    "    name        VARCHAR(100) NOT NULL\n" +
                    ");\n" +

                    "CREATE TABLE Item (\n" +
                    "    itemID      SERIAL PRIMARY KEY,\n" +
                    "    name        VARCHAR(100) NOT NULL,\n" +
                    "    brandID     INT NOT NULL,\n" +
                    "    price       DECIMAL(10,2) NOT NULL,\n" +
                    "    FOREIGN KEY (brandID)\n" +
                    "        REFERENCES Brands(brandID)\n" +
                    "        ON DELETE SET NULL\n" +
                    ");\n" +

                    "CREATE TABLE Wishlist_Item (\n" +
                    "    wishlistID  INT NOT NULL,\n" +
                    "    itemID      INT NOT NULL,\n" +
                    "    PRIMARY KEY (wishlistID, itemID),\n" +
                    "    FOREIGN KEY (wishlistID)\n" +
                    "        REFERENCES Wishlist(wishlistID)\n" +
                    "        ON DELETE CASCADE,\n" +
                    "    FOREIGN KEY (itemID)\n" +
                    "        REFERENCES Item(itemID)\n" +
                    "        ON DELETE CASCADE\n" +
                    ");";
                stmt.execute(sql);
                System.out.println("Successful connection to PostgreSQl database");
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to PostgreSQL database");
        }
    }

}
