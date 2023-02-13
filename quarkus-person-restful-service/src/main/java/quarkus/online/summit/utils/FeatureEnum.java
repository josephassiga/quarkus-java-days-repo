package quarkus.online.summit.utils;

public enum FeatureEnum {
    /**
     *
     */
    INTERFACE("NEW USER INTERFACE ENABLED"),
    /**
     *
     */
    ALGORITHM("ALTERNATIVE ALGORITHM ENABLED"),
    /**
     *
     */
    NONE("This Feature '%s' is not available or enabled");

    private String name;

    private FeatureEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
