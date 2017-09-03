package sopt.client.cleanting.MakeTing.RecentCleaner;

/**
 * Created by glgld on 2017-07-03.
 */

public class RecentCleanerData
{
    public String R_img;
    public String R_name;
    public String R_date;
    public int Star;

    public RecentCleanerData(String name, String date)
    {
        R_name = name;
        R_date = date;
    }

    public int getStar() {
        return Star;
    }

    public String getR_date() {
        return R_date;
    }

    public String getR_img() {
        return R_img;
    }

    public String getR_name() {
        return R_name;
    }

    public void setR_date(String r_date) {
        R_date = r_date;
    }

    public void setR_img(String r_img) {
        R_img = r_img;
    }

    public void setR_name(String r_name) {
        R_name = r_name;
    }

    public void setStar(int star) {
        Star = star;
    }
}
