package kz.thquiet.data_streaming.config;

import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Setter
@Configuration
public class RabbitConfiguration {

        @Value("${queue.name}")
        private String queueName;

        @Value("${rabbit.queue.name}")
        private String jsonQueueName;

        @Value("${spring.rabbitmq.username}")
        private String username;

        @Value("${spring.rabbitmq.password}")
        private String password;

        @Value("${spring.rabbitmq.virtual-host}")
        private String virtualHost;

        @Value("${spring.rabbitmq.host}")
        private String host;

        @Value("${data.routing.key}")
        private String routingKey;

        @Value("${rabbit.routing.json.name}")
        private String routingJsonKey;

        @Value("${data.exchange}")
        private String exchange;

        @Bean
        public ConnectionFactory connectionFactory() {
            CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
            cachingConnectionFactory.setUsername(username);
            cachingConnectionFactory.setPassword(password);
            cachingConnectionFactory.setVirtualHost(virtualHost);
            return cachingConnectionFactory;
        }

        @Bean
        public AmqpAdmin amqpAdmin() {
            return new RabbitAdmin(connectionFactory());
        }

    public static final String EXCHANGE_NAME = "app-exchange";

        @Bean
        public Queue myQueue() {
            return new Queue(queueName);
        }

        @Bean("jsonQueueName")
        public Queue jsonQueue(){
            return new Queue(jsonQueueName);
        }

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

        @Bean
        public DirectExchange exchange() {
            return new DirectExchange(exchange,true , false);
        }

        @Bean
        public Binding binding() {
            return BindingBuilder
                    .bind(myQueue())
                    .to(exchange())
                    .with(routingKey);
        }

        @Bean
        public Binding jsonBinding(@Qualifier("jsonQueueName") Queue queue) {
            return BindingBuilder
                    .bind(queue)
                    .to(exchange())
                    .with(routingJsonKey);
        }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
