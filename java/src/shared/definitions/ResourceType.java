package shared.definitions;

import com.google.gson.annotations.SerializedName;

public enum ResourceType
{
    @SerializedName("wood")
	WOOD("wood"),
    @SerializedName("brick")
    BRICK("brick"), 
    @SerializedName("sheep")
    SHEEP("sheep"), 
    @SerializedName("wheat")
    WHEAT("wheat"), 
    @SerializedName("ore")
    ORE("ore");

    private transient String value;

    ResourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
