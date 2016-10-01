package shared.commands;

public class RollNumber extends MoveCommand{
    private int number;

    public RollNumber(int index, int number){
        super("rollNumber", index);
        this.number = number;
    };
}
