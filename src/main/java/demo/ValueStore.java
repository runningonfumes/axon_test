package demo;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import java.math.BigDecimal;

public class ValueStore extends AbstractAnnotatedAggregateRoot {

    private BigDecimal balance = BigDecimal.ZERO;
    private String merchantId;
    private boolean active = false;

    @AggregateIdentifier
    private String id;

    public ValueStore() {
    }

    @CommandHandler
    public ValueStore(CreateValueStoreCommand command) {
        apply(new ValueStoreCreatedEvent(command.getId(), command.getPartyId(), command.getAmount()));
    }

    @EventHandler
    public void on(ValueStoreCreatedEvent event) {
        System.out.println("Applying ValueStoreCreatedEvent");
        this.id = event.getId();
        this.balance = event.getAmount();
    }

    @CommandHandler
    public void debit(ActivateValueStoreCommand command) {
        apply(new ValueStoreActivatedEvent(command.getId()));
    }

    @EventHandler
    public void on(ValueStoreActivatedEvent event) {
        this.active = true;
    }


    public BigDecimal getBalance() {
        return balance;
    }
}
