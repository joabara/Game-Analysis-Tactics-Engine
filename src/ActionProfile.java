
public class ActionProfile 
{	
	public Action [][] bestResponses;

	public ActionProfile(int numJActions, int numPlayers)
	{
		this.bestResponses = new Action[numJActions][numPlayers];
	}

	@Override
	public String toString()
	{
		String toString = "";

		for(int i = 0; i < bestResponses.length; i++)
		{
			for (int j = 0; j < this.bestResponses[i].length; j++) 
			{
				toString+=bestResponses[i][j].name;

				if(j+1 < bestResponses[i].length)
					toString+=",";
			}
			
			toString+='\n';
		}
		
		return toString;
	}
}
