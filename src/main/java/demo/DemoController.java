package demo;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
@RequestMapping("/step")
class DemoController {

    private static String johnId;
    private static String janeId;

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private EventSourcingRepository<ValueStore> valueStoreRepository;

    @RequestMapping(value = "/1", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void step1() {
        johnId = UUID.randomUUID().toString();
        janeId = UUID.randomUUID().toString();
        commandGateway.send(new CreateValueStoreCommand(johnId, "John", BigDecimal.TEN));
        commandGateway.send(new CreateValueStoreCommand(janeId, "Jane", BigDecimal.ZERO));

        commandGateway.send(new ActivateValueStoreCommand(johnId));
        commandGateway.send(new ActivateValueStoreCommand(janeId));
    }



}
