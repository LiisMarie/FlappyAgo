package com.flappyago.game.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMusic {
    public static float masterVolume;
    public static Music menuMusic;
    public static Music playMusic;
    public static Sound die;
    private static List<String> previousSongs;
    private static String chosenSong;
    private static Random random;

    public GameMusic() {
        random = new Random();
        previousSongs = new ArrayList<String>();
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("menumusic.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.5f);  // 1f is 100% volume

        playMusic = Gdx.audio.newMusic(Gdx.files.internal("music1.mp3"));
        playMusic.setLooping(true);
        masterVolume = 0.5f;
        playMusic.setVolume(masterVolume);  // 1f is 100% volume

        // Dying sound
        die = Gdx.audio.newSound(Gdx.files.internal("dying.ogg"));
    }

    public static void start() {
        chosenSong = randomizer();
        playMusic = Gdx.audio.newMusic(Gdx.files.internal("music" + chosenSong + ".mp3"));
        playMusic.setVolume(GameMusic.masterVolume);
        playMusic.play();
        switch (Integer.parseInt(chosenSong)) {
            case 0:
                System.out.println("'Taylor Swift - Bad Blood' started to play!");
                break;
            case 1:
                System.out.println("'Baby Got Athletic' started to play!");
                break;
            case 2:
                System.out.println("'Rick Astley - Never Gonna Give You Up' started to play!");
                break;
            case 3:
                System.out.println("'Selena Gomez & Charlie Puth - We Don't Talk Anymore' started to play!");
                break;
            case 4:
                System.out.println("'Bag Raiders - Shooting Stars' started to play!");
                break;
            case 5:
                System.out.println("'Ed Sheeran - Shape of You' - started to play!");
                break;
            case 6:
                System.out.println("'Käh' started to play!");
            default:
                System.out.println("Music started to play!");
                break;
        }
    }

    private static String randomizer() {
        chosenSong = String.valueOf(random.nextInt(7));
        while (previousSongs.contains(chosenSong)) {
            chosenSong = String.valueOf(random.nextInt(7));
        }
        previousSongs.add(chosenSong);
        if (previousSongs.size() > 3) previousSongs.remove(0);
        System.out.println(chosenSong);
        System.out.println(previousSongs);
        return chosenSong;
    }

    public static void setMasterVolume(float newVolume) {
        masterVolume = newVolume;
        menuMusic.setVolume(masterVolume);
        playMusic.setVolume(masterVolume);
    }

    public static Music getMenuMusic() {
        return menuMusic;
    }

    public static Music getPlayMusic() {
        return playMusic;
    }

    public static float getMasterVolume() {
        return masterVolume;
    }

    public void dispose() {
        menuMusic.dispose();
        playMusic.dispose();
    }
}
