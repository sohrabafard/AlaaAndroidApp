package ir.sanatisharif.android.konkur96.model;

public class SectionList_class {

    private String headerTitle;
    private int type;


    public SectionList_class() {
    }

    public SectionList_class(String headerTitle, int TYPE) {
        this.headerTitle = headerTitle;
        this.type = TYPE;

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }


}