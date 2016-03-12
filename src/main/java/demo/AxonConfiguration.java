package demo;


import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.commandhandling.interceptors.BeanValidationInterceptor;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.Arrays;

/**
 * Axon Java Configuration with reasonable defaults like SimpleCommandBus, SimpleEventBus and GenericJpaRepository.
 *
 * @author albert
 */
@Configuration
//@ImportResource("axon.xml")
public class AxonConfiguration {
    @Bean
    public AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor() {
        AnnotationEventListenerBeanPostProcessor processor = new AnnotationEventListenerBeanPostProcessor();
        processor.setEventBus(eventBus());
        return processor;
    }

    @Bean
    public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
        AnnotationCommandHandlerBeanPostProcessor processor = new AnnotationCommandHandlerBeanPostProcessor();
        processor.setCommandBus(commandBus());
        return processor;
    }

    @Bean
    public CommandBus commandBus() {
        SimpleCommandBus commandBus = new SimpleCommandBus();
        commandBus.setHandlerInterceptors(Arrays.asList(new BeanValidationInterceptor()));
//		commandBus.setTransactionManager(new SpringTransactionManager(transactionManager));
        return commandBus;
    }

    @Bean
    public EventBus eventBus() {
        return new SimpleEventBus();
    }

    @Bean
    public CommandGatewayFactoryBean<CommandGateway> commandGatewayFactoryBean() {
        CommandGatewayFactoryBean<CommandGateway> factory = new CommandGatewayFactoryBean<CommandGateway>();
        factory.setCommandBus(commandBus());
        return factory;
    }

    @Bean
    public EventSourcingRepository<ValueStore> ValueStoreRepository() {
        FileSystemEventStore eventStore = new FileSystemEventStore(new SimpleEventFileResolver(new File("data/evenstore")));
        EventSourcingRepository<ValueStore> repository = new EventSourcingRepository<ValueStore>(ValueStore.class, eventStore);
        repository.setEventBus(eventBus());
        return repository;
    }

    @Bean
    public AggregateAnnotationCommandHandler<ValueStore> ValueStoreCommandHandler() {
        AggregateAnnotationCommandHandler<ValueStore> commandHandler = AggregateAnnotationCommandHandler.subscribe(ValueStore.class, ValueStoreRepository(), commandBus());
        return commandHandler;
    }
}
