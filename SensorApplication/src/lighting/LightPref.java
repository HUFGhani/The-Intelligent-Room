package lighting;


public class LightPref {
	private Light light;
	private String actionMethod;
	private int actionPriority;


	public LightPref() {
	}

	public LightPref(Light light, String actionMethod, int actionPriority) {
		this.light = new Light(light);
		this.actionMethod = actionMethod;
		this.actionPriority = actionPriority;
	}

	public LightPref(LightPref pref) {
		this.actionMethod = pref.getActionMethod();
		this.actionPriority = pref.getActionPriority();
		this.light = new Light(pref.getLight());
	}

	public int getActionPriority() {
		return actionPriority;
	}

	public void setActionPriority(int actionPriority) {
		this.actionPriority = actionPriority;
	}


	public String getActionMethod() {
		return actionMethod;
	}

	public void setActionMethod(String actionMethod) {
		this.actionMethod = actionMethod;
	}

	public Light getLight() {
		return light;
	}

	public void setLight(Light light) {
		this.light = light;
	}

	@Override
	public String toString() {
		return "LightPref [light=" + light + ", actionMethod=" + actionMethod + ", actionPriority=" + actionPriority
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionMethod == null) ? 0 : actionMethod.hashCode());
		result = prime * result + actionPriority;
		result = prime * result + ((light == null) ? 0 : light.hashCode());
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
		LightPref other = (LightPref) obj;
		if (actionMethod == null) {
			if (other.actionMethod != null)
				return false;
		} else if (!actionMethod.equals(other.actionMethod))
			return false;
		if (actionPriority != other.actionPriority)
			return false;
		if (light == null) {
			if (other.light != null)
				return false;
		} else if (!light.equals(other.light))
			return false;
		return true;
	}
	
	
}
