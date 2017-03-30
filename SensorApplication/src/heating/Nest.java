package heating;

public class Nest {
	private double target_temperature_c;
	private boolean automated = true;

	public Nest(Nest nest) {
		this.target_temperature_c = nest.getTarget_temperature_c();
		this.automated = nest.isAutomated();

	}

	public Nest(double target_temperature_c, Boolean automated) {
		this.target_temperature_c = target_temperature_c;
		this.automated = automated;
	}

	public double getTarget_temperature_c() {
		return target_temperature_c;
	}

	public void setTarget_temperature_c(double target_temperature_c) {
		this.target_temperature_c = target_temperature_c;
	}

	public boolean isAutomated() {
		return automated;
	}

	public void setAutomated(boolean automated) {
		this.automated = automated;
	}

	public Nest() {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (automated ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(target_temperature_c);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Nest other = (Nest) obj;
		if (automated != other.automated)
			return false;
		if (Double.doubleToLongBits(target_temperature_c) != Double.doubleToLongBits(other.target_temperature_c))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Nest [target_temperature_c=" + target_temperature_c + ", automated=" + automated + "]";
	}


}
