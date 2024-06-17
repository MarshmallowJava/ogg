package ogg;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上位ストリームから流れるデータをoggコンテナとして読み込みデータを下位ストリームに流します
 */
public class OggInputStream extends InputStream{

    private InputStream in;

    private Map<Integer, List<OggPage>> pages = new HashMap<>();
    private Map<OggPage, byte[][]> dataset = new HashMap<>();
    private int current_page_number;

    private byte[] data;
    private int off, off2;

    /**
     * コンストラクタ
     * @param in 上位ストリーム
     * @throws IOException 入出力エラー
     */
    public OggInputStream(InputStream in) throws IOException{
        this.in = in;
    }

    public void setPageNumber(int number){
        this.current_page_number = number;
    }

    public void readPage() throws IOException{
        OggPage page = new OggPage(IOUtil.readData(this.in, OggPage.HEADER));
    }

    @Override
    public int read() throws IOException{
        if(this.off < this.data.length){
            return this.data[this.off++];
        }else{
            List<OggPage> pages = this.pages.get(this.current_page_number);
            while(pages.isEmpty()){
                this.readPage();
            }

            OggPage page = pages.remove(0);

        }

        return -1;
    }

    @Override
    public void close() throws IOException{
        this.in.close();
    }
}
