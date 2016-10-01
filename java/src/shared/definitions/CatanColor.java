package shared.definitions;

import java.awt.Color;
import com.google.gson.annotations.SerializedName;

public enum CatanColor
{
    @SerializeName("red")
	RED, 
    @SerializeName("orange")
    ORANGE, 
    @SerializeName("yellow")
    YELLOW, 
    @SerializeName("blue")
    BLUE, 
    @SerializeName("green")
    GREEN, 
    @SerializeName("purple")
    PURPLE, 
    @SerializeName("puce")
    PUCE, 
    @SerializeName("white")
    WHITE, 
    @SerializeName("brown")
    BROWN;
	
	private Color color;
    private String value;
	
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

        RED.value = "red";
        ORANGE.value = "orange";
        YELLOW.value = "yellow";
        BLUE.value = "blue";
        GREEN.value = "green";
        PURPLE.value = "purple";
        PUCE.value = "puce";
        WHITE.value = "white";
        BROWN.value = "brown";
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

