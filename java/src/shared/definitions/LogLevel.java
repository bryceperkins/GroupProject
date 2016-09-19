package shared.definitions;

/**
 * The logging levels that are accepted by the Server.
 */
public enum LogLevel
{
	SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST;
	
	private String level;
	
	static
	{
        SEVERE.level = "severe";
        WARNING.level = "warning";
        INFO.level = "info";
        CONFIG.level = "config";
        FINE.level = "fine";
        FINER.level = "finer";
        FINEST.level = "finest";
	}
	
	public String getLevel()
	{
		return level;
	}
}

