package com.example.luyan.smartmenu_shop.GreenDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.luyan.smartmenu_shop.Metadata.DESKITEM;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DESKITEM".
*/
public class DESKITEMDao extends AbstractDao<DESKITEM, Integer> {

    public static final String TABLENAME = "DESKITEM";

    /**
     * Properties of entity DESKITEM.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, int.class, "id", true, "ID");
        public final static Property DeskName = new Property(1, String.class, "deskName", false, "DESK_NAME");
        public final static Property Capacity = new Property(2, int.class, "capacity", false, "CAPACITY");
        public final static Property Statue = new Property(3, int.class, "statue", false, "STATUE");
        public final static Property IsHall = new Property(4, boolean.class, "isHall", false, "IS_HALL");
    }


    public DESKITEMDao(DaoConfig config) {
        super(config);
    }
    
    public DESKITEMDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DESKITEM\" (" + //
                "\"ID\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"DESK_NAME\" TEXT," + // 1: deskName
                "\"CAPACITY\" INTEGER NOT NULL ," + // 2: capacity
                "\"STATUE\" INTEGER NOT NULL ," + // 3: statue
                "\"IS_HALL\" INTEGER NOT NULL );"); // 4: isHall
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DESKITEM\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DESKITEM entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String deskName = entity.getDeskName();
        if (deskName != null) {
            stmt.bindString(2, deskName);
        }
        stmt.bindLong(3, entity.getCapacity());
        stmt.bindLong(4, entity.getStatue());
        stmt.bindLong(5, entity.getIsHall() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DESKITEM entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String deskName = entity.getDeskName();
        if (deskName != null) {
            stmt.bindString(2, deskName);
        }
        stmt.bindLong(3, entity.getCapacity());
        stmt.bindLong(4, entity.getStatue());
        stmt.bindLong(5, entity.getIsHall() ? 1L: 0L);
    }

    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.getInt(offset + 0);
    }    

    @Override
    public DESKITEM readEntity(Cursor cursor, int offset) {
        DESKITEM entity = new DESKITEM( //
            cursor.getInt(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // deskName
            cursor.getInt(offset + 2), // capacity
            cursor.getInt(offset + 3), // statue
            cursor.getShort(offset + 4) != 0 // isHall
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DESKITEM entity, int offset) {
        entity.setId(cursor.getInt(offset + 0));
        entity.setDeskName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCapacity(cursor.getInt(offset + 2));
        entity.setStatue(cursor.getInt(offset + 3));
        entity.setIsHall(cursor.getShort(offset + 4) != 0);
     }
    
    @Override
    protected final Integer updateKeyAfterInsert(DESKITEM entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public Integer getKey(DESKITEM entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DESKITEM entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
