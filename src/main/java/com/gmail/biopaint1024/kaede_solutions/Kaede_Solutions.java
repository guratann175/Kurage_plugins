package com.gmail.biopaint1024.kaede_solutions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public final class Kaede_Solutions extends JavaPlugin {

    private final Command commands = new Command();

    private Connection connection;
    public String host, database, username, password, user_list, user_log;
    public int port;

    @Override
    public void onEnable() {

        // Plugin startup logic
        getServer().getConsoleSender().sendMessage("\n\n" +
                ChatColor.WHITE + "-------------------------------------------------------------\n\n" +
                ChatColor.AQUA + "Kaede Solutions\n" +
                ChatColor.GOLD + "Main plugin\n" +
                ChatColor.GREEN + "start!!\n\n" +
                ChatColor.WHITE + "-------------------------------------------------------------\n\n" +
                "\n");

        //イベントクラスの読み込み
        getServer().getPluginManager().registerEvents(new GatyaEvent(), this);
        getServer().getPluginManager().registerEvents(new MessageEvent(), this);
        getServer().getPluginManager().registerEvents(new ItemUseSystem(), this);
        getServer().getPluginManager().registerEvents(new ScoreBoard(), this);
        getServer().getPluginManager().registerEvents(new ServerBook(), this);

        // コマンドの設定
        Objects.requireNonNull(getCommand(commands.cmd1)).setExecutor(commands);
        Objects.requireNonNull(getCommand(commands.cmd2)).setExecutor(commands);
        Objects.requireNonNull(getCommand(commands.cmd3)).setExecutor(commands);

        loadConfig();
        mysqlSetup();

        this.getServer().getPluginManager().registerEvents(new MysqlRecord(), this);

    }

    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    // SQL使用のためのセットアップ---------------------------------------------------------
    public void mysqlSetup() {
        host = this.getConfig().getString("host");
        port = this.getConfig().getInt("port");
        database = this.getConfig().getString("database");
        username = this.getConfig().getString("username");
        password = this.getConfig().getString("password");
        user_list = this.getConfig().getString("user_list");
        user_log = this.getConfig().getString("user_log");

        try {

            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed()) {
                    return;
                }

                Class.forName("com.mysql.jdbc.Driver");
                setConnection(
                        DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
                                this.username, this.password));

                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL CONNECTED");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    // サーバーを閉じるときの処理------------------------------------------------------------------
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "\n\nplugin stop!!\n\n");
    }
}
