import java.io.FileInputStream;
import java.io.IOException;

import ogg.OggInputStream;

public class Main{

    public static void main(String[] args){
        try (FileInputStream fis = new FileInputStream("data.ogg");OggInputStream ois = new OggInputStream(fis);){
            ois.read();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}