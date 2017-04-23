package net.strocamp.bergjes.domain.resource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by hugo on 11/04/2017.
 */
public enum ResourceType {
    EGG,
    WOOD,
    FENCE,
    GRAIN,
    BRICK,
    CHICKEN,
    RANDOM;

    private static final List<ResourceType> VALUES =
            Collections.unmodifiableList(Arrays.stream(values())
                    .filter(item -> !(RANDOM.equals(item) || CHICKEN.equals(item)))
                    .collect(Collectors.toList()));
    private static final int SIZE = VALUES.size();
    private static final Random RND = new Random();

    public static ResourceType randomResource()  {
        return VALUES.get(RND.nextInt(SIZE));
    }
}
