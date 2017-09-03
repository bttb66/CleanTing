package sopt.client.cleanting.Community;

/**
 * Created by glgld on 2017-06-30.
 */

public class Bulletin {

    public String B_writer = "작성자";
    public String B_title;
    public String B_date;
    public String B_content;
    public String B_reply;

    public Bulletin(String title, String date, String content, String reply)
    {
        B_title = title;
        B_date = date;
        B_content = content;
        B_reply = reply;
    }


}
