/**
 * 
 */
package util;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:config/schemagen.xml")
public class SchemaGen {

    //private static Logger LOG = Logger.getLogger(SchemaGen.class);
	@Autowired
	LocalSessionFactoryBean sessionFactory;
    @Test
    public void testGenerateSchema() {
    	Configuration configuration = sessionFactory.getConfiguration();
        configuration.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
        boolean newSchema = false;
        boolean dumpToDb = false;
        if (newSchema) {
            SchemaExport export = new SchemaExport(configuration);
            //export.setOutputFile("scripts/schema.sql");
            export.execute(true, dumpToDb, false, false);         
        } else {
            SchemaUpdate update = new SchemaUpdate(configuration);
            //update.setOutputFile("scripts/schema.update."+System.currentTimeMillis()+".sql");
            update.execute(true, dumpToDb);
                
        }
    }
}
