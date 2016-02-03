public class NashEquilibrium 
{
	private Action [] actionProfile;

	public NashEquilibrium(Action[] actionProfile)
	{
		this.actionProfile = actionProfile;
	}

	public NashEquilibrium(Action i, Action j)
	{
		this.actionProfile = new Action [2];

		this.actionProfile[0] = i;
		this.actionProfile[1] = j;
	}

	@Override
	public String toString()
	{
		String toString = "";

		toString += "NASH EQUILIBRIUM @ ";

		for(int i = 0; i < this.actionProfile.length; i++)
		{
			toString += this.actionProfile[i].name;

			if(i + 1 < this.actionProfile.length)
				toString += ",";
		}

		toString+='\n';

		return toString;
	}
}
