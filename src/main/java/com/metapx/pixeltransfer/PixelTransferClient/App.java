package com.metapx.pixeltransfer.PixelTransferClient;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.metapx.pixeltransfer.database.ConnectionFactory;
import com.metapx.pixeltransfer.database.DatabaseBuilder;
import com.metapx.pixeltransfer.database.generated.Tables;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        
        DatabaseBuilder.buildFile("test");
        
        DSLContext db = DSL.using(ConnectionFactory.newConnection("test"), SQLDialect.H2);
        Record var = db.select().from(Tables.SYSTEM).where(Tables.SYSTEM.ID.eq(1)).fetchOne();
        System.out.println(var.get(Tables.SYSTEM.SCHEMA_VERSION));
    }
}
