package shared.definitions;

public enum ResourceType
{
	WOOD, BRICK, SHEEP, WHEAT, ORE;

    private String value;

    static {
        WOOD.value = "wood";
        BRICK.value = "brick";
        SHEEP.value = "sheep";
        WHEAT.value = "wheat";
        ORE.value = "ore";
    }

    public String toString() {
        return value;
    }
}

