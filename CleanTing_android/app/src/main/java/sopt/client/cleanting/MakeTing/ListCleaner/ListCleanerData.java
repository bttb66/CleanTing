package sopt.client.cleanting.MakeTing.ListCleaner;

/**
 * Created by glgld on 2017-07-03.
 */

public class ListCleanerData {
    public String Limg;
    public String ListCleanername;
    public String ListCleanerhistory;
    public String ListCleanercomment;
    public String ListCleanerstar;


    public ListCleanerData(String name, String history, String comment)
    {
        ListCleanername = name;
        ListCleanerhistory = history;
        ListCleanercomment = comment;
    }


    public String getLimg() {
        return Limg;
    }

    public String getListCleanercomment() {
        return ListCleanercomment;
    }

    public String getListCleanerhistory() {
        return ListCleanerhistory;
    }

    public String getListCleanername() {
        return ListCleanername;
    }

    public String getListCleanerstar() {
        return ListCleanerstar;
    }

    public void setLimg(String limg) {
        Limg = limg;
    }

    public void setListCleanercomment(String listCleanercomment) {
        ListCleanercomment = listCleanercomment;
    }

    public void setListCleanerhistory(String listCleanerhistory) {
        ListCleanerhistory = listCleanerhistory;
    }

    public void setListCleanername(String listCleanername) {
        ListCleanername = listCleanername;
    }

    public void setListCleanerstar(String listCleanerstar) {
        ListCleanerstar = listCleanerstar;
    }
}
