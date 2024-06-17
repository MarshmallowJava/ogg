package ogg;

/**
 * ヘッダの値などの名前とデータのペアです
 */
public class Tag {

    private String name;
    private int size;

    public Tag(String name, int size){
        this.name = name;
        this.size = size;
    }

    public String getName(){
        return this.name;
    }

    public int getSize(){
        return this.size;
    }
}
