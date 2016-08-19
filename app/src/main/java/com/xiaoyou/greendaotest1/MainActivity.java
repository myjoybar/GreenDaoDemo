package com.xiaoyou.greendaotest1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.joybar.db.entity.Note;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private TextView tv;
    private EditText et_id;
    NotePresenter presenter;
    Note note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        et_id = (EditText) findViewById(R.id.et_id);

        presenter = new NotePresenter(this);

        note = new Note();
        note.setTitle("这是第一个笔记");
        note.setContent("笔记的内容是greendao");
        note.setCreateTime(DateUtils.formatElapsedTime(Calendar.getInstance().getTimeInMillis()));
    }

    //插入一个对象
    public void onAdd(View v) {
        try {
            presenter.add(note);
            showAllDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //插入多个对象
    public void onAddList(View v) {
        try {
            List<Note> list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Note note = new Note();
                note.setTitle("这是第" + ++i + "个笔记");
                note.setContent("笔记的内容是greendao" + i + i + i + i);
                note.setCreateTime(DateUtils.formatElapsedTime(Calendar.getInstance().getTimeInMillis()));
                list.add(note);
            }
            presenter.addList(list);
            showAllDate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //查询所有数据
    public void onQuery(View v) {
        tv.setText("");
        String str = "";
        try {
            List<Note> list = presenter.loadAll();
            str = "共有" + list.size() + "条数据";
            for (Note note : list) {
                str = str + note.toString() + "\n";
            }
            tv.setText(str);
            Log.d(TAG, "query all:" + presenter.loadAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //查询所有数据
    public void onQueryAll(View v) {
        tv.setText("");
        String str = "";
        try {
            List<Note> list = presenter.queryAll();
            str = "共有" + list.size() + "条数据";
            for (Note note : list) {
                str = str + note.toString() + "\n";
            }
            tv.setText(str);
            Log.d(TAG, "query all:" + presenter.queryAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //根据用户id,取出用户信息
    public void onQueryId(View v) {
        try {
            if (TextUtils.isEmpty(et_id.getText().toString())) {
                return;
            }
            long id = Long.parseLong(et_id.getText().toString());
            Note note = presenter.queryById(id);
            tv.setText("");
            tv.setText("查询的数据" + note.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //查询-复合查询-content.like(555)
    public void onQueryByCondition(View v) {
        tv.setText("");
        String str = "";
        try {
            List<Note> list = presenter.queryAllByCondition();
            str = "共有" + list.size() + "条数据";
            for (Note note : list) {
                str = str + note.toString() + "\n";
            }
            tv.setText(str);
            Log.d(TAG, "query all:" + presenter.queryAll());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //更新一个对象
    public void onUpdate(View v) {
        note.setCreateTime(DateUtils.formatElapsedTime(Calendar.getInstance().getTimeInMillis()));
        note.setTitle("我是更新的");
        try {
            presenter.update(note);
            showAllDate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Log.d(TAG, "update after query all:" + presenter.loadAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //更新Id = 1的对象
    public void onUpdateId1(View v) {
        note.setCreateTime(DateUtils.formatElapsedTime(Calendar.getInstance().getTimeInMillis()));
        note.setTitle("我是更新的ID=1");
        note.setId(1L);
        try {
            presenter.update(note);
            showAllDate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Log.d(TAG, "update after query all:" + presenter.loadAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //删除某个对象
    public void onDelete(View v) {
        try {
            presenter.delete(note);
            showAllDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Log.d(TAG, "delete after query all:" + presenter.loadAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据用户id删除某个对象
    public void onDeleteById(View v) {
        try {
            if (TextUtils.isEmpty(et_id.getText().toString())) {
                return;
            }
            long id = Long.parseLong(et_id.getText().toString());
            presenter.deleteById(id);
            showAllDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Log.d(TAG, "delete after query all:" + presenter.loadAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //删除所有对象

    public void onDeleteAll(View v) {
        try {
            presenter.deleteAll();
            showAllDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Log.d(TAG, "delete after query all:" + presenter.loadAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.close();
    }

    private void showAllDate() {
        tv.setText("");
        String str = "";
        try {
            List<Note> list = presenter.queryAll();
            str = "共有" + list.size() + "条数据";
            for (Note note : list) {
                str = str + note.toString() + "\n";
            }
            tv.setText(str);
            Log.d(TAG, "query all:" + presenter.queryAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
