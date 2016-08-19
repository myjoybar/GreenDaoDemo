# GreenDaoDemo
studyDemos about greenDAO

# 使用步骤
#### 1生成DAO和实体

```
  public static void main(String[] args) throws Exception {

        Schema schema = new Schema(version, entityPackageName);
        addNote(schema);
        addUser(schema);
        //自动生成java模板类
        new DaoGenerator().generateAll(schema, autoGenerateJavaPath);
    }

    private static void addNote(Schema schema) {
        schema.setDefaultJavaPackageDao(daoPackageName);//如果不指定 默认与entityPackageName一致
        Entity entity = schema.addEntity(entityClassName);
        entity.addIdProperty();//主键
        entity.addStringProperty("title").notNull();//表的地2列 列名
        entity.addStringProperty("content");//表的地3列 列名
        entity.addStringProperty("createTime");//表的地4列 列名
        entity.addStringProperty("addNewColumn");//表的地5列 列名//新添加，测试数据库升级
        entity.setClassNameDao("NoteDao");//设置dao类的名称
        entity.setJavaDoc("auto greenDao generate javaBean by xuan");
        entity.setTableName("tb_note");//设置表名,默认是entityClassName(NOTE)的大写形式
    }

    private static void addUser(Schema schema) {
        schema.setDefaultJavaPackageDao(daoPackageName);//如果不指定 默认与entityPackageName一致
        Entity entity = schema.addEntity("User");
        entity.addIdProperty();//主键
        entity.addStringProperty("name").notNull();//表的地2列 列名
        entity.addStringProperty("sex");//表的地3列 列名
        entity.addStringProperty("joinTime");//表的地4列 列名
        entity.setClassNameDao("UserDao");//设置dao类的名称
        entity.setJavaDoc("auto greenDao generate javaBean by joybar");
        entity.setTableName("tb_user");//设置表名,默认是entityClassName(NOTE)的大写形式
    }
```
#### 2在application中初始化GreenDao

```
  GreenDaoManager.initialize(this);//初始化数据库
```
在GreenDaoManager.java

```
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
```

   ![image](https://github.com/myjoybar/GreenDaoDemo/blob/master/img/demo.png)  
