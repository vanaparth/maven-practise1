package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
class PubSubService
{
    private static final Logger LOGGER = Logger.getLogger(PubSubService.class);

    private PubSubService()
    {
    }

    static PubSubService getInstance()
    {
        return new PubSubService();
    }

    private ConnectionFactory getConnectionFactory()
    {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost(ApplicationConfiguration.getKeystoneRabbitHost());
        connectionFactory.setPort(Integer.parseInt(ApplicationConfiguration.getKeystoneRabbitPort()));
        connectionFactory.setUsername(ApplicationConfiguration.getKeystoneRabbitUser());
        connectionFactory.setPassword(ApplicationConfiguration.getKeystoneRabbitPassword());
        connectionFactory.setVirtualHost(ApplicationConfiguration.getKeystoneRabbitVirtualHost());

        return connectionFactory;
    }

    void createExchange(String exchange, String type, boolean durable) throws Exception
    {
        Connection connection = getConnectionFactory().newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchange, type, durable, false, null);

        close(channel, connection);
    }

    void createQueue(String exchange, String queue) throws Exception
    {
        Connection connection = getConnectionFactory().newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queue, true, false, false, null);
        channel.queueBind(queue, exchange, getRoutingKey(queue));

        close(channel, connection);
    }

    private void close(Channel channel, Connection connection)
    {
        close(channel);
        close(connection);
    }

    private void close(Channel channel)
    {
        try
        {
            channel.close();
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void close(Connection connection)
    {
        try
        {
            connection.close();
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private String getRoutingKey(String queue)
    {
        return "*.*.*.*.*." + queue;
    }
}