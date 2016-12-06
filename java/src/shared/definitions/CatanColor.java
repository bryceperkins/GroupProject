package shared.definitions;

import java.awt.Color;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public enum CatanColor implements Serializable 
{
    @SerializedName("red")
	RED("red"), 
    @SerializedName("orange")
    ORANGE("orange"), 
    @SerializedName("yellow")
    YELLOW("yellow"), 
    @SerializedName("blue")
    BLUE("blue"), 
    @SerializedName("green")
    GREEN("green"), 
    @SerializedName("purple")
    PURPLE("purple"), 
    @SerializedName("puce")
    PUCE("puce"), 
    @SerializedName("white")
    WHITE("white"), 
    @SerializedName("brown")
    BROWN("brown");
	
	private transient Color color;
    private transient String value;

    CatanColor(String value) {
        this.value = value;
    }
	
	static
	{
		RED.color = new Color(227, 66, 52);
		ORANGE.color = new Color(255, 165, 0);
		YELLOW.color = new Color(253, 224, 105);
		BLUE.color = new Color(111, 183, 246);
		GREEN.color = new Color(109, 192, 102);
		PURPLE.color = new Color(157, 140, 212);
		PUCE.color = new Color(204, 136, 153);
		WHITE.color = new Color(223, 223, 223);
		BROWN.color = new Color(161, 143, 112);
	}
	
	public Color getJavaColor()
	{
		return color;
	}
	
    public String getValue()
	{
		return value;
	}
}

