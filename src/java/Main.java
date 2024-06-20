import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import ogg.OggInputStream;

public class Main{

    public static void main(String[] args){
        try (FileInputStream fis = new FileInputStream("13.ogg");BufferedInputStream bis = new BufferedInputStream(fis);OggInputStream ois = new OggInputStream(bis);FileOutputStream fos = new FileOutputStream(new File("result.txt"));BufferedOutputStream bos = new BufferedOutputStream(fos);){
            //最初のページのシリアルナンバーを利用
            ois.setPageNumber(ois.readPage().getSerialNumber());

            int data;
            while((data = ois.read()) != -1){
                bos.write(data);
            }
            bos.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}