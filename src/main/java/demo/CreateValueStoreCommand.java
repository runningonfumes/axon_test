package demo;

import java.math.BigDecimal;

public class CreateValueStoreCommand {
    private String id;
    private String partyId;
    private BigDecimal amount;

    public CreateValueStoreCommand(String id, String partyId, BigDecimal amount) {
        this.id = id;
        this.partyId = partyId;
        this.amount = amount;
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
