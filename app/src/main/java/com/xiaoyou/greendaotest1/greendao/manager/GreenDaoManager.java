package com.xiaoyou.greendaotest1.greendao.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.joybar.db.dao.DaoMaster;
import com.joybar.db.dao.DaoSession;
import com.joybar.db.dao.NoteDao;
import com.xiaoyou.greendaotest1.greendao.dbhelper.MigrationHelper;
import com.xiaoyou.greendaotest1.utils.APPUtils;


/**
 * Created by joybar on 8/19/16.
 */
public class GreenDaoManager {
    private static String TAG = "GreenDaoManager";
    private static GreenDaoManager greenDaoManager;
    private static DaoSession daoSession;//greenDao数据库
    private static DaoMaster.DevOpenHelper helper;
    private static Context context;
    public static final String dbName = "mynote.db";

    public GreenDaoManager() {
    }

    public static void initialize(Context cxt) {
        context = cxt;
        initGreenDao();
    }

    public static GreenDaoManager newInstance() {
        if (null == greenDaoManager) {
            greenDaoManager = new GreenDaoManager();
        }

        return greenDaoManager;
    }


    private static void initGreenDao() {
        helper = new DaoMaster.DevOpenHelper(context, dbName, null);//创建数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        int dbVersion = DaoMaster.SCHEMA_VERSION;
        int appVersionCode = APPUtils.getVersionCode();
        if (dbVersion < appVersionCode) {
            //更新数据库
            Log.d(TAG, "更新数据库");
            MigrationHelper.getInstance().migrate(db, NoteDao.class);
        } else {
            Log.d(TAG, "不更新数据库");
            // MigrationHelper.getInstance().migrate(db, NoteDao.class);
        }
    }


    public DaoSession getDaoSession() {
        if (daoSession == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, dbName, null);//创建数据库
            DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public void closeGreenDao() {
        if (null != daoSession) {
            daoSession.clear();
            daoSession = null;
        }
        if (null != helper) {
            helper.close();
            helper = null;
        }
    }


}
