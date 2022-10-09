package com.gmail.biopaint1024.kaede_solutions;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MysqlRecord implements Listener {

    Kaede_Solutions plugin = Kaede_Solutions.getPlugin(Kaede_Solutions.class);

    // PLayerが入場したときのイベント
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        createPlayer(player.getUniqueId(), player);
    }

    @EventHandler
    public void hit(PlayerInteractEvent event) {
        // updateCoins(event.getPlayer().getUniqueId());
        // getCoins(event.getPlayer().getUniqueId());
    }

    /*
    @EventHandler
    public void breakBlock(BlockBreakEvent event) {
        // Block block = event.getBlock();
        UUID uuid = event.getPlayer().getUniqueId();
        dig_log(uuid);
    }
    */

    // データベース内にプレイヤーが存在するか?
    public boolean playerExists(UUID uuid) {
        try {
            // PreparedStatement: sql文をデータベースに送信(すぐに送信するわけでなく、一旦は、変数に格納しておく)
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("SELECT * FROM " + plugin.user_list + " WHERE UUID=?");
            // setString: パラメータ1にuuid.toString()を追加する
            statement.setString(1, uuid.toString());

            //sql文を送信したときの結果を受け取る(executeQuery: 検索系 WHERE etc)
            ResultSet results = statement.executeQuery();
            if (results.next()) {
                plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Player Found");
                return true;
            }
            plugin.getServer().broadcastMessage(ChatColor.RED + "Player NOT Found");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 入場したプレイヤーをデータベースに追加
    public void createPlayer(final UUID uuid, Player player) {
        try {
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("SELECT * FROM " + plugin.user_list + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            System.out.print(1);
            // もしuuidと同じプレーヤーがデータベース内に存在しないなら、
            if (!playerExists(uuid)) {
                // sql文実行
                PreparedStatement insert = plugin.getConnection()
                        .prepareStatement("INSERT INTO " + plugin.user_list + " (NAME, UUID, DIG_COUNT) VALUES (?, ?, ?)");
                insert.setString(1, player.getName());
                insert.setString(2, uuid.toString());
                insert.setInt(3, 0);
                insert.executeUpdate();

                plugin.getServer().broadcastMessage(ChatColor.GREEN + "Player Inserted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
    public void dig_log(UUID uuid) {
        try {
            PreparedStatement statement = plugin.getConnection().prepareStatement(
                    "UPDATE " + plugin.user_list + " SET DIG_COUNT = DIG_COUNT + 1 WHERE UUID=?");
            statement.setString(1, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    */

    /*
    public void updateCoins(UUID uuid) {
        try {
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("UPDATE " + plugin.table + " SET COINS=? WHERE UUID=?");
            statement.setInt(1, 1000);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getCoins(UUID uuid) {
        try {
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("SELECT * FROM " + plugin.table + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();

            System.out.print(results.getInt("COINS"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */
}
