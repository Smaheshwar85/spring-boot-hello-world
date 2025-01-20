package org.example;

import com.solacesystems.jcsmp.*;

public class SolaceMessagePublisher {

    private final JCSMPSession session;
    private final Queue queue;

    public SolaceMessagePublisher(String solaceHost, String vpnName, String username, String password, String queueName) throws JCSMPException {
        JCSMPProperties properties = new JCSMPProperties();
        properties.setProperty(JCSMPProperties.HOST, "tcp://host.docker.internal:55555");
        properties.setProperty(JCSMPProperties.VPN_NAME, "");
        properties.setProperty(JCSMPProperties.USERNAME, "admin");
        properties.setProperty(JCSMPProperties.PASSWORD, "admin");

        this.session = JCSMPFactory.onlyInstance().createSession(properties);
        this.session.connect();
        this.queue = JCSMPFactory.onlyInstance().createQueue(queueName);
    }

    public void publishMessage(String messageContent) {
        try {
            XMLMessageProducer producer = session.getMessageProducer(new JCSMPStreamingPublishEventHandler() {
                @Override
                public void handleError(String s, JCSMPException e, long l) {

                }

                @Override
                public void responseReceived(String s) {

                }
            });
            TextMessage message = JCSMPFactory.onlyInstance().createMessage(TextMessage.class);
            message.setText(messageContent);
            producer.send(message, queue);
            System.out.println("Message published to Solace queue: " + messageContent);
        } catch (JCSMPException e) {
            System.err.println("Error publishing message to Solace: " + e.getMessage());
        }
    }

    public void close() {
        if (session != null) {
            session.closeSession();
        }
    }
}
