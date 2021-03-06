package choongyul.android.com.databasebasic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import choongyul.android.com.databasebasic.domain.Bbs;

/**
 * Created by myPC on 2017-02-14.
 */

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
