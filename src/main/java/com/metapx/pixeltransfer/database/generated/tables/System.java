/**
 * This class is generated by jOOQ
 */
package com.metapx.pixeltransfer.database.generated.tables;


import com.metapx.pixeltransfer.database.generated.Keys;
import com.metapx.pixeltransfer.database.generated.Public;
import com.metapx.pixeltransfer.database.generated.tables.records.SystemRecord;

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
public class System extends TableImpl<SystemRecord> {

    private static final long serialVersionUID = 453952848;

    /**
     * The reference instance of <code>PUBLIC.SYSTEM</code>
     */
    public static final System SYSTEM = new System();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SystemRecord> getRecordType() {
        return SystemRecord.class;
    }

    /**
     * The column <code>PUBLIC.SYSTEM.ID</code>.
     */
    public final TableField<SystemRecord, Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.SYSTEM.SCHEMA_VERSION</code>.
     */
    public final TableField<SystemRecord, Integer> SCHEMA_VERSION = createField("SCHEMA_VERSION", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>PUBLIC.SYSTEM</code> table reference
     */
    public System() {
        this("SYSTEM", null);
    }

    /**
     * Create an aliased <code>PUBLIC.SYSTEM</code> table reference
     */
    public System(String alias) {
        this(alias, SYSTEM);
    }

    private System(String alias, Table<SystemRecord> aliased) {
        this(alias, aliased, null);
    }

    private System(String alias, Table<SystemRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<SystemRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_9;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<SystemRecord>> getKeys() {
        return Arrays.<UniqueKey<SystemRecord>>asList(Keys.CONSTRAINT_9);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public System as(String alias) {
        return new System(alias, this);
    }

    /**
     * Rename this table
     */
    public System rename(String name) {
        return new System(name, null);
    }
}