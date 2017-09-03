package sopt.client.cleanting.MyRequest;

import java.io.Serializable;

/**
 * Created by 김지원 on 2017-06-28.
 */

public class MyRequestData implements Serializable{
    String day, time, member;
//    String img, member;

    public MyRequestData(String day, String time, String member) {
        this.day = day;
        this.time = time;
        this.member = member;

//        this.img = img;
//        this.member = member;
    }
}
