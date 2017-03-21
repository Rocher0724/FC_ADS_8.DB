package choongyul.android.com.databasebasic.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by myPC on 2017-02-14.
 */
@DatabaseTable(tableName = "bbs")
public class Bbs {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String title;

    @DatabaseField
    private String content;

    @DatabaseField
    private Date currentDate;

    public String getContent() {
        return content;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    // ormlite는 디폴트 생성자를 하나 가지고있어야한다
    // 이게 없으면 ormlite가 동작하지 않는다.
    Bbs() {

    }

    public Bbs(String title, String content, Date currentDate) {
        this.title = title;
        this.content = content;
        this.currentDate = currentDate;
    }



}
