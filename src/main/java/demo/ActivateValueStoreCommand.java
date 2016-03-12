package demo;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class ActivateValueStoreCommand {

    @TargetAggregateIdentifier
    private String id;

    public ActivateValueStoreCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
