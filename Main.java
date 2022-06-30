import java.io.IOException;
import java.util.*;
import javax.sound.sampled.*;
public class Main
{
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException
    {
        new SimpleAudioPlayer().myAudioPlay();
        System.out.println("WELCOME TO LIBRARY MANAGEMENT SYSTEM");
        new LoginClass().welcomeMessage();
    }
}