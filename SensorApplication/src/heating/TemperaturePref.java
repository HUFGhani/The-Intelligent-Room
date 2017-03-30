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

}
