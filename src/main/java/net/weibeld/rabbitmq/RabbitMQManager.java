package net.weibeld.rabbitmq;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
import java.security.KeyManagementException;
import java.util.Map;


import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class RabbitMQManager {

    private static RabbitMQManager instance = null;
    private String mUri;
    private Connection mConnection;
    private Channel mChannel;

    protected RabbitMQManager() {}

    public static RabbitMQManager getInstance() {
        if (instance == null) instance = new RabbitMQManager();
        return instance;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public void setUriFromVar(String varName) {
        mUri = System.getenv(varName);
    }

    public void setUriFromVar(String varName, String defaultUri) {
        String varValue = System.getenv(varName);
        mUri = varValue == null ? defaultUri : varValue;
    }

    public void connect() {
        ConnectionFactory factory = new ConnectionFactory();

        try {
            factory.setUri(mUri);
        } catch (URISyntaxException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        try {
            mConnection = factory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

        try {
            mChannel = mConnection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Declare a queue on the open channel.
     */
    public void declareQueue(String name, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments) {
        try {
            mChannel.queueDeclare(name, durable, exclusive, autoDelete, arguments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Declare a server-named, non-durable, exclusive, autodelete queue on the
     * open channel.
     */
    public void declareQueue() {
        try {
            mChannel.queueDeclare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }

    public Channel getChannel() {
        return mChannel;
    }

    public Connection getConnection() {
        return mConnection;
    }

    public void close() {
        try {
            mChannel.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        try {
            mConnection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
