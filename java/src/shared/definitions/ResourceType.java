package shared.definitions;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public enum ResourceType implements Serializable
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
