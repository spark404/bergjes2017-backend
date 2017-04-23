package net.strocamp.bergjes;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import net.strocamp.bergjes.domain.chicken.NewChickenRequest;
import net.strocamp.bergjes.domain.chicken.NewChickenResponse;
import net.strocamp.bergjes.domain.resource.ResourceType;

import java.util.Map;

/**
 * Created by hugo on 23/04/2017.
 */
public class NewChickenRequestHandler extends AbstractRequestHandler implements RequestHandler<NewChickenRequest, NewChickenResponse> {
    @Override
    public NewChickenResponse handleRequest(NewChickenRequest newChickenRequest, Context context) {
        init(context);

        NewChickenResponse response = new NewChickenResponse();
        response.setSuccess(false);

        Map<String, Integer> resources = teamStatus.getResources();
        Integer eggAmount = resources.get(ResourceType.EGG.name());
        Integer woodAmount = resources.get(ResourceType.WOOD.name());
        Integer grainAmount = resources.get(ResourceType.GRAIN.name());
        Integer fenceAmount = resources.get(ResourceType.FENCE.name());
        Integer chickenAmount = resources.get(ResourceType.CHICKEN.name());
        if (eggAmount >= 1 && woodAmount >= 2 && grainAmount >= 5 && fenceAmount >= 3) {

            resources.put(ResourceType.EGG.name(), eggAmount - 1);
            resources.put(ResourceType.WOOD.name(), woodAmount - 2);
            resources.put(ResourceType.GRAIN.name(), grainAmount - 5);
            resources.put(ResourceType.FENCE.name(), fenceAmount - 3);
            resources.put(ResourceType.CHICKEN.name(), chickenAmount + 1);
            database.updateTeamStatus(teamStatus);
            response.setSuccess(true);
        }

        return response;
    }
}
