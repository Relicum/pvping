package com.relicum.duel.Tasks;

import com.relicum.titleapi.Exception.ReflectionException;
import com.relicum.titleapi.TitleMaker;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * ActionTimer
 *
 * @author Relicum
 * @version 0.0.1
 */
public class ActionTimer extends BukkitRunnable {

    private TitleMaker titleMaker;

    private List<Player> players;


    private int length;
    private int count = 0;
    private String[] mess;

    public ActionTimer(List<Player> players, int length, TitleMaker titleMaker) {
        this.titleMaker = titleMaker;
        this.length = length;
        this.mess = new String[length];
        this.players = new ArrayList<>();
        this.players.addAll(players);
        makeMessage();

    }

    @Override
    public void run() {

        if (count == length) {

            players.stream().forEach(player -> {
                player.playSound(player.getEyeLocation(), Sound.ENDERDRAGON_GROWL, 10.0f, 1.0f);
                try {
                    titleMaker.sendResetPacket(player);
                    titleMaker.sendTitlePacket(player, "&a&lLETS FIGHT", 0, 60, 20);
                }
                catch (ReflectionException e) {
                    e.printStackTrace();
                }

            });

            cancel();
        }
        else {

            players.stream().forEach(player -> {
                player.playSound(player.getEyeLocation(), Sound.NOTE_PLING, 10.0f, 1.0f);
                try {
                    titleMaker.sendActionBar(player, mess[count]);
                }
                catch (ReflectionException e) {
                    e.printStackTrace();
                }

            });
        }
        count++;

    }

    private void makeMessage() {

        for (int i = 0; i < length; i++) {

            String tmp = "&f&lStarting in ";

            for (int j = 1; j < length; j++) {

                if (j > i) {
                    String RED = "&4&l\u2B1B";
                    tmp += RED;
                }
                else {
                    String GREEN = "&a&l\u2B1B";
                    tmp += GREEN;
                }
            }

            String SUFFIX = " &f&l in NN seconds";
            tmp += SUFFIX.replaceAll("NN", String.valueOf((length - i)));
            mess[i] = tmp;
        }

    }
}
