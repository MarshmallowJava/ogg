package ogg;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class IOUtil{

    public static Map<String, Data> readData(InputStream in, Tag... tags) throws IOException{
        Map<String, Data> data = new HashMap<>();

        for(Tag tag : tags){
            data.put(tag.getName(), new Data(read(in, tag.getSize())));
        }

        return data;
    }

    public static byte[] read(InputStream in, int length) throws IOException{
        byte[] buf = new byte[length];
        in.read(buf, 0, length);

        return buf;
    }
}