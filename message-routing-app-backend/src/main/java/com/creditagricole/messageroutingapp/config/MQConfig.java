package com.creditagricole.messageroutingapp.config;

import com.ibm.mq.jakarta.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.Session;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class MQConfig {
    @Value("${ibm.mq.host}")
    private String host;

    @Value("${ibm.mq.port}")
    private int port;

    @Value("${ibm.mq.queue-manager}")
    private String queueManager;

    @Value("${ibm.mq.channel}")
    private String channel;

    @Value("${ibm.mq.username:#{null}}")
    private String username;

    @Value("${ibm.mq.password:#{null}}")
    private String password;

    @Bean
    public MQQueueConnectionFactory mqQueueConnectionFactory() {
        MQQueueConnectionFactory factory = new MQQueueConnectionFactory();
        try {
            factory.setHostName(host);
            factory.setPort(port);
            factory.setQueueManager(queueManager);
            factory.setChannel(channel);
            factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);

            if (username != null && password != null) {
                factory.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
                factory.setStringProperty(WMQConstants.USERID, username);
                factory.setStringProperty(WMQConstants.PASSWORD, password);
            }
        } catch (JMSException e) {
            throw new RuntimeException("Failed to create MQ connection factory", e);
        }
        return factory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory(MQQueueConnectionFactory mqQueueConnectionFactory) {
        return new CachingConnectionFactory(mqQueueConnectionFactory);
    }

    @Bean
    public JmsTemplate jmsTemplate(CachingConnectionFactory cachingConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        jmsTemplate.setReceiveTimeout(2000);
        return jmsTemplate;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(
            CachingConnectionFactory cachingConnectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, cachingConnectionFactory);
        return factory;
    }

   /* @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(
            @Qualifier("cachingConnectionFactory") ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setSessionTransacted(false);
        factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        return factory;
    }*/
}
