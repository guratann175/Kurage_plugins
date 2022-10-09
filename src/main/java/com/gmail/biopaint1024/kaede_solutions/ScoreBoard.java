package com.gmail.biopaint1024.kaede_solutions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class ScoreBoard implements Listener {

    Kaede_Solutions plugin = Kaede_Solutions.getPlugin(Kaede_Solutions.class);

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        /*
        try {
            String sql = "SELECT * FROM " + plugin.user_list + " WHERE UUID=" + "'" + player.getUniqueId() + "'";

            Statement statement = plugin.getConnection().createStatement();
            // SQLの実行
            ResultSet resultset = statement.executeQuery(sql);
            resultset.next();

            // player.sendMessage(resultset.getString("DIG_COUNT"));

            // スコアボードのセット
            ScoreboardManager m = Bukkit.getScoreboardManager();
            Scoreboard b = m.getNewScoreboard();

            Objective o = b.registerNewObjective("Gold", "");
            o.setDisplaySlot(DisplaySlot.SIDEBAR);
            o.setDisplayName(ChatColor.DARK_AQUA + "Kaede Solutions");

            // スコアボードに記入
            Score gold = o.getScore(ChatColor.WHITE + "掘った数 " + ChatColor.GOLD);
            gold.setScore(resultset.getInt("DIG_COUNT"));
            player.setScoreboard(b);


        } catch (SQLException e) {
            e.printStackTrace();
        }

         */
    }


    @EventHandler
    /*ブロックを壊したときのイベント*/
    public void onBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        UUID uuid = event.getPlayer().getUniqueId();
        try {

            // 採掘したら、1を足す
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
            // スコアボードへの反映
            ScoreboardManager m = Bukkit.getScoreboardManager();
            Scoreboard b = m.getNewScoreboard();

            Objective o = b.registerNewObjective("Gold", "");
            o.setDisplaySlot(DisplaySlot.SIDEBAR);
            o.setDisplayName(ChatColor.DARK_AQUA + "Kaede Solutions");

            Score gold = o.getScore(ChatColor.WHITE + "掘った数 " + ChatColor.GOLD);
            // スコアボードに記入
            gold.setScore(resultset.getInt("DIG_COUNT"));

            player.setScoreboard(b);
             */

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
