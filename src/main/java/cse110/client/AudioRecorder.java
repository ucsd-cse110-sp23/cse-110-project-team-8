package cse110.client;

import java.io.*;
import javax.sound.sampled.*;

/**
 * Record audio and save to file.
 */
public class AudioRecorder {
    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;

    public AudioRecorder() {
        this.audioFormat = getAudioFormat();
    }

    private AudioFormat getAudioFormat() {
        // the number of samples of audio per second.
        // 44100 represents the typical sample rate for CD-quality audio.
        float sampleRate = 44100;

        // the number of bits in each sample of a sound that has been digitized.
        int sampleSizeInBits = 16;

        // the number of audio channels in this format (1 for mono, 2 for stereo).
        int channels = 1;

        // whether the data is signed or unsigned.
        boolean signed = true;

        // whether the audio data is stored in big-endian or little-endian order.
        boolean bigEndian = false;

        return new AudioFormat(
        sampleRate,
        sampleSizeInBits,
        channels,
        signed,
        bigEndian
        );
    }

    public void startRecording(String filePath) {
        Thread t = new Thread(
        () -> {
            try {
            // the format of the TargetDataLine
            DataLine.Info dataLineInfo = new DataLine.Info(
                TargetDataLine.class,
                audioFormat
            );
            // the TargetDataLine used to capture audio data from the microphone
            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            targetDataLine.open(audioFormat);
            targetDataLine.start();

            // the AudioInputStream that will be used to write the audio data to a file
            AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);

            // the file that will contain the audio data
            File audioFile = new File(filePath);
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, audioFile);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        );
        t.start();
    }

    public void stopRecording() {
        targetDataLine.stop();
        targetDataLine.close();
    }
}
