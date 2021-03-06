/**
 * This class is generated by jOOQ
 */
package com.metapx.pixeltransfer.database.generated.tables;


import com.metapx.pixeltransfer.database.generated.Public;
import com.metapx.pixeltransfer.database.generated.tables.records.ImageFolderRecord;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
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
public class ImageFolder extends TableImpl<ImageFolderRecord> {

    private static final long serialVersionUID = 961198013;

    /**
     * The reference instance of <code>PUBLIC.IMAGE_FOLDER</code>
     */
    public static final ImageFolder IMAGE_FOLDER = new ImageFolder();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ImageFolderRecord> getRecordType() {
        return ImageFolderRecord.class;
    }

    /**
     * The column <code>PUBLIC.IMAGE_FOLDER.IMAGE_ID</code>.
     */
    public final TableField<ImageFolderRecord, Integer> IMAGE_ID = createField("IMAGE_ID", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>PUBLIC.IMAGE_FOLDER.FOLDER_ID</code>.
     */
    public final TableField<ImageFolderRecord, Integer> FOLDER_ID = createField("FOLDER_ID", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>PUBLIC.IMAGE_FOLDER.STATE</code>.
     */
    public final TableField<ImageFolderRecord, Byte> STATE = createField("STATE", org.jooq.impl.SQLDataType.TINYINT, this, "");

    /**
     * Create a <code>PUBLIC.IMAGE_FOLDER</code> table reference
     */
    public ImageFolder() {
        this("IMAGE_FOLDER", null);
    }

    /**
     * Create an aliased <code>PUBLIC.IMAGE_FOLDER</code> table reference
     */
    public ImageFolder(String alias) {
        this(alias, IMAGE_FOLDER);
    }

    private ImageFolder(String alias, Table<ImageFolderRecord> aliased) {
        this(alias, aliased, null);
    }

    private ImageFolder(String alias, Table<ImageFolderRecord> aliased, Field<?>[] parameters) {
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
    public ImageFolder as(String alias) {
        return new ImageFolder(alias, this);
    }

    /**
     * Rename this table
     */
    public ImageFolder rename(String name) {
        return new ImageFolder(name, null);
    }
}
