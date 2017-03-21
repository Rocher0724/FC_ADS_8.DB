package choongyul.android.com.databasebasic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import choongyul.android.com.databasebasic.domain.Bbs;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            insert();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insert() throws SQLException {

        // 1. 데이터 베이스를 연결합니다
        // OpenHelperManager는 dbHelper를 생성하는것을 돕는 유틸이다.
        // 팩토리 메소드패턴으로 구현되어 있고 컨텍스트와 클래스파일을 던져주면 new해서 나에게 던져준다.
        // getHelper 가 생성하는 DB class는 this만 갖고 생성을 한다. 싱글턴이기 때문에 close를 안해줘야 한다.
//        DBHelper dbHelper= new DBHelper(this); //
        DBHelper dbHelper= OpenHelperManager.getHelper(this, DBHelper.class);

        // 2. 테이블을 조작하기 위한 객체를 생성합니다.
        Dao<Bbs, Long> bbsDao = dbHelper.getBbsDao();

        // 3. 입력 값 생성
        String title = "제목";
        String content = "내용입니다";
        Date currDateTime = new Date(System.currentTimeMillis());
        // 4. 위의 입력값으로 Bbs 객체 생성
        Bbs bbs = new Bbs(title, content, currDateTime);

        // ---------------------------- 생성 create -----------------------------
        // 5. 생성된 Bbs 객체를 dao를 통해 insert
//        bbsDao.create(bbs);
//        bbsDao.create( new Bbs( "제목2","내용2",new Date(System.currentTimeMillis()) ) );
//        bbsDao.create( new Bbs( "제목3","내용3",new Date(System.currentTimeMillis()) ) );
        // 위 create가 내부적으로
        // String query = "insert into bbs(title, content, curDate) values('제목2', '내용2', 현재시간);"
        // Sqlite.execute(query);
        // 를 해준다.

        // ----------------------------- 읽기 Read -------------------------------
        // 01. 조건 ID
        Bbs bbs2 = bbsDao.queryForId(3L);
        Log.i("Test Bbs one","queryForId ::::::: content = " + bbs2.getContent());

        // 02. 조건 컬럼명 값 (컬럼명과 내용을 지정하여 가져온다.)
        List<Bbs> bbsList2 = bbsDao.queryForEq("title","제목3");
        for(Bbs item : bbsList2) {
            Log.i("Bbs Item","queryForEq ::::::: id = " + item.getId() + ", title = " + item.getTitle());
        }

        // 03. 조건 컬럼 raw query
        String query = "SELECT * FROM bbs where title like '%2%'"; // 2가 들어간 모든 title을 가져온다.
        GenericRawResults<Bbs> rawResults = bbsDao.queryRaw(query, bbsDao.getRawRowMapper());

        List<Bbs> bbsList3 = rawResults.getResults();
        for(Bbs item : bbsList3) {
            Log.i("Bbs Item","queryRaw :::::::: id " + item.getId()+ ", title = " + item.getTitle());
        }

        //-------------------------삭제 Delete---------------------
        // 11. id로 삭제
        bbsDao.deleteById(5L);

        // 12. bbs객체로 삭제
        bbsDao.delete(bbs2);

        //-------------------------수정 update----------------------
        // 21. 수정
        Bbs bbstemp = bbsDao.queryForId(7L);
        bbstemp.setTitle("7번 수정됨");


        // -------------------------------전체 조회 -----------------------------
        // 99. 위에서 입력해 뒀던 값을 모두 꺼낸다.
        List<Bbs> bbsList = bbsDao.queryForAll();
        for( Bbs item : bbsList) {
            Log.i("Bbs Item", "id = " + item.getId() + ", title = " + item.getTitle());
        }


//        dbHelper.close(); // 싱글턴으로 생성하였기 때문에 닫지않는다.


//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(currDateTime);
//        calendar.add(Calendar.DATE, 14);
//
//        Date dueDate = calendar.getTime();
//
//        List<Bbs> bbsList = bbsDao.queryForAll();
    }

}
