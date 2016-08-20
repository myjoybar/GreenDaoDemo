package com.example;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by joybar on 8/18/16.
 */
public class NoteDaoGenerator {

    //http://blog.csdn.net/axuanqq/article/details/49585387
    //http://blog.csdn.net/chenguang79/article/details/50441343
    //http://blog.csdn.net/luckchoudog/article/details/51274241

    public static final int version = 1;//数据库版本号，
    public static final String entityPackageName = "com.joybar.db.entity";//实体生存的包名
    public static final String entityClassName = "Note";//实体的类名
    public static final String daoPackageName = "com.joybar.db.dao";//指定dao层模板的包
    //自动生成模板类存放的绝对地址，也就是你的module创建的session文件夹 也就是java-gen
    public static final String autoGenerateJavaPath = "/Users/joybar/Documents/ASWorkspace/GitDemos/GreenDaoDemo/app/src/main/java-gen";

    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(version, entityPackageName);
        schema.enableKeepSectionsByDefault(); // 通过此 schema 创建的实体类都不会覆盖自定义的代码


        addNote(schema);
        addUser(schema);
        //自动生成java模板类
        new DaoGenerator().generateAll(schema, autoGenerateJavaPath);
    }

    private static void addNote(Schema schema) {
        schema.setDefaultJavaPackageDao(daoPackageName);//如果不指定 默认与entityPackageName一致
        Entity entity = schema.addEntity(entityClassName);
        entity.setHasKeepSections(true); // 此实体类不会覆盖自定义的代码

        entity.addIdProperty();//主键
        entity.addStringProperty("title").notNull();//表的地2列 列名
        entity.addStringProperty("content");//表的地3列 列名
        entity.addStringProperty("createTime");//表的地4列 列名
        entity.addStringProperty("addNewColumn");//表的地5列 列名//新添加，测试数据库升级
        entity.setClassNameDao("NoteDao");//设置dao类的名称
        entity.setJavaDoc("auto greenDao generate javaBean by joybar");
        entity.setTableName("tb_note");//设置表名,默认是entityClassName(NOTE)的大写形式
    }

    private static void addUser(Schema schema) {
        schema.setDefaultJavaPackageDao(daoPackageName);//如果不指定 默认与entityPackageName一致
        Entity entity = schema.addEntity("User");
        entity.setHasKeepSections(true); // 此实体类不会覆盖自定义的代码

        entity.addIdProperty();//主键
        entity.addStringProperty("name").notNull();//表的地2列 列名
        entity.addStringProperty("sex");//表的地3列 列名
        entity.addStringProperty("joinTime");//表的地4列 列名
        entity.setClassNameDao("UserDao");//设置dao类的名称
        entity.setJavaDoc("auto greenDao generate javaBean by joybar");
        entity.setTableName("tb_user");//设置表名,默认是entityClassName(NOTE)的大写形式
    }

}
