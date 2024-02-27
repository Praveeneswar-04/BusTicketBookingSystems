package SearchDetails;

public class Bus {

	private int Bus_Id;
	private int Bus_Number;
	private String Bus_Name;
	private int seat;
	private Double Price;
	private int Route_id;
	private String Driver_Name;
	
	public Bus(int bus_Id, int bus_Number, String bus_Name, int seat, Double price, int route_id, String driver_Name) {
		this.Bus_Id = bus_Id;
		this.Bus_Number = bus_Number;
		this.Bus_Name = bus_Name;
		this.seat = seat;
		this.Price = price;
		this.Route_id = route_id;
		this.Driver_Name = driver_Name;
	}

	public int getBus_Id() {
		return Bus_Id;
	}

	public void setBus_Id(int bus_Id) {
		Bus_Id = bus_Id;
	}

	public int getBus_Number() {
		return Bus_Number;
	}

	public void setBus_Number(int bus_Number) {
		Bus_Number = bus_Number;
	}

	public String getBus_Name() {
		return Bus_Name;
	}

	public void setBus_Name(String bus_Name) {
		Bus_Name = bus_Name;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public Double getPrice() {
		return Price;
	}

	public void setPrice(Double price) {
		Price = price;
	}

	public int getRoute_id() {
		return Route_id;
	}

	public void setRoute_id(int route_id) {
		Route_id = route_id;
	}

	public String getDriver_Name() {
		return Driver_Name;
	}

	public void setDriver_Name(String driver_Name) {
		Driver_Name = driver_Name;
	}

	

	@Override
	public String toString() {
		return String.format("%-8d%-24s%-16d%-20s%-8d%-8d%n", Bus_Id, Bus_Name, Bus_Number, Driver_Name, Route_id, seat);

	}

}

