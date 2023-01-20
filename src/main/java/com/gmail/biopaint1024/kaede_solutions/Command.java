package com.gmail.biopaint1024.kaede_solutions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Command implements CommandExecutor {

    // ここには宣言でぎない
    // Kaede_Solutions plugin = Kaede_Solutions.getPlugin(Kaede_Solutions.class);

    public String cmd1 = "explode";
    public String cmd2 = "check";
    public String cmd3 = "gold";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command cmd,
                             @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            //プレイヤー限定のコマンド　入力したのがサーバーではなくプレイヤー
            Player player = (Player) sender;
            //コマンドを実行したプレイヤー
            if (cmd.getName().equalsIgnoreCase(cmd1)) {
                if (args.length != 0) {
                    // Bukkit.getPlayer(args[0]) サブコマンドに打ったプレイヤーを取得
                    Player target = Bukkit.getPlayer(args[0]);

                    assert target != null;
                    target.sendMessage(ChatColor.DARK_RED + "あなたは違法なプレイヤーです。");

                    // playerの座標を取得
                    Location loc = target.getLocation();
                    double x = loc.getX();
                    double y = loc.getY();
                    double z = loc.getZ();

                    player.sendMessage(ChatColor.AQUA + "座標: " +
                           ChatColor.WHITE + " X: " + x + " Y: " + y + " Z: " + z);

                    // 雷を落とすコマンド
                    // target.getWorld().spawnEntity(target.getLocation(), EntityType.LIGHTNING);

                }

                // 採掘数を表示するコマンド
            } else if (cmd.getName().equalsIgnoreCase(cmd2)) {

                Kaede_Solutions plugin = Kaede_Solutions.getPlugin(Kaede_Solutions.class);
                try {
                    String sql = "SELECT * FROM " + plugin.user_list + " WHERE UUID=" + "'" + player.getUniqueId() + "'";

                    Statement statement = plugin.getConnection().createStatement();
                    // SQLの実行
                    ResultSet resultset = statement.executeQuery(sql);
                    resultset.next();

                    player.sendMessage("あなたの掘った数 " + ChatColor.BLUE + resultset.getString("DIG_COUNT"));
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }

            } else if (cmd.getName().equalsIgnoreCase(cmd3)) {
                player.sendMessage("あなたの所持金 " + ChatColor.YELLOW + "0" + ChatColor.WHITE + " ゴールドです");
            }

            return false;
        }
        return false;
    }
}

