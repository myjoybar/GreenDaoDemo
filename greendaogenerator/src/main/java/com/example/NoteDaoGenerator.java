package com.example;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by joybar on 8/18/16.
 */
public class NoteDaoGenerator {

    //http://blog.csdn.net/axuanqq/article/details/49585387
    //http://blog.csdn.net/chenguang79/article/details/50441343
    //http://blog.csdn.net/luckchoudog/article/details/51274241
    // http://www.jianshu.com/p/db639ea070ce
   // http://www.jianshu.com/collection/3ac0f0d78091
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
        addUserAndPic(schema);
        //自动生成java模板类
        new DaoGenerator().generateAll(schema, autoGenerateJavaPath);
    }

    private static void addNote(Schema schema) {
        schema.setDefaultJavaPackageDao(daoPackageName);//如果不指定 默认与entityPackageName一致
        Entity note = schema.addEntity(entityClassName);

        note.setHasKeepSections(true); // 此实体类不会覆盖自定义的代码
        note.implementsSerializable();// 序列化
        // note.implementsInterface("com.sivan.A"); // 实现 A 接口
        // note.setSuperclass("com.sivan.B"); // 继承 B 类
        // note.addImport("com.sivan.C"); // 导包
        note.addIdProperty();//主键
        note.addStringProperty("title").notNull();//表的地2列 列名
        note.addStringProperty("content");//表的地3列 列名
        note.addStringProperty("createTime");//表的地4列 列名
        note.addStringProperty("addNewColumn");//表的地5列 列名//新添加，测试数据库升级
        note.setClassNameDao("NoteDao");//设置dao类的名称
        note.setJavaDoc("auto greenDao generate javaBean by joybar");
        note.setTableName("tb_note");//设置表名,默认是entityClassName(NOTE)的大写形式
    }

    private static void addUserAndPic(Schema schema) {
        schema.setDefaultJavaPackageDao(daoPackageName);//如果不指定 默认与entityPackageName一致

        //User

        Entity user = schema.addEntity("User");
        user.setHasKeepSections(true); // 此实体类不会覆盖自定义的代码

       // user.addIdProperty();//主键 //目前多表关联只能支持关联的表只能有一个主键,不能加入user.addIdProperty();
        user.addStringProperty("name").notNull();//表的地2列 列名
        user.addStringProperty("sex");//表的地3列 列名
        user.addStringProperty("joinTime");//表的地4列 列名
        user.setClassNameDao("UserDao");//设置dao类的名称
        user.setJavaDoc("auto greenDao generate javaBean by joybar");
        user.setTableName("tb_user");//设置表名,默认是entityClassName(NOTE)的大写形式

        //Picture
        Entity picture = schema.addEntity("Picture");
        picture.addLongProperty("pictureId").primaryKey();
        picture.addStringProperty("pictureName").notNull();
        picture.addIntProperty("width");
        picture.addIntProperty("height");
        picture.setTableName("tb_pic");//设置表名,默认是entityClassName(NOTE)的大写形式


        //建立一对一关联
        Property property =  user.addLongProperty("pictureId").getProperty();
        user.addToOne(picture,property);


    }

}
