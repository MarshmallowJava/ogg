package ogg;

/**
 * �w�b�_�̒l�Ȃǂ̖��O�ƃf�[�^�̃y�A�ł�
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
