public class Action 
{
	public final String name;
	public int index;
	public Agent actioner;

	public Action(String name, Agent a)
	{
		this.name = name;
		
		actioner = a;
	}

	public void setIndex(int i)
	{
		this.index = i;
	}

	public void setAgent(Agent a)
	{
		this.actioner = a;
	}
}
