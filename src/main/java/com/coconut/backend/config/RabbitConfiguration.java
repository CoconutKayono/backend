package com.coconut.backend.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Configuration
public class RabbitConfiguration {

    @Bean("directExchange")  //定义交换机Bean，可以很多个
    public Exchange exchange() {
        return ExchangeBuilder.directExchange("amq.direct").build();
    }

    @Bean("emailQueue")
    public Queue queue() {
        return QueueBuilder
                .durable("mail")
                .build();
    }

    @Bean("binding")
    public Binding binding(@Qualifier("directExchange") Exchange exchange,
                           @Qualifier("emailQueue") Queue queue) {
        //将我们刚刚定义的交换机和队列进行绑定
        return BindingBuilder
                .bind(queue)   //绑定队列
                .to(exchange)  //到交换机
                .with("my-mail")   //使用自定义的routingKey
                .noargs();
    }

    @Component
    class RabbitMessageConverter implements MessageConverter {

        @Override
        public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
            // 将Java对象转换为Json对象
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String json = objectMapper.writeValueAsString(object);
                return new Message(json.getBytes(), messageProperties);
            } catch (JsonProcessingException e) {
                throw new MessageConversionException("Failed to convert object to JSON", e);
            }
        }

        @Override
        public Object fromMessage(Message message) throws MessageConversionException {
            // 将Json对象转换为Java对象
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(message.getBody(), Object.class);
            } catch (IOException e) {
                throw new MessageConversionException("Failed to convert JSON to object", e);
            }
        }
    }

}
