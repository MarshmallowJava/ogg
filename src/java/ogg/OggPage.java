package ogg;

import java.util.Map;

public class OggPage {

    public static final Tag[] HEADER = new Tag[]{
        new Tag("capture_pattern", 4),
        new Tag("stream_structure_version", 1),
        new Tag("header_type_flag", 1),
        new Tag("granule_position", 8),
        new Tag("bitstream_serial_number", 4),
        new Tag("page_squence_number", 4),
        new Tag("crc_checksum", 4),
        new Tag("number_page_segments", 1)
    };

    public static final String MAGIC = "OggS";

    private byte stream_structure_version;
    private byte header_type_flag;

    private long granule_position;

    private int bitstream_serial_number;
    private int page_squence_number;
    private int crc_checksum;

    private byte number_page_segments;

    public OggPage(Map<String, Data> data){
        if(! data.get("capture_pattern").asText().equals(MAGIC)) throw new RuntimeException();

        this.stream_structure_version = data.get("stream_structure_version").getRawData()[0];
        this.header_type_flag = data.get("header_type_flag").getRawData()[0];
        this.granule_position = data.get("granule_position").asLittleEndianLong();
        this.bitstream_serial_number = data.get("bitstream_serial_number").asLittleEndianInt();
        this.page_squence_number = data.get("page_squence_number").asLittleEndianInt();
        this.crc_checksum = data.get("crc_checksum").asLittleEndianInt();
        this.number_page_segments = data.get("number_page_segments").getRawData()[0];
    }

    public byte getVersion(){
        return this.stream_structure_version;
    }

    public byte getHeaderType(){
        return this.header_type_flag;
    }

    public long getGranulePosition(){
        return this.granule_position;
    }

    public int getSerialNumber(){
        return this.bitstream_serial_number;
    }

    public int getPageSequenceNumber(){
        return this.page_squence_number;
    }

    public int getChecksum(){
        return this.crc_checksum;
    }

    public byte getPageSequenceCount(){
        return this.number_page_segments;
    }
}
