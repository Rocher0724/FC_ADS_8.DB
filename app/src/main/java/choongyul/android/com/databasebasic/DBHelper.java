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

    private Dao<Bbs, Long> bbsDao;


    // 생성자 제일 위에 것 선택.  databaseName , factory, databaseVersion은 안씀.
    public DBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 생성자에서 호출되는 super(context... 에서 database.db 파일이 생성되어 있지 않으면 호출된다
     * @param database
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            // Bbs.class 파이렝 정의된 테이블을 생성한다.
            TableUtils.createTable(connectionSource, Bbs.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 파일이 존재하지만 DB_VERSION 이 증가하면 호출된다.
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
    // Dao 객체를 통해서
    // Long은 키값이고 Bbs는 자료이다.
    public Dao<Bbs, Long> getDao() throws SQLException {
        if (bbsDao == null) {
            bbsDao = getDao(Bbs.class);
        }
        return bbsDao;
    }
}
