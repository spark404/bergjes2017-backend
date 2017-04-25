package net.strocamp.bergjes;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.strocamp.bergjes.db.TeamStatus;
import net.strocamp.bergjes.domain.resource.Resource;
import net.strocamp.bergjes.domain.trade.TradeRequest;
import net.strocamp.bergjes.domain.trade.TradeResponse;
import net.strocamp.bergjes.exceptions.InsufficientResourcesException;
import net.strocamp.bergjes.exceptions.NoSuchTeamException;
import org.apache.log4j.Logger;

/**
 * Created by hugo on 25/04/2017.
 */
public class TradeRequestHandler extends AbstractRequestHandler implements RequestHandler<TradeRequest, TradeResponse> {
    private final static Logger LOG = Logger.getLogger(TradeRequestHandler.class);

    @Override
    public TradeResponse handleRequest(TradeRequest tradeRequest, Context context) {
        init(context);

        TeamStatus supplier = database.getTeamStatusById(tradeRequest.getSupplierId());
        if (supplier == null) {
            throw new NoSuchTeamException(String.format("Team %s bestaat helemaal niet, dit is verdacht...",
                    tradeRequest.getSupplierId()));
        }

        Resource suppliedResource = tradeRequest.getSuppliedResource();
        String resourceKey = suppliedResource.getResourceType().name();
        Integer supplierCurrentAmount =
                supplier.getResources().get(resourceKey);
        if (supplierCurrentAmount < suppliedResource.getAmount()) {
            throw new InsufficientResourcesException(String.format("Team %s heeft niet genoeg over om dit te kunnen geven",
                    tradeRequest.getSupplierId()));
        }

        // Update supplier
        supplier.getResources().put(resourceKey, supplierCurrentAmount - suppliedResource.getAmount());
        database.updateTeamStatus(supplier);

        // Update our own team
        Integer currentAmount = teamStatus.getResources().get(resourceKey);
        teamStatus.getResources().put(resourceKey, currentAmount + suppliedResource.getAmount());
        database.updateTeamStatus(teamStatus);

        LOG.info(String.format("[%s] Received %d %s from %s", teamStatus.getTeamName(),
                suppliedResource.getAmount(), resourceKey, supplier.getTeamName()));

        TradeResponse response = new TradeResponse();
        response.setGainedResource(tradeRequest.getSuppliedResource());
        return response;
    }
}
