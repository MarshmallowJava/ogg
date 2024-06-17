package ogg;

import java.io.IOException;
import java.io.InputStream;

/**
 * 上位ストリームから流れるデータをoggコンテナとして読み込みデータを下位ストリームに流します
 */
public class OggInputStream extends InputStream{

    private static final Tag[] HEADER = new Tag[]{
        new Tag("magic", 4),
        new Tag("stream_structure_version", 1),
        new Tag("header_type_flag", 1),
        new Tag("stream_serial_number", 4),
        new Tag("absolute_granule_position", 8),
        new Tag("page_squence_no", 4),
        new Tag("page_check_sum", 4),
        new Tag("page_segments", 4)
    };

    private InputStream in;

    public OggInputStream(InputStream in){
        this.in = in;
    }

    @Override
    public int read() throws IOException{
        return -1;
    }

    @Override
    public void close() throws IOException{
        this.in.close();
    }
}
