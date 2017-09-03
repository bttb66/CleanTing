package sopt.client.cleanting.MakeTing.CleanerDetail;

/**
 * Created by glgld on 2017-07-03.
 */

public class ReviewItem {
    public String writername;
    public String writedate;
    public String writestar;
    public String writecomment;

    public ReviewItem(String name, String date, String comment)
    {
        writername = name;
        writedate = date;
        writecomment = comment;
    }

    public String getWritecomment() {
        return writecomment;
    }

    public String getWritedate() {
        return writedate;
    }

    public String getWritername() {
        return writername;
    }

    public String getWritestar() {
        return writestar;
    }

    public void setWritecomment(String writecomment) {
        this.writecomment = writecomment;
    }

    public void setWritedate(String writedate) {
        this.writedate = writedate;
    }

    public void setWritername(String writername) {
        this.writername = writername;
    }

    public void setWritestar(String writestar) {
        this.writestar = writestar;
    }
}
