package com.example.rohilscomputer.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class userDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "USERS.db";
    private static final String DATABASE_TABLE_NAME_users = "users";
    private static final String DATABASE_TABLE_NAME_bossFighter = "boss";
    private static final String DATABASE_TABLE_NAME_TChest = "TChest";
    //user table
    private static final String uCol_1 = "UserID";
    private static final String uCol_2 = "Username";
    private static final String uCol_3 = "Password";
    private static final String uCol_4 = "Email";
    private static final String uCol_5 = "UserLevel";
    private static final String uCol_6 = "UserAttack";
    private static final String uCol_7 = "UserHealth";
    private static final String uCol_8 = "UserExpNeeded";
    private static final String uCol_9 = "TotalUserExp";

    //boss table
    private static final String bCol_1 = "BossID";
    private static final String bCol_2 = "BossName";
    private static final String bCol_3 = "BossLongitude";
    private static final String bCol_4 = "BossLatitude";
    private static final String bCol_5 = "BossHealth";
    private static final String bCol_6 = "ExpRecieved";
    private static final String bCol_7 = "AttackDamage";
    private static final String bCol_8 = "AttackSpeed";

    //chest table
    private static final String cCol_1 = "ChestLatitude";
    private static final String cCol_2 = "ChestLongitude";
    private static final String cCol_3 = "ChestEXP";
    private static final String cCol_4 = "ChestCurrency";

    private static final String CREATE_STATEMENT_users = "" +
            "create table users( " +
            "UserID integer primary key, " +
            "Username varchar(100) not null, " +
            "Password varchar(100) not null, " +
            "Email varchar(100) not null, " +
            "UserLevel integer not null, " +
            "UserAttack integer not null, " +
            "UserHealth integer not null, " +
            "UserExpneeded integer not null, " +
            "TotalUserExp integer not null)";

    private static final String CREATE_STATEMENT_boss = "" +
            "create table boss( " +
            "BossID integer primary key, " +
            "BossName varchar(100) not null, " +
            "BossLongitude double, " +
            "BossLatitude double, " +
            "BossHealth integer not null, " +
            "ExpRecieved integer not null, " +
            "AttackDamage integer not null, " +
            "AttackSpeed integer not null)";

    private static final String CREAT_STATEMENT_chest = "" +
            "create table TChest( " +
            "ChestLongitude double, " +
            "ChestLatitude double, " +
            "ChestCurrency int, " +
            "ChestEXP int)";

    private static final String DROP_STATEMENT = "" +
            "drop table users ";

    public userDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATEMENT_users);
        db.execSQL(CREATE_STATEMENT_boss);
        db.execSQL(CREAT_STATEMENT_chest);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_STATEMENT);
        db.execSQL(CREATE_STATEMENT_users);
        db.execSQL(CREATE_STATEMENT_boss);
        db.execSQL(CREAT_STATEMENT_chest);
    }
//searchs for username and returns password for login (username table)
    public String searchCred(String username)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = "select Username, Password from " +DATABASE_TABLE_NAME_users;
        Cursor cursor = db.rawQuery(query,null);
        String uname, passw;
        passw = "no such username";

        if(cursor.moveToFirst()) {
            do {
                uname = cursor.getString(0);
                if (uname.equals(username)) {
                    passw = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return passw;
    }

    public User getUser()
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " +DATABASE_TABLE_NAME_users;
        Cursor cursor = db.rawQuery(query,null);
        User user = new User(cursor.getString(1),cursor.getString(2),cursor.getString(2),
                cursor.getString(3),cursor.getString(3));
        return user;
    }
//Adds user into the database (username)
    public boolean insertUser(User user)
    {
        long moveOn;
        if(user != null)
        {
            if (user.getPassword().equals(user.getcPassword()) && user.getEmail().equals(user.getcEmail()))
            {
                moveOn = 1;
            }
            else
            {
                moveOn = -1;
            }
            if(moveOn == 1)
            {
                {
                    SQLiteDatabase db = getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(uCol_2,user.getUsername());
                    contentValues.put(uCol_3,user.getPassword());
                    contentValues.put(uCol_4,user.getEmail());
                    contentValues.put(uCol_5,user.getUserLevel());
                    contentValues.put(uCol_6,user.getAttackDamage());
                    contentValues.put(uCol_7,user.getHealth());
                    contentValues.put(uCol_8,user.getExpNeeded());
                    contentValues.put(uCol_9, user.getUserTotalEXP());
                    db.insert(DATABASE_TABLE_NAME_users,null,contentValues);
                    long result = 1;
                    if (result == -1)
                    {
                        return false;
                    }
                    else
                    {
                        return true;
                    }
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }




    }

    public boolean updateUserStats(User user)
    {
        SQLiteDatabase db = getWritableDatabase();
        if(user != null)
        {
             db.execSQL("update " + DATABASE_TABLE_NAME_users +" set UserLevel = " + user.getUserLevel() + " , UserAttack = "+
                     user.getAttackDamage() + " , UserHealth = " + user.getHealth() + " , UserExpNeeded = " + user.getExpNeeded()
                     + " , TotalUserExp = " + user.getUserTotalEXP() +" where Username = " + user.getUsername());

             return true;
        }
        else
        {
            return false;
        }
    }
    //boss table functions
//Inserts boss battles

    public boolean insertBossFighters(bossFighter bossFighter)
    {
        if (bossFighter != null)
        {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(bCol_2, bossFighter.getBossName());
            contentValues.put(bCol_3,bossFighter.getBossLongitude());
            contentValues.put(bCol_4,bossFighter.getBossLatitude());
            contentValues.put(bCol_5,bossFighter.getBossHealth());
            contentValues.put(bCol_6,bossFighter.getExpRecieved());
            contentValues.put(bCol_7,bossFighter.getAttackDamage());
            contentValues.put(bCol_8,bossFighter.getBossAttackSpeed());
            db.insert(DATABASE_TABLE_NAME_bossFighter,null,contentValues);
            long result = 1;
            if (result == 1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    //return boss location
    public ArrayList<bossFighter> bossLocator()
    {

        ArrayList<bossFighter> bossList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select Latitude, Longitude, ChestCurrency, BossLatitude, BossLongitude from " + DATABASE_TABLE_NAME_bossFighter;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            do {
                bossFighter bossFighter = new bossFighter(cursor.getString(0),cursor.getInt(1),cursor.getInt(2),cursor.getDouble(3),
                        cursor.getDouble(4));
                bossList.add(bossFighter);

            }
            while (cursor.moveToNext());
        }
        return bossList;
    }

    public int[] bossStats(double lat, double lng, String bName)
    {
        System.out.println("Boss stats getter");
        SQLiteDatabase db = getReadableDatabase();
        int[] bossStatsVar = new int[4];
        String query = "select BossHealh, AttackDamage, AttackSpeed, ExpRecieved from " +
                DATABASE_TABLE_NAME_bossFighter + " where BossLatitude = " + lat + " , BossLongitude = "
                + lng + " , BossName = " + bName;

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            do {
                for(int i = 0; i < 4; i++)
                {
                    bossStatsVar[i] = cursor.getInt(i);
                }
                break;
            }
            while(cursor.moveToNext());
        }

        return bossStatsVar;
    }



    //chest table functions
//inserts chests
    public boolean insertChest(TChest TChest)
    {
        if(TChest != null)
        {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(cCol_1,TChest.getTCLatitude());
            contentValues.put(cCol_2,TChest.getTCLongitude());
            contentValues.put(cCol_3,TChest.getTCExp());
            contentValues.put(cCol_4,TChest.getTCCurrency());
            System.out.println(TChest.getTCCurrency());
            db.insert(DATABASE_TABLE_NAME_TChest,null,contentValues);
            long result = 1;
            if (result == 1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }

    }
//returns chest rewards
    public int[] chestLocator(double lat, double lng)
    {

        int[] rewards = new int[2];
        SQLiteDatabase db = getReadableDatabase();
        String query = "select ChestEXP, ChestCurrency from " + DATABASE_TABLE_NAME_TChest + " where ChestLatitude = " + lat +
                ", ChestLongitude = " + " lng";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            for (int i = 0; i < 2; i++)
            {
                rewards[i] = cursor.getInt(i);
            }
        }
        return rewards;
    }
}
