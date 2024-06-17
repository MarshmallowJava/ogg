package ogg;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class IOUtil{

    public static Map<String, Data> readData(InputStream in, Tag... tags) throws IOException{
        Map<String, Data> data = new HashMap<>();

        for(Tag tag : tags){
            byte[] buf = new byte[tag.getSize()];
            in.read(buf, 0, buf.length);

            data.put(tag.getName(), new Data(buf));
        }

        return data;
    }
}