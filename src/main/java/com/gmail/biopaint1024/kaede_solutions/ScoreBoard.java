package com.gmail.biopaint1024.kaede_solutions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
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

    // プレイヤーがサーバーに入ったときのイベント
    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // スコアボードのセット
        ScoreboardManager m = Bukkit.getScoreboardManager();
        Scoreboard b = m.getNewScoreboard();
        Objective o = b.registerNewObjective("Gold", "");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(ChatColor.DARK_AQUA + "Kaede Solutions");

        try {
            //SQL文
            String sql = "SELECT * FROM " + plugin.user_list + " WHERE UUID=" + "'" + player.getUniqueId() + "'";

            Statement statement = plugin.getConnection().createStatement();

            // SQLの実行
            ResultSet resultset = statement.executeQuery(sql);
            resultset.next();

            // player.sendMessage(resultset.getString("DIG_COUNT"));

            // スコアボードのスコアをセット
            Score dig_score = o.getScore(ChatColor.WHITE + "掘った数 ");
            // データベースから"DIG_COUNT"カラムの値を取り出して、スコアボードに書き込む
            dig_score.setScore(resultset.getInt("DIG_COUNT"));

            // 以上の設定でスコアボードをセットする
            player.setScoreboard(b);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @EventHandler
    // ブロックを壊したときのイベント
    public void onBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        UUID uuid = event.getPlayer().getUniqueId();

        // スコアボードのセット
        ScoreboardManager m = Bukkit.getScoreboardManager();
        Scoreboard b = m.getNewScoreboard();
        Objective o = b.registerNewObjective("Gold", "");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(ChatColor.DARK_AQUA + "Kaede Solutions");

        try {
            //SQL文
            String sql = "SELECT * FROM " + plugin.user_list + " WHERE UUID=" + "'" + player.getUniqueId() + "'";

            Statement statement = plugin.getConnection().createStatement();

            // SQLの実行
            ResultSet resultset = statement.executeQuery(sql);
            resultset.next();

            // player.sendMessage(resultset.getString("DIG_COUNT"));

            // スコアボードのスコアをセット
            Score dig_score = o.getScore(ChatColor.WHITE + "掘った数 ");
            // データベースから"DIG_COUNT"カラムの値を取り出して、スコアボードに書き込む
            // (なぜか最初の一回がカウントされない?)
            dig_score.setScore(resultset.getInt("DIG_COUNT") + 1);

            // 以上の設定でスコアボードをセットする
            player.setScoreboard(b);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
