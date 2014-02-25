package com.poliana.config;

import com.poliana.core.users.User;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactoryBean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Configuration
@ComponentScan("com.poliana.core")
@Import({ImpalaConfig.class, RedisConfig.class, HiveConfig.class, MongoConfig.class, AWSConfig.class})
public class ApplicationConfig {

    public static final String CRM_NAME = "crm";
    /**
     * The root directory to which all uploads for the application are uploaded.
     */
    public static final File CRM_STORAGE_DIRECTORY = new File(SystemUtils.getUserHome(), CRM_NAME);
    /**
     * Things are first uploaded by the application server to this directory. it's a sort
     * of staging directory
     */
    public static final File CRM_STORAGE_UPLOADS_DIRECTORY = new File(CRM_STORAGE_DIRECTORY, "uploads");
    /**
     * When a profile photo is uploaded, the resultant, completely uploaded image is
     * stored in this directory
     */
    public static final File CRM_STORAGE_PROFILES_DIRECTORY = new File(CRM_STORAGE_DIRECTORY, "profiles");

    @PostConstruct
    public void setupStorage() throws Throwable {
        File[] files = {CRM_STORAGE_DIRECTORY, CRM_STORAGE_UPLOADS_DIRECTORY, CRM_STORAGE_PROFILES_DIRECTORY};
        for (File f : files) {
            if (!f.exists() && !f.mkdirs()) {
                String msg = String.format("you must create the profile photos directory ('%s') " +
                        "and make it accessible to this process. Unable to do so from this process.", f.getAbsolutePath());
                throw new RuntimeException(msg);
            }
        }
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(HibernateJpaVendorAdapter adapter, DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan(User.class.getPackage().getName());
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(adapter);
        return emf;
    }

    @Bean
    public org.springframework.transaction.PlatformTransactionManager transactionManager( EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.H2);
        adapter.setGenerateDdl(true);
        adapter.setShowSql(true);
        return adapter;
    }

    @Bean
    public DataSource dataSource() {

        ClassPathResource classPathResource = new ClassPathResource("/crm-schema-h2.sql");

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(classPathResource);

        EmbeddedDatabaseFactoryBean embeddedDatabaseFactoryBean = new EmbeddedDatabaseFactoryBean();
        embeddedDatabaseFactoryBean.setDatabasePopulator(resourceDatabasePopulator);
        embeddedDatabaseFactoryBean.setDatabaseName(ApplicationConfig.CRM_NAME);
        embeddedDatabaseFactoryBean.setDatabaseType(EmbeddedDatabaseType.H2);
        embeddedDatabaseFactoryBean.afterPropertiesSet();
        return embeddedDatabaseFactoryBean.getObject();
    }

}

@Configuration
@Profile({"production"})
class ProductionDataSourceConfiguration {

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.H2);
        adapter.setGenerateDdl(true);
        adapter.setShowSql(true);
        return adapter;
    }

    @Bean
    public DataSource dataSource() {

        ClassPathResource classPathResource = new ClassPathResource("/crm-schema-h2.sql");

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(classPathResource);

        EmbeddedDatabaseFactoryBean embeddedDatabaseFactoryBean = new EmbeddedDatabaseFactoryBean();
        embeddedDatabaseFactoryBean.setDatabasePopulator(resourceDatabasePopulator);
        embeddedDatabaseFactoryBean.setDatabaseName(ApplicationConfig.CRM_NAME);
        embeddedDatabaseFactoryBean.setDatabaseType(EmbeddedDatabaseType.H2);
        embeddedDatabaseFactoryBean.afterPropertiesSet();
        return embeddedDatabaseFactoryBean.getObject();
    }
}

@Configuration
@Profile({"default", "test"})
class EmbeddedDataSourceConfiguration {

    private Log log = LogFactory.getLog(getClass());

    @PostConstruct
    public void setupTestProfileImages() throws Exception {
        long userId = 5;
        File profilePhotoForUser5 = new File(ApplicationConfig.CRM_STORAGE_PROFILES_DIRECTORY, Long.toString(userId));
        if (!profilePhotoForUser5.exists()) {
            // copy the profile photo back
            String pathForProfilePhoto = "/sample-photos/spring-dog-2.png";
            ClassPathResource classPathResource = new ClassPathResource(pathForProfilePhoto);
            Assert.isTrue(classPathResource.exists(), "the resource " + pathForProfilePhoto + " does not exist");
            OutputStream outputStream = new FileOutputStream(profilePhotoForUser5);
            InputStream inputStream = classPathResource.getInputStream();
            try {
                IOUtils.copy(inputStream, outputStream);
            } finally {
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(outputStream);
            }
            log.debug("setup photo " + profilePhotoForUser5.getAbsolutePath() + " for the sample user #" + Long.toString(userId) + "'s profile photo.");
        }

        if (!profilePhotoForUser5.exists()) {
            throw new RuntimeException("couldn't setup profile photos at " + profilePhotoForUser5.getAbsolutePath() + ".");
        }
    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.H2);
        adapter.setGenerateDdl(true);
        adapter.setShowSql(true);
        return adapter;
    }

    /**
     * You can access this H2 database at <a href = "http://localhost:8080/admin/console">the H2 administration
     * console</a>.
     */
    @Bean
    public DataSource dataSource() {

        ClassPathResource classPathResource = new ClassPathResource("/crm-schema-h2.sql");

        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(classPathResource);

        EmbeddedDatabaseFactoryBean embeddedDatabaseFactoryBean = new EmbeddedDatabaseFactoryBean();
        embeddedDatabaseFactoryBean.setDatabasePopulator(resourceDatabasePopulator);
        embeddedDatabaseFactoryBean.setDatabaseName(ApplicationConfig.CRM_NAME);
        embeddedDatabaseFactoryBean.setDatabaseType(EmbeddedDatabaseType.H2);
        embeddedDatabaseFactoryBean.afterPropertiesSet();
        return embeddedDatabaseFactoryBean.getObject();
    }

}