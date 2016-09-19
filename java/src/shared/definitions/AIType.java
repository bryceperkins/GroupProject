package shared.definitions;

/**
 * The types of AI players that are available.
 */
public enum AIType
{
	LARGEST_ARMY;
	
	private String type;
	
	static
	{
        LARGEST_ARMY.type = "largest_army";
	}
	
	public String getType()
	{
		return type;
	}
}

