package sopt.client.cleanting.Community.Reply;

/**
 * Created by glgld on 2017-07-03.
 */

public class ReplyData
{
    int postid;
    String Content;
    String Date;

    public ReplyData(String content, String date)
    {
        Content = content;
        Date = date;
    }

    public String getContent() {
        return Content;
    }

    public String getWriter() {
        return Date;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setWriter(String date) {
        this.Date = date;
    }
}
