import java.util.*;
import java.io.*;

public class GTSimulator 
{
	public static void main(String[] args) 
	{
		//MENU VARS
		Scanner in = new Scanner(System.in);
		
		//FILE I/O
		String fileName;

		//GAME OBJECTS
		Game g;

		//MENU MESSAGE
		System.out.print("Welcome to G.A.T.E (Game Analysis & Tactics Engine)." 
			+ '\n');

		System.out.println("This is the alpha version - try our demo files!");
		System.out.println(" - 'example.gt' \n - 'massmatrix.gt' \n");
		System.out.println("Please note, matrices larger than 3x3 will not" +
			" be represented in the console.");
		System.out.print("Enter the name of a .gt file for analysis: ");
		fileName = in.nextLine();

		g = constructGame(fileName);
		
		System.out.print(g.toString());
	}

	/* 
	 * Given a filename, this method reads the file and constructs the file in
	 * such a manner that the program can analyze various aspects of the game.
	 *
	 * @param String fileName: name of the .gt file to analyze
	 *
	 * @return Game: A game object to analyze
	 */
	public static Game constructGame(String fileName)
	{
		//GAME COMPONENTS
		String gameName;
		String payoffEntry;
		ArrayList<Agent> players = new ArrayList<Agent>();
		ArrayList<Action> actions = new ArrayList<Action>();
		String playerName;
		String [] actionEntry;
		
		Action newAction;

		Game newGame;
		File gameFile = new File(fileName);

		try
		{
			Scanner gt = new Scanner(gameFile);
			
			//NAME OF GAME
			gameName = gt.nextLine();

			//PAYOFF FUNCTION
			payoffEntry = gt.nextLine();

			int playerCount = 0;

			while(gt.hasNextLine())
			{
				//PLAYER NAME
				playerName = gt.nextLine();

				//PLAYER ACTIONS
				actionEntry = gt.nextLine().split(", ");

				//CREATE NEW PLAYER
				players.add(new Agent(playerName, playerCount));

				//CREATE ACTION
				for(int i = 0; i < actionEntry.length; i++)
				{
					newAction = new Action(actionEntry[i], 
						players.get(playerCount));
					newAction.setIndex(i);

					actions.add(newAction);
				}


				//ASSIGNING ACTIONS TO THEIR RESPECTIVE PLAYERS
				players.get(playerCount).assignActions(actions);

				playerCount++;

				actions.clear();
			}

			newGame = new Game(gameName, payoffEntry, players);

			return newGame;
		}
		catch(FileNotFoundException e)
		{
			System.out.print("File Not Found! Try again: ");
			return null;
		}
	}

	//Used to create massmatrix.gt
	public static void writeMassMatrix(String fileName)
	{
		Random rng = new Random();
		int newPayoff;

		File massMatrixFile = new File(fileName);

		PrintWriter pw;

		String toString = "";
		
		toString+= "MASS MATRIX\n";

		for(int i = 0; i < 26; i++)
			for(int j = 0; j < 26; j++)
			{
				
				for(int k = 0; k < 2; k++)
				{
					newPayoff = rng.nextInt(9);
					toString+= newPayoff;
					if(k < 1)
						toString+=",";
				}
				
				toString+=" ";
				
				if(j == 25)
					toString+="# ";
			}
		toString+="\n";

		toString+="ADAM\n";
		toString+="A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, " 
			+"T, U, V, W, X, Y, Z\n";
		toString+="BURT\n";
		toString+="A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, " 
			+"T, U, V, W, X, Y, Z\n";

		try
		{
			pw = new PrintWriter(massMatrixFile);

			pw.print(toString);
			
			pw.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File Not Found!");
		}	
	}	
}


