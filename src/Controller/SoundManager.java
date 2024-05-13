package Controller;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager {

    private Clip flapSound;
    private Clip collideSound;
    private Clip getScoreSound;

    public SoundManager() {
        flapSound = loadSound("/Sound/fap.wav");
        collideSound = loadSound("/Sound/fall.wav");
        getScoreSound = loadSound("/Sound/getpoint.wav");
    }

    private Clip loadSound(String fileName) {
        try {
            URL url = this.getClass().getResource(fileName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            AudioFormat baseFormat = audioIn.getFormat();
            AudioFormat decodeFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels()*2,
                    baseFormat.getSampleRate(),
                    false
            );
            AudioInputStream newAudioIn = AudioSystem.getAudioInputStream(decodeFormat, audioIn);
            Clip clip = AudioSystem.getClip();
            clip.open(newAudioIn);
            return clip;

        } catch (UnsupportedAudioFileException | IOException e) {
            System.err.println("Error loading sound file: " + fileName);
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("Error playing sound file: " + fileName);
            System.err.println("The audio format of this file is not supported.");
            e.printStackTrace();
        }
        return null;
    }

    public void playFlapSound() {
        if (flapSound != null) {
            stopFlapSound();
            flapSound.setFramePosition(0);
            flapSound.start();
        }
    }

    public void stopFlapSound() {
        if (flapSound != null && flapSound.isRunning()) {
            flapSound.stop();
        }
    }

    public void playCollideSound() {
        if (collideSound != null) {
            stopCollideSound();
            collideSound.setFramePosition(0);
            collideSound.start();
        }
    }

    public void stopCollideSound() {
        if (collideSound != null && collideSound.isRunning()) {
            collideSound.stop();
        }
    }

    public void playGetScoreSound() {
        if (getScoreSound != null) {
            stopGetScoreSound();
            getScoreSound.setFramePosition(0);
            getScoreSound.start();
        }
    }

    public void stopGetScoreSound() {
        if (getScoreSound != null && getScoreSound.isRunning()) {
            getScoreSound.stop();
        }
    }

    public void close() {
        if (flapSound != null) {
            flapSound.close();
        }
        if (collideSound != null) {
            collideSound.close();
        }
        if (getScoreSound != null) {
            getScoreSound.close();
        }
    }
}