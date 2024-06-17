package ogg;

/**
 * バイト配列のラッパークラス
 */
public class Data {

    private final byte[] data;

    public Data(byte[] array){
        this.data = array;
    }

    public byte[] getRawData(){
        return this.data;
    }

    public int size(){
        return this.data.length;
    }

    private byte get(int index){
        if(0 <= index && index < this.size()) return this.data[index];

        return 0;
    }

    public int asLittleEndianInt(){
        if(this.size() > 4) throw new RuntimeException();

        int i0 = this.get(0);
        int i1 = this.get(1);
        int i2 = this.get(2);
        int i3 = this.get(3);

        return (i0 << 0) + (i1 << 8) + (i2 << 16) + (i3 << 24);
    }

    public int asBigEndianInt(){
        if(this.size() > 4) throw new RuntimeException();

        int i0 = this.get(3);
        int i1 = this.get(2);
        int i2 = this.get(1);
        int i3 = this.get(0);

        return (i0 << 0) + (i1 << 8) + (i2 << 16) + (i3 << 24);
    }

    public long asLittleEndianLong(){
        if(this.size() > 8) throw new RuntimeException();

        long l0 = this.get(0);
        long l1 = this.get(1);
        long l2 = this.get(2);
        long l3 = this.get(3);
        long l4 = this.get(4);
        long l5 = this.get(5);
        long l6 = this.get(6);
        long l7 = this.get(7);

        return (l0 << 0) + (l1 << 8) + (l2 << 16) + (l3 << 24) + (l4 << 32) + (l5 << 40) + (l6 << 48) + (l7 << 56);
    }

    public long asBigEndianLong(){
        long l0 = this.get(7);
        long l1 = this.get(6);
        long l2 = this.get(5);
        long l3 = this.get(4);
        long l4 = this.get(3);
        long l5 = this.get(2);
        long l6 = this.get(1);
        long l7 = this.get(0);

        return (l0 << 0) + (l1 << 8) + (l2 << 16) + (l3 << 24) + (l4 << 32) + (l5 << 40) + (l6 << 48) + (l7 << 56);
    }

    public String asText(){
        return new String(this.data);
    }

    @Override
    public String toString(){
        return String.format("{size=%d, as_long=%d, as_text=%s}", this.size(), this.asLittleEndianLong(), this.asText());
    }
}
