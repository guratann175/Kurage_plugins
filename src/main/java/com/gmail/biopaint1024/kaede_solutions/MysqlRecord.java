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
import java.sql.Statement;
import java.util.UUID;
/**
 * データベースの生成
 */

public class MysqlRecord implements Listener {

    Kaede_Solutions plugin = Kaede_Solutions.getPlugin(Kaede_Solutions.class);

    // PLayerが入場したときのイベント
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        createPlayer(player.getUniqueId(), player);
    }

    // データベース内にプレイヤーが存在するか?
    private boolean playerExists(UUID uuid) {
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

    // 入場したプレイヤーをデータベースに追加する
    public void createPlayer(final UUID uuid, Player player) {
        try {
            PreparedStatement statement = plugin.getConnection()
                    .prepareStatement("SELECT * FROM " + plugin.user_list + " WHERE UUID=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();

            // もしuuidと同じプレーヤーがデータベース内に存在しないなら、
            if (!playerExists(uuid)) {
                // sql文実行 (データベースに新たな行を追加する)
                PreparedStatement insert = plugin.getConnection()
                        .prepareStatement("INSERT INTO " + plugin.user_list + " (NAME, UUID, DIG_COUNT) VALUES (?, ?, ?)");
                // データベースにユーザー名を追加
                insert.setString(1, player.getName());
                // データベースにuuidを追加
                insert.setString(2, uuid.toString());
                // データベースに掘った数を追加
                insert.setInt(3, 0);
                insert.executeUpdate();

                plugin.getServer().broadcastMessage(ChatColor.GREEN + "Player Inserted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    /*ブロックを壊したときのイベント データベースの値を更新*/
    public void onBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        UUID uuid = event.getPlayer().getUniqueId();
        try {
            // 採掘したら、1を足す (データベースの値を更新する)
            PreparedStatement statement = plugin.getConnection().prepareStatement(
                    "UPDATE " + plugin.user_list + " SET DIG_COUNT = DIG_COUNT + 1 WHERE UUID=?");
            statement.setString(1, uuid.toString());
            statement.executeUpdate();

            // SQLからデータを取り出す
            String sql = "SELECT * FROM " + plugin.user_list + " WHERE UUID=" + "'" + player.getUniqueId() + "'";
            Statement statement2 = plugin.getConnection().createStatement();
            // SQLの実行
            ResultSet resultset = statement2.executeQuery(sql);
            resultset.next();

            /*
            player.sendMessage(ChatColor.GOLD + player.getName() +
                    ChatColor.WHITE + "は"
                    + ChatColor.GREEN + block.getType().toString().toUpperCase() +
                    ChatColor.WHITE + "を壊しました" );
            */

            // 壊したブロックについてのログを取る----------------------------------------------------------------------------------------
            String sql_log = "SELECT * FROM " + plugin.user_list + " WHERE UUID=" + "'" + player.getUniqueId() + "'";
            Statement statement_log = plugin.getConnection().createStatement();
            // SQLの実行
            ResultSet resultset_log = statement_log.executeQuery(sql_log);
            resultset_log.next();

            PreparedStatement insert = plugin.getConnection()
                    .prepareStatement("INSERT INTO " + plugin.user_log + " (USER_ID, BLOCK, DO) VALUES (?, ?, ?)");

            insert.setString(1, String.valueOf(resultset_log.getInt("id")));
            insert.setString(2, block.getType().toString().toUpperCase());
            insert.setInt(3, 0);
            insert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
