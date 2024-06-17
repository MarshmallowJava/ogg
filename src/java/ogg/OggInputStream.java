package ogg;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 上位ストリームから流れるデータをoggコンテナとして読み込みデータを下位ストリームに流します
 */
public class OggInputStream extends InputStream{

    private InputStream in;

    private Map<Integer, List<OggPage>> pages = new HashMap<>();
    private Map<OggPage, byte[][]> dataset = new HashMap<>();
    private int current_page_number;

    private byte[][] data;
    private int off, off2, off3;

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

    public boolean readPage() throws IOException{
        Map<String, Data> result = IOUtil.readData(this.in, OggPage.HEADER);
        if(result == null) return true;

        OggPage page = new OggPage(result);
    
        int[] sizelist = new int[page.getPageSegmentSize()];
        for(int i = 0;i < sizelist.length;i++){
            sizelist[i] = in.read();
        }

        byte[][] dataset = new byte[sizelist.length][];
        for(int i = 0;i < dataset.length;i++){
            dataset[i] = IOUtil.read(in, sizelist[i]);
        }

        if(! this.pages.containsKey(page.getSerialNumber())) this.pages.put(page.getSerialNumber(), new ArrayList<>());
        List<OggPage> pages = this.pages.get(page.getSerialNumber());
        pages.add(page);

        Collections.sort(pages);

        return false;
    }

    public Set<Integer> getSerialNumbers(){
        return this.pages.keySet();
    }

    @Override
    public int read() throws IOException{
        if(this.off3 == this.data[this.off2].length){
            this.off2++;
            this.off3 = 0;
            if(this.off2 == this.data.length){
                this.off++;
                this.off2 = 0;

                List<OggPage> pages = this.pages.get(this.current_page_number);

                load:while(true){

                    //search for available data
                    for(OggPage page : pages){
                        if(page.getSerialNumber() == this.off) break load;
                    }

                    //EOF
                    if(this.readPage()){
                        return -1;
                    }
                }

                this.data = this.dataset.remove(pages.remove(0));
            }
        }

        return this.data[this.off2][this.off3++];
    }

    @Override
    public void close() throws IOException{
        this.in.close();
    }
}
