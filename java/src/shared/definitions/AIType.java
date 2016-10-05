package shared.definitions;

import com.google.gson.annotations.SerializedName;

/**
 * The types of AI players that are available.
 */
public enum AIType
{
    @SerializedName("LARGEST_ARMY")
	LARGEST_ARMY;
	
	private transient String type;
	
	static
	{
        LARGEST_ARMY.type = "largest_army";
	}
	
	public String getType()
	{
		return type;
	}
}

