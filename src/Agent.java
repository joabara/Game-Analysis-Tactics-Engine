import java.util.*;

public class Agent 
{
	private final String name;
	private Action [] actions;
	private int playerIndex;

	public Agent(String name, int playerIndex)
	{
		//NAME
		this.name = name;

		//player index/id with respect to the game
		this.playerIndex = playerIndex;
	}

	/* 
	 * Method assigns actions to the player who can execute said
	 * actions.
	 *
	 * @param ArrayList<Action> actions that this player can execute
	*/
	public void assignActions(ArrayList<Action> actions)
	{
		this.actions = new Action [actions.size()];

		for(int i = 0; i < actions.size(); i++)
			this.actions[i] = actions.get(i);
		
	}

	/* 
	 * Standard accessor method for the player's name
	 *
	 * @return player's name in the game
	 */
	public String getName()
	{
		return this.name;
	}

	/* 
	 * Standard accessor method for the player's actions
	 *
	 * @return player's actions in the game
	 */
	public Action [] getActions()
	{
		return this.actions;
	}

	/* 
	 * Standard accessor method for the number of available actions to player
	 *
	 * @return number of available actions to the player in the game
	 */
	public int numActions()
	{
		return actions.length;
	}

	/* 
	 * Formal representation of an Agent's (Player) profile for analysis use
	 *
	 * @return String summary of the instance of player
	 */
	public String toString(boolean indent)
	{
		String toString = "";

		if(indent)
		{
			toString += "	NAME: " + getName() + '\n';
			toString += "	ACTIONS: ";

			for(int i = 0; i < actions.length; i++)
			{
				toString += actions[i].name;

				if(i+1 < actions.length)
					toString+=", ";
			}


			toString+='\n';
		}

		else
		{
			toString += "NAME: " + getName() + '\n';
			toString += "ACTIONS: ";

			for(int i = 0; i < actions.length; i++)
			{
				toString += actions[i].name;

				if(i+1 < actions.length)
					toString+=", ";
			}

			toString+='\n';
		}

		toString+='\n';

		return toString;
	}

	/* 
	 * The best response function is a critical component of game analysis.
	 * Given an opponent's action, the best reponse function is the 
	 * optimization of your choices.
	 *
	 * For example, if Player A plays A, Players B's best
	 * response/action is to play that which grants him the most utility
	 * (synonymous with satisfaction, payout, revenue, etc.)
	 *
	 * @param Game g: the game in question (and its payoff matrix)
	 * @param Action j_action: the opponents action in question
	 *
	 * @return best action given a hypothetical move from the other player
	 */	
	public Action bestResponse(Game g, Action j_action)
	{
		int maxUtility = 0;
		int utility;
		int index = 0;

		ArrayList<Integer> uProfile = new ArrayList<Integer>();
		
		for (int i = 0; i < actions.length; i++) 
		{
			uProfile.add(utility(actions[i], j_action, g));
		}

		maxUtility = Collections.max(uProfile);
		index = uProfile.indexOf(maxUtility);

		return this.actions[index];
	}

	/* 
	 * Given a Game g and with it, a payoff matrix, the utility function
	 * for both players is given by two parameters: 
	 * 
	 * - Player 1's move
	 * - Player 2's move
	 * 
	 * These actions act as an index in the payoff matrix to derive 
	 * the players' payout.
	 *
	 * @param Action i: this player's action
	 * @param Action j: opponent's action
	 *
	 * @return payout according to combination of actions
	 */	
	public int utility(Action i, Action j, Game g)
	{
		int [][][] u = g.getPayoffFunction();
		int utility = 0;

		switch(playerIndex)
		{
			case 0: utility = u[i.index][j.index][playerIndex];
					break;

			case 1: utility  = u[j.index][i.index][playerIndex];
					break;

			default: break;
		}

		return utility;
	}
}
