import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SimpleAudioPlayer
{
    Clip clip;
    AudioInputStream audioInputStream;
    String filePath;

    public SimpleAudioPlayer()
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        File f = new File("mywavefile.wav");
        audioInputStream =
                AudioSystem.getAudioInputStream(f.getAbsoluteFile());

        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    }

    public void myAudioPlay()
    {
        try
        {
            SimpleAudioPlayer audioPlayer =
                    new SimpleAudioPlayer();
            audioPlayer.clip.start();
        }
        catch (Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }
    }
}

