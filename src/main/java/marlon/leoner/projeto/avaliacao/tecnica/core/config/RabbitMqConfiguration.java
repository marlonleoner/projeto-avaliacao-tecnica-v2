package marlon.leoner.projeto.avaliacao.tecnica.core.config;


import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RabbitMqConfiguration {

    private final String host;
    private final String vhost;
    private final Integer port;
    private final String user;
    private final String password;

    public RabbitMqConfiguration(Environment env) {
        this.host = env.getProperty("spring.rabbitmq.host");
        this.vhost = env.getProperty("spring.rabbitmq.virtual-host");
        this.port = Integer.parseInt(env.getProperty("spring.rabbitmq.port"));
        this.user = env.getProperty("spring.rabbitmq.username");
        this.password = env.getProperty("spring.rabbitmq.password");
    }

    @Bean("rabbitMQContainer")
    public DirectMessageListenerContainer createContainer(@Qualifier("rabbitMQConnectionFactory") CachingConnectionFactory connectionFactory) {
        return new DirectMessageListenerContainer(connectionFactory);
    }

    @Bean("rabbitMQTemplate")
    public RabbitTemplate createRabbitTemplate(@Qualifier("rabbitMQConnectionFactory") CachingConnectionFactory factory) {
        return new RabbitTemplate(factory);
    }

    @Bean("rabbitMQConnectionFactory")
    public CachingConnectionFactory createConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(this.host);
        connectionFactory.setVirtualHost(this.vhost);
        connectionFactory.setPort(this.port);
        connectionFactory.setUsername(this.user);
        connectionFactory.setPassword(this.password);
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);

        return connectionFactory;
    }

}
