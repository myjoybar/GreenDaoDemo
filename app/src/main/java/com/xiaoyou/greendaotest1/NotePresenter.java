package com.xiaoyou.greendaotest1;

import android.content.Context;

import com.joybar.db.dao.DaoSession;
import com.joybar.db.dao.NoteDao;
import com.joybar.db.entity.Note;
import com.xiaoyou.greendaotest1.greendao.manager.GreenDaoManager;

import java.util.List;

/**
 * Created by joybar on 8/18/16.
 */
public class NotePresenter {

     DaoSession daoSession;
//    DaoMaster daoMaster;
//    DaoMaster.DevOpenHelper helper;

    public NotePresenter(Context context) {
        //建议放在application
//        helper = new DaoMaster.DevOpenHelper(context, dbName, null);//创建数据库
//        daoMaster = new DaoMaster(helper.getWritableDatabase());
//        daoSession = daoMaster.newSession();
        daoSession =   GreenDaoManager.newInstance().getDaoSession();

    }

    //插入一个对象
    public long add(Note note) throws Exception {
        return daoSession.insertOrReplace(note);

    }

    //插入多个对象
    public void addList(final List<Note> notes) throws Exception {
        //   daoSession.getNoteDao().insertInTx(notes);//都可以

        daoSession.getNoteDao().getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < notes.size(); i++) {
                    Note user = notes.get(i);
                    daoSession.getNoteDao().insertOrReplace(user);
                }
            }
        });
    }

    //更新一个对象
    public void update(Note note) throws Exception {
        daoSession.update(note);
    }

    //查询所有数据
    public List<Note> loadAll() throws Exception {
        return daoSession.getNoteDao().loadAll();
    }

    //查询所有数据
    public List<Note> queryAll() throws Exception {
        //  return daoSession.getNoteDao().queryBuilder().list();
        return daoSession.getNoteDao().queryBuilder().orderDesc(NoteDao.Properties.Id).list();

    }

    //QueryBuilder 复合查询
    public List<Note> queryAllByCondition() throws Exception {
        //  return daoSession.getNoteDao().queryBuilder().list();
      //  return daoSession.getNoteDao().queryBuilder().orderDesc(NoteDao.Properties.Id).list();
        return daoSession.getNoteDao().queryBuilder().where(NoteDao.Properties.Content.like("%5555%")).build().list();
        //下面是查询数量
        //return daoSession.getNoteDao().queryBuilder().where(NoteDao.Properties.Content.like("greendao")).buildCount().count();


    }

    //根据用户id,取出用户信息
    public Note queryById(long id) throws Exception {
        // return daoSession.getNoteDao().queryBuilder().list();
        return daoSession.getNoteDao().load(id);
    }

    //删除某个对象
    public void delete(Note note) throws Exception {
        daoSession.delete(note);
    }

    //根据用户id删除某个对象
    public void deleteById(long id) throws Exception {
        daoSession.getNoteDao().deleteByKey(id);
    }


    //删除所有对象
    public void deleteAll() throws Exception {
        daoSession.getNoteDao().deleteAll();
    }

    public void close() {
//        daoSession.clear();
//        daoSession = null;
//        helper.close();
//        helper = null;
        GreenDaoManager.newInstance().closeGreenDao();
    }
}
