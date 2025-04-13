package com.creditagricole.messageroutingapp.service;

import com.creditagricole.messageroutingapp.model.Message;
import com.creditagricole.messageroutingapp.repository.MessageRepository;
import jakarta.jms.BytesMessage;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class MQService {
    private final MessageRepository messageRepository;
    private final JmsTemplate jmsTemplate;
    private final String queueName;

    @Autowired
    public MQService(MessageRepository messageRepository,
                     JmsTemplate jmsTemplate,
                     @Value("${ibm.mq.queue}") String queueName) {
        this.messageRepository = messageRepository;
        this.jmsTemplate = jmsTemplate;
        this.queueName = queueName;
    }

    @JmsListener(destination = "${ibm.mq.queue}")
    public void onMessage(jakarta.jms.Message jmsMessage) {
        try {
            log.info("Message reçu via JmsListener: {}", jmsMessage.getJMSMessageID());
            log.info("Message reçu via JmsListener: {}", jmsMessage);
            Message message = convertJmsMessageToEntity(jmsMessage);
            messageRepository.save(message);
        } catch (Exception e) {
            log.error("Erreur lors du traitement du message JMS via JmsListener", e);
        }
    }


    private Message convertJmsMessageToEntity(jakarta.jms.Message jmsMessage) throws JMSException {
        String messageId = jmsMessage.getJMSMessageID();
        String content = "";

        // Extraction du contenu du message
        if (jmsMessage instanceof TextMessage) {
            content = ((TextMessage) jmsMessage).getText();
        } else if (jmsMessage instanceof BytesMessage) {
            BytesMessage bytesMessage = (BytesMessage) jmsMessage;
            byte[] bytes = new byte[(int) bytesMessage.getBodyLength()];
            bytesMessage.readBytes(bytes);
            content = new String(bytes);
        }

        // Créer l'entité Message
        Message message = new Message();
        message.setMessageId(messageId);
        String source = jmsMessage.getStringProperty("SOURCE_APP");
        message.setSource(source != null ? source : "UNKNOWN");
        message.setContent(content);
        message.setReceivedDate(LocalDateTime.now());
        message.setStatus("RECEIVED");

        return message;
    }
}
