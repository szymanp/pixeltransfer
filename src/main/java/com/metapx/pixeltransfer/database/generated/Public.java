/**
 * This class is generated by jOOQ
 */
package com.metapx.pixeltransfer.database.generated;


import com.metapx.pixeltransfer.database.generated.tables.Folder;
import com.metapx.pixeltransfer.database.generated.tables.Image;
import com.metapx.pixeltransfer.database.generated.tables.ImageFolder;
import com.metapx.pixeltransfer.database.generated.tables.System;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


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
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1500404757;

    /**
     * The reference instance of <code>PUBLIC</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>PUBLIC.SYSTEM</code>.
     */
    public final System SYSTEM = com.metapx.pixeltransfer.database.generated.tables.System.SYSTEM;

    /**
     * The table <code>PUBLIC.IMAGE</code>.
     */
    public final Image IMAGE = com.metapx.pixeltransfer.database.generated.tables.Image.IMAGE;

    /**
     * The table <code>PUBLIC.FOLDER</code>.
     */
    public final Folder FOLDER = com.metapx.pixeltransfer.database.generated.tables.Folder.FOLDER;

    /**
     * The table <code>PUBLIC.IMAGE_FOLDER</code>.
     */
    public final ImageFolder IMAGE_FOLDER = com.metapx.pixeltransfer.database.generated.tables.ImageFolder.IMAGE_FOLDER;

    /**
     * No further instances allowed
     */
    private Public() {
        super("PUBLIC", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        List result = new ArrayList();
        result.addAll(getSequences0());
        return result;
    }

    private final List<Sequence<?>> getSequences0() {
        return Arrays.<Sequence<?>>asList(
            Sequences.SYSTEM_SEQUENCE_90B0090A_7A87_405F_B9E1_EAD9D80B28C8,
            Sequences.SYSTEM_SEQUENCE_B0A71AEC_5D62_45DF_B9E5_3A37FA9FBD70);
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            System.SYSTEM,
            Image.IMAGE,
            Folder.FOLDER,
            ImageFolder.IMAGE_FOLDER);
    }
}
