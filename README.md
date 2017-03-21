# FC_ADS_8.DB
패스트캠퍼스 안드로이드 스튜디오 프로젝트 8. 데이터베이스

* 테이블 내용을 담고있는 Bbs클래스 
```

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


```

* DB helper 구성
```

public class DBHelper extends OrmLiteSqliteOpenHelper {

    public static final String DB_NAME = "database.db";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 생성자에서 호출되는 super(context... 에서 database.db 파일이 생성되어 있지 않으면 호출된다
     * 즉 여기에는 모든 데이터 테이블이 들어가있어야한다.
     * @param database
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            // Bbs.class 파일에 정의된 테이블을 생성한다.
            TableUtils.createTable(connectionSource, Bbs.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 이전 버전을 갖고 있는 사람이 새 버전으로 업데이트 할때 호출된다.
     * 예를들면 버전 1때는 아무것도 들어가있지 않아도 되며
     * 버전 2때는 버전 2에서 추가된 테이블만 들어간다.
     * 자세한 사용예시는 내 깃헙에서 project - Memoappp 을 확인해보자
     * @param database
     * @param connectionSource
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try{
            // Bbs.class 에서 정의된 테이블을 삭제
            TableUtils.dropTable(connectionSource, Bbs.class, false);
            // 데이터를 보존해야 될 필요성이 있으면 중간처리를 해줘야만 한다.
            // TODO : 임시테이블을 생성한 후 데이터를 먼저 저장하고 onCreate 이후에 데이터를 입력해준다.
            // onCreate를 호출해서 테이블을 다시 생성한다.
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Dao<Bbs, Long> bbsDao = null;
    // Dao 객체를 통해서
    // Long은 키값이고 Bbs는 자료이다.
    public Dao<Bbs, Long> getBbsDao() throws SQLException {
        if(bbsDao != null) {
            return bbsDao;
        }
        bbsDao = getDao(Bbs.class);
        return bbsDao;
    }

    // 메모리에서 null 시켜주는게 보통 release 함수들의 역할이다.
    public void releaseBbsDao() {
        if (bbsDao != null) {
            bbsDao = null;
        }
    }
}


```