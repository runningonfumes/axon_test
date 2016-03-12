package demo;



import java.math.BigDecimal;

public class ValueStoreCreatedEvent {
    private final BigDecimal amount;
    private final String id;
    private final String partyId;

    public ValueStoreCreatedEvent(String id, String partyId, BigDecimal amount) {
        this.amount = amount;
        this.id = id;
        this.partyId = partyId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPartyId() {
        return partyId;
    }

    public String getId() {
        return id;
    }
}
