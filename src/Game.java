import java.util.*;
import java.lang.*;

public class Game 
{
	private String name;
	private ArrayList<Agent> players;
	private int [][][] payoffMatrix;
	public int numPlayers;
	private ArrayList<NashEquilibrium> ne;

	public Game(String name, String payoff, ArrayList<Agent> players)
	{
		//GAME NAME
		this.name = name;

		//GAME PLAYERS
		this.players = players;
		numPlayers = players.size();

		//GAME PAYOFF FUNCTIONs
		payoffMatrix = constructPayoffFunction(payoff);

		//SOLUTIONS
		this.ne = getNashEquilibria();
	}

	/* 
	 * Standard accessor method for the PLAYERS
	 *
	 * @return PLAYERS in the game
	 */
	public ArrayList<Agent> getPlayers()
	{
		return players;
	}

	/* 
	 * Constructs the payoff/outcome function. This is used to 
	 *
	 * @param String fileName: name of the .gt file to analyze
	 *
	 * @return Game: A game object to analyze
	 */
	public int [][][] constructPayoffFunction(String fileMatrix)
	{
		int [] playerActions = {players.get(0).numActions(), 
			players.get(1).numActions()};

		int [][][] payoffFunction = 
		new int[playerActions[0]][playerActions[1]][numPlayers];

		String [] row;
		String [] collumn;
		String [] payoff;

		row = fileMatrix.split(" # ");
		for(int i = 0; i < payoffFunction.length; i++)
		{
			collumn = row[i].split(" ");
			for(int j = 0; j < payoffFunction[i].length; j++)
			{
				payoff = collumn[j].split(",");
				for (int k = 0;	k < payoffFunction[i][j].length; k++) 
				{
					payoffFunction[i][j][k] = Integer.parseInt(payoff[k]);
				}
			}
		}

		return payoffFunction;
	}
	
	/* 
	 * Standard accessor method for the PAYOFF FUNCTION
	 *
	 * @return PAYOFF FUNCTION in the game
	 *
	 */
	public int [][][] getPayoffFunction()
	{
		return this.payoffMatrix;
	}
	
	/* 
	 * Returns the game object in string form - used to create a summary and an
	 * analysis of the game
	 *
	 * @return String representation of an instance of the Game object
	 */
	@Override
	public String toString()
	{
		boolean indent = false;
		String toString = "";

		toString+="-------------------------------------------------------\n";
		toString+="GAME NAME: " + this.name + '\n';	
		toString+="-------------------------------------------------------\n";
		indent = true;

		toString+= playersToString();
		toString+= actionsToString();
		
		toString+='\n';
		
		for(int i = 0; i < players.size(); i++) 
			toString+= players.get(i).toString(indent);

		toString+='\n';

		if(payoffMatrix.length < 3 && payoffMatrix[0].length < 3)
			toString += payoffToString() + '\n';

		toString += solutionsToString();

		return toString;
	}

	/* 
	 * Method to access the PLAYERS in the formal math format
	 *
	 * @return PLAYER summary
	 */
	public String playersToString()
	{
		String toString = "";

		toString+="PLAYERS: ";

		toString+= "[";

		for (int i = 0; i < numPlayers; i++) 
		{
			
			toString+=players.get(i).getName();

			if(i+1 < numPlayers)
				toString+=", ";
		}

		toString+="]";
		toString+='\n';

		return toString;
	}

	/* 
	 * Method to access the ACTIONS in the formal math format
	 *
	 * @return ACTION summary
	 */
	public String actionsToString()
	{
		String toString = "";

		toString+="ACTIONS: ";

		for (int i = 0; i < numPlayers; i++) 
		{
			toString+="{";

			for (int j = 0; j < getPlayers().get(i).getActions().length; j++)
			{
				toString+=(getPlayers().get(i).getActions()[j].name);

				if(j+1 < getPlayers().get(i).getActions().length)
					toString+=",";
			}

			toString+="}";

			if(i+1 < numPlayers)
				toString+=" x ";
			
		}

		toString+='\n';

		return toString;
	}

	/* 
	 * Method to access the PAYOFF FUNCTION in the formal math format
	 *
	 * @return PAYOFF FUNCTION summary
	 */
	public String payoffToString()
	{
		String toString = "";

		toString += "PAYOFF MATRIX: \n";

		Agent player1 = players.get(0);
		Agent player2 = players.get(1);

		toString +=  "__|";
		for(int q = 0; q < player2.getActions().length; q++)
			toString += "_" + player2.getActions()[q].name + "_|";

		toString += '\n';

		for(int i = 0; i < payoffMatrix.length; i++)
		{
			toString += player1.getActions()[i].name + " |";
			for(int j = 0; j < payoffMatrix[i].length; j++)
			{
				for(int k = 0; k < payoffMatrix[i][j].length; k++)
				{
					toString += payoffMatrix[i][j][k];

					if(k+1 < payoffMatrix[i][j].length)
						toString+=",";
				}

				toString += "|";
			}
			toString += '\n';
		}
		
		return toString;
	}

	/* 
	 * Method to access the SOLUTIONS in the formal math format
	 *
	 * @return SOLUTION summary
	 */
	public String solutionsToString()
	{
		String toString = "";

		toString += "SOLUTIONS: (" + ne.size() + ") "+ '\n';
		toString +="------------------------------------------------------\n";
		toString += "PURE: \n";

		for (int q = 0; q < ne.size(); q++)
			toString += "- " + ne.get(q).toString();

		return toString;
	}

	/* 
	 * Method that parses through the payoff matrix and finds all PURE Nash
	 * Equilibrium (commonly abbreviated as NE), and adds them to the list of
	 * all NE's in the game.
	 *
	 * Note: Implemented only for 2D matrices (games with two players),
	 * update coming soon
	 *
	 * @return ArrayList of all pure NE's
	 */
	public ArrayList<NashEquilibrium> getNashEquilibria()
	{
		ArrayList<NashEquilibrium> nes = new ArrayList<NashEquilibrium>();
		Action a1;
		Action r2;
		Agent p1 = players.get(0);
		Agent p2 = players.get(1);

		for(int i = 0; i < p2.getActions().length; i++)
		{
			a1 = p1.bestResponse(this, p2.getActions()[i]);

			r2 = p2.bestResponse(this, a1);

			if(r2.equals(p2.getActions()[i]))
				nes.add(new NashEquilibrium(a1, r2));
		}

		return nes;
	}
}
