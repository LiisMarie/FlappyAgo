package com.flappyago.game.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMusic {
    private static final int NUMBER_OF_SONGS = 10;
    private static float masterVolume;
    private static float soundVolume;
    private static Sound die;
    private static Music menuMusic;
    private static Music playMusic;
    private static List<String> previousSongs;
    private static String chosenSong;
    private static Random random;


    public GameMusic() {

        // Menu music.
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("menumusic.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.5f);  // 1f is 100% volume

        // In-game music necessities.
        random = new Random();
        previousSongs = new ArrayList<String>();

        playMusic = Gdx.audio.newMusic(Gdx.files.internal("music1.mp3"));
        playMusic.setLooping(true);
        masterVolume = 0.5f;
        playMusic.setVolume(masterVolume);  // 1f is 100% volume

        // Dying sound
        die = Gdx.audio.newSound(Gdx.files.internal("dying.ogg"));
        soundVolume = GameMusic.getMasterVolume();
        if (GameMusic.getMasterVolume() != 0) {  // set volume
            soundVolume = 1f;
        }
    }

    // Chooses a music to play, plays it and prints out the name of the song.
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
                System.out.println("'Ed Sheeran - Shape of You' started to play!");
                break;
            case 6:
                System.out.println("'Käh' started to play!");
                break;
            case 7:
                System.out.println("'Imagine Dragons - Radioactive' started to play!");
                break;
            case 8:
                System.out.println("'Daft Punk - Get lucky' started to play!");
                break;
            case 9:
                System.out.println("'DNCE - Cake by the Ocean' started to play!");
                break;
            default:
                System.out.println("Music started to play!");
                break;
        }
    }

    // Chooses a random song.
    private static String randomizer() {
        chosenSong = String.valueOf(random.nextInt(NUMBER_OF_SONGS));
        while (previousSongs.contains(chosenSong)) {
            chosenSong = String.valueOf(random.nextInt(NUMBER_OF_SONGS));
        }
        System.out.println("Previously used songs: " + previousSongs);
        previousSongs.add(chosenSong);
        if (previousSongs.size() > 5) previousSongs.remove(0);
        System.out.println("Song this time: " + chosenSong);
        return chosenSong;
    }

    // Plays dying sound.
    public static void playDying() {
        die.play(soundVolume);
    }

    // Sets master volume.
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

    public static Sound getDie() {
        return die;
    }
    public static float getMasterVolume() {
        return masterVolume;
    }

    public static float getSoundVolume() {
        return soundVolume;
    }

    public static void dispose() {
        menuMusic.dispose();
        playMusic.dispose();
    }
}
