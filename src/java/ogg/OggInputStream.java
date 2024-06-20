package ogg;

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
    private Map<OggPage, int[][]> dataset = new HashMap<>();
    private int current_page_number;

    private int[][] data;
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

        int[][] dataset = new int[sizelist.length][];
        for(int i = 0;i < dataset.length;i++){
            dataset[i] = new int[sizelist[i]];

            for(int j = 0;j < sizelist[i];j++){
                dataset[i][j] = this.in.read();
            }
        }

        List<OggPage> pages = this.getList(page.getSerialNumber());
        pages.add(page);

        Collections.sort(pages);

        this.dataset.put(page, dataset);

        return false;
    }

    public Set<Integer> getSerialNumbers(){
        return this.pages.keySet();
    }

    @Override
    public int read() throws IOException{
        if(this.data == null){
            if(!this.loadPage()) return -1;
        }else if(this.off3 == this.data[this.off2].length){
            do{
                this.off2++;
                this.off3 = 0;
                if(this.off2 == this.data.length){
                    this.off++;
                    this.off2 = 0;
    
                    if(!this.loadPage()) return -1;
                }    
            }while(this.data[this.off2].length == 0);
        }

        return this.data[this.off2][this.off3++];
    }

    private boolean loadPage() throws IOException{
        List<OggPage> pages = this.getCurrentList();

        load:while(true){
            //search for available data
            for(OggPage page : pages){
                if(page.getPageSequenceNumber() == this.off) break load;
            }

            //EOF
            if(this.readPage()){
                return false;
            }
        }

        this.data = this.dataset.remove(pages.remove(0));

        return true;
    }

    private List<OggPage> getCurrentList(){
        return this.getList(this.current_page_number);
    }

    public List<OggPage> getList(int number){
        if(! this.pages.containsKey(number)) this.pages.put(number, new ArrayList<>());
        return this.pages.get(number);
    }

    @Override
    public void close() throws IOException{
        this.in.close();
    }
}
