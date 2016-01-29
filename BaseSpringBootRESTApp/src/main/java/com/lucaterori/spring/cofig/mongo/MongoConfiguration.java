package com.lucaterori.spring.cofig.mongo;

import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Profile("default")
@Configuration
@EnableConfigurationProperties(MongoConfiguration.PersistenceProperties.class)
//@EnableMongoRepositories(basePackageClasses = DefaultMongoConfig.class)
@EnableMongoRepositories(basePackages = "com.lucaterori.repositories")
public class MongoConfiguration extends AbstractMongoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MongoConfiguration.class);

    @ConfigurationProperties(prefix = "mongo.persistence") // in properties.yml
    public static class PersistenceProperties {
        String databaseName;
        List<String> databaseHosts;
        String login;
        String password;
        int acceptableLatencyDifference = 150;
        int connectTimeout = 300;

        public void setDatabaseName(String databaseName) {
            this.databaseName = databaseName;
        }

        public void setDatabaseHosts(List<String> databaseHosts) {
            this.databaseHosts = databaseHosts;
        }

        public List<String> getDatabaseHosts() {
            return databaseHosts;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setAcceptableLatencyDifference(int acceptableLatencyDifference) {
            this.acceptableLatencyDifference = acceptableLatencyDifference;
        }

        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }
        @Override
        public  String toString(){
            return "db:" + databaseName + "," + databaseHosts;
        }
    }

    @Autowired
    private PersistenceProperties persistenceProperties;

   /* @Value("${application.imageBlacklistTtl:900}")
    private int imageBlacklistTtl;
    @Value("${application.installedStatusTtl:86400}")
    private int installedStatusTtl;
    @Value("${application.catalogCacheTtl:600}")
    private int catalogCacheTtl;
*/

    @Override
    protected String getDatabaseName() {
        return persistenceProperties.databaseName;
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.lucaterori.domains";
    }

    @Bean
    @Override
    public Mongo mongo() throws Exception {

        /* // Java8
        List<ServerAddress> serverAddresses = persistenceProperties.databaseHosts.stream()
                .map(hostname -> {
                    try {
                        return new ServerAddress(hostname);
                    } catch (UnknownHostException e) {
                        logger.error("Cannot resolve host {}", hostname);
                        return null;
                    }
                })
                .filter(s -> s != null)
                .collect(Collectors.toList());
        */

        System.out.println(":::::::::::PersistenceProperties>" + persistenceProperties);
        List<String> serverAddressesStrLst = persistenceProperties.databaseHosts != null ? persistenceProperties.databaseHosts : new ArrayList<String>();
        List<ServerAddress> serverAddresses = new ArrayList<>();
        for(String serverAddress : serverAddressesStrLst){
            try {
                if(serverAddress != null)
                    serverAddresses.add(new ServerAddress(serverAddress));
            } catch (UnknownHostException e) {
                logger.error("Cannot resolve host {}", serverAddress);
            }
        }


        if (serverAddresses.size() == 0) {
            logger.error("No valid Mongo hosts, aborting startup");
            throw new IllegalStateException("No valid Mongo hosts, aborting startup");
        }

        MongoClient client;

        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        //builder.localThreshold(persistenceProperties.acceptableLatencyDifference);
        builder.connectTimeout(persistenceProperties.connectTimeout);

        if (serverAddresses.size() > 1) {
            builder.writeConcern(WriteConcern.MAJORITY);
        } else {
            builder.writeConcern(WriteConcern.JOURNALED);
        }

        MongoClientOptions options = builder.build();

        if (serverAddresses.size() > 1) {
            if (persistenceProperties.login != null && !persistenceProperties.login.isEmpty()) {

                client = new MongoClient(serverAddresses,
                        Arrays.asList(MongoCredential.createMongoCRCredential(persistenceProperties.login, persistenceProperties.databaseName, persistenceProperties.password.toCharArray())),
                        options);
            } else {
                client = new MongoClient(serverAddresses, options);
            }


            client.setWriteConcern(WriteConcern.MAJORITY);
            client.setReadPreference(ReadPreference.primaryPreferred());
        } else {
            if (persistenceProperties.login != null && !persistenceProperties.login.isEmpty()) {
                client = new MongoClient(serverAddresses.get(0),
                        Arrays.asList(MongoCredential.createMongoCRCredential(persistenceProperties.login, persistenceProperties.databaseName, persistenceProperties.password.toCharArray())),
                        options);
            } else {
                client = new MongoClient(serverAddresses.get(0), options);
            }

            client.setWriteConcern(WriteConcern.JOURNALED);
        }
        return client;
    }

    @Override
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = super.mongoTemplate();
        mongoTemplate.setWriteResultChecking(WriteResultChecking.EXCEPTION);

        //ensureTtlIndex(mongoTemplate, ImageDownloadAttempt.class, "time", imageBlacklistTtl);
        //ensureTtlIndex(mongoTemplate, InstalledStatus.class, "computed", installedStatusTtl);

        return mongoTemplate;
    }


    /*private void ensureTtlIndex(MongoTemplate mongoTemplate, Class<?> objectClass, String ttlFieldName, int ttlSeconds) {
        IndexInfo index = mongoTemplate.indexOps(objectClass).getIndexInfo().stream().filter(indexInfo -> indexInfo.getName().equals(ttlFieldName)).findFirst().orElse(null);
        if (index == null) {
            mongoTemplate.indexOps(objectClass).ensureIndex(new Index().on(ttlFieldName, Sort.Direction.ASC).named(ttlFieldName).expire(ttlSeconds, TimeUnit.SECONDS));
        } else {
            logger.info("Index on {}.{} already exists", objectClass.getName(), ttlFieldName);
        }
    }*/


}