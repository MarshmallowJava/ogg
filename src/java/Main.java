import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import ogg.OggInputStream;

public class Main{

    public static void main(String[] args){
        try (FileInputStream fis = new FileInputStream("data.ogg");BufferedInputStream bis = new BufferedInputStream(fis);OggInputStream ois = new OggInputStream(bis);){
            
            //���ׂẴy�[�W��ǂݍ���
            while(true){
                if(ois.readPage()){
                    break;
                }
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}