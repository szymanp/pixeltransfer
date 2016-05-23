/**
 * This class is generated by jOOQ
 */
package com.metapx.pixeltransfer.database.generated.tables;


import com.metapx.pixeltransfer.database.generated.Keys;
import com.metapx.pixeltransfer.database.generated.Public;
import com.metapx.pixeltransfer.database.generated.tables.records.FolderRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Folder extends TableImpl<FolderRecord> {

    private static final long serialVersionUID = 1512139959;

    /**
     * The reference instance of <code>PUBLIC.FOLDER</code>
     */
    public static final Folder FOLDER = new Folder();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FolderRecord> getRecordType() {
        return FolderRecord.class;
    }

    /**
     * The column <code>PUBLIC.FOLDER.ID</code>.
     */
    public final TableField<FolderRecord, Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.FOLDER.NAME</code>.
     */
    public final TableField<FolderRecord, String> NAME = createField("NAME", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

    /**
     * The column <code>PUBLIC.FOLDER.PARENT_ID</code>.
     */
    public final TableField<FolderRecord, Integer> PARENT_ID = createField("PARENT_ID", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>PUBLIC.FOLDER</code> table reference
     */
    public Folder() {
        this("FOLDER", null);
    }

    /**
     * Create an aliased <code>PUBLIC.FOLDER</code> table reference
     */
    public Folder(String alias) {
        this(alias, FOLDER);
    }

    private Folder(String alias, Table<FolderRecord> aliased) {
        this(alias, aliased, null);
    }

    private Folder(String alias, Table<FolderRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<FolderRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_7;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<FolderRecord>> getKeys() {
        return Arrays.<UniqueKey<FolderRecord>>asList(Keys.CONSTRAINT_7);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Folder as(String alias) {
        return new Folder(alias, this);
    }

    /**
     * Rename this table
     */
    public Folder rename(String name) {
        return new Folder(name, null);
    }
}