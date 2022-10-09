package com.gmail.biopaint1024.kaede_solutions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnect {

    private Connection connection;
    public String host, database, username, password, table;
    public int port;

    public void mysqlSetup(){
        host = "localhost";
        port = 3306;
        database = "Kaede_information";
        username = "user";
        password = "root";

        try{
            synchronized (this){
                if(getConnection() != null && !getConnection().isClosed()){
                    return;
                }

                Class.forName("com.mysql.jdbc.Driver");
                // MySQLへ接続
                setConnection( DriverManager.getConnection("jdbc:mysql://" + this.host + ":"
                        + this.port + "/" + this.database, this.username, this.password));

                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL CONNECTED");
            }
        }catch(SQLException | ClassNotFoundException e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MYSQL UNCONNECTED");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
