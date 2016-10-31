package com.du.gout_app.common;

import java.util.Date;

/**
 * Created by 杜哲凯 on 2016/7/11.
 */
public class News {
    private int id;
    private String title;
    private String intro;
    private Date createdate;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public News()
    {
        super();
    }

    public News(int id,String title,String intro,Date createdate){
        this.id=id;
        this.title = title;
        this.intro = intro;
        this.createdate = createdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}
