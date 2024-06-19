package ogg;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class IOUtil{

    public static Map<String, Data> readData(InputStream in, Tag... tags) throws IOException{
        Map<String, Data> data = new HashMap<>();

        for(Tag tag : tags){
            byte[] buf = read(in, tag.getSize());
            if(buf == null) return null;
            
            data.put(tag.getName(), new Data(buf));
        }

        return data;
    }

    private static byte[] read(InputStream in, int length) throws IOException{
        byte[] buf = new byte[length];
        if(in.read(buf, 0, length) != length){
            return null;
        }

        return buf;
    }
}