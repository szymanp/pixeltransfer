/**
 * This class is generated by jOOQ
 */
package com.metapx.pixeltransfer.database.generated;


import javax.annotation.Generated;

import org.jooq.Sequence;
import org.jooq.impl.SequenceImpl;


/**
 * Convenience access to all sequences in PUBLIC
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>PUBLIC.SYSTEM_SEQUENCE_0DEAEC08_1E64_4D66_8182_141802E22A4C</code>
     */
    public static final Sequence<Long> SYSTEM_SEQUENCE_0DEAEC08_1E64_4D66_8182_141802E22A4C = new SequenceImpl<Long>("SYSTEM_SEQUENCE_0DEAEC08_1E64_4D66_8182_141802E22A4C", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT);

    /**
     * The sequence <code>PUBLIC.SYSTEM_SEQUENCE_6CE5C490_9DBC_46D9_B4CD_159866F97EFA</code>
     */
    public static final Sequence<Long> SYSTEM_SEQUENCE_6CE5C490_9DBC_46D9_B4CD_159866F97EFA = new SequenceImpl<Long>("SYSTEM_SEQUENCE_6CE5C490_9DBC_46D9_B4CD_159866F97EFA", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT);
}