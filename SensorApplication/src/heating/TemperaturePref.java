package heating;

public class TemperaturePref {
	private Nest nest;
	private String actionMethod;
	private int actionPriority;

	public TemperaturePref(){}
	
	public TemperaturePref(TemperaturePref temp){
		this.nest = new Nest(temp.getNest());
		this.actionMethod = temp.getActionMethod();
		this.actionPriority = temp.getActionPriority();
	}
	
	public TemperaturePref(Nest nest, String actionMethod, int actionPriority) {
		this.nest = new Nest(nest);
		this.actionMethod = actionMethod;
		this.actionPriority = actionPriority;
	}

	public int getActionPriority() {
		return actionPriority;
	}

	public void setActionPriority(int actionPriority) {
		this.actionPriority = actionPriority;
	}

	public Nest getNest() {
		return nest;
	}

	public void setNest(Nest nest) {
		this.nest = new Nest(nest);
	}

	public String getActionMethod() {
		return actionMethod;
	}

	public void setActionMethod(String actionMethod) {
		this.actionMethod = actionMethod;
	}

	@Override
	public String toString() {
		return "Temperature [nest=" + nest + ", actionMethod=" + actionMethod + ", actionPriority=" + actionPriority
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionMethod == null) ? 0 : actionMethod.hashCode());
		result = prime * result + actionPriority;
		result = prime * result + ((nest == null) ? 0 : nest.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TemperaturePref other = (TemperaturePref) obj;
		if (actionMethod == null) {
			if (other.actionMethod != null)
				return false;
		} else if (!actionMethod.equals(other.actionMethod))
			return false;
		if (actionPriority != other.actionPriority)
			return false;
		if (nest == null) {
			if (other.nest != null)
				return false;
		} else if (!nest.equals(other.nest))
			return false;
		return true;
	}



}
