package quarkus.online.summit.featuretoggles;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "features")
public interface Features {
    @WithName("new-user-interface-enabled")
    boolean newUserInterfacceEnbaled();

    @WithName("alternative-algorithm-enabled")
    boolean alternativeAlgorithmEnabled();
}
