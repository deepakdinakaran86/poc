/**
 * Copyright (C) 2014 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stratio.ingestion.sink.kafka;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.google.common.io.Files;

import kafka.server.KafkaConfig;
import kafka.server.KafkaServerStartable;

public class KafkaServer {
//    public static final String KAFKA_PORT = "9092";
    public static final String KAFKA_PORT = System.getProperty("kafka.port");
    private static final int WAIT_SECONDS = 5;

    private KafkaServerStartable kafkaServer;

    public void start() throws Exception {

        File dir = Files.createTempDir();
        String dirPath = dir.getAbsolutePath();
        System.out.println("Storing Kafka files in " + dirPath);

        String localhostKafka = System.getProperty("kafka.ip");
        String localhostZookeeper = System.getProperty("zookeeper.ip");
        String portKafka = System.getProperty("kafka.port");
        String portZookeeper = System.getProperty("zookeeper.port");

        Properties properties = new Properties();
        properties.put("broker.id", "0");
        properties.put("host.name", "localhost");
//        properties.put("host.name", localhostKafka);
        properties.put("port", KAFKA_PORT);
        properties.put("log.dir", dirPath);
        properties.put("log.flush.interval.messages", "1");
//        properties.put("zookeeper.connect", "localhost:" + ZookeeperServer.CLIENT_PORT);
        properties.put("zookeeper.connect", localhostZookeeper+":" + ZookeeperServer.CLIENT_PORT);
//        properties.put("zookeeper.connect", "port:" + ZookeeperServer.CLIENT_PORT);
//        Integer clientPort = Integer.parseInt(System.getProperty("zookeeper.port"));
//        properties.put("zookeeper.connect", "localhost:" + clientPort);
        properties.put("replica.socket.timeout.ms", "1500");
        properties.put("auto.create.topics.enable", "true");
        properties.put("num.partitions", "1");

        KafkaConfig kafkaConfig = new KafkaConfig(properties);
        kafkaServer = new KafkaServerStartable(kafkaConfig);
        kafkaServer.startup();

        TimeUnit.SECONDS.sleep(WAIT_SECONDS);
    }

    public void shutdown() throws IOException {
        kafkaServer.shutdown();
    }
}
