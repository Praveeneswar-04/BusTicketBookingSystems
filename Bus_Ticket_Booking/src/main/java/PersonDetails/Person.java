package PersonDetails;

public class Person  {
       
	   private int User_Id;
       private String User_Name;
       private String password;
       private String Phone_number;
       private String Email_Id;
       private String Address;
       private String Gender;
       
       
	

	public Person( String user_Name,  String password, String phone_number,String Gender, String email_Id,String address) {
		

		this.User_Name = user_Name;
		this.password = password;
		this.Phone_number = phone_number;
		this.Email_Id = email_Id;
		this.Address = address;
		this.Gender=Gender;
	}
	
	public Person() {
		// TODO Auto-generated constructor stub
	}

	public Person( String user_Name2, String password2, String phone_number2, String email_Id2,
			String address2) {
		
		this.User_Name = user_Name2;
		this.password = password2;
		this.Phone_number = phone_number2;
		this.Email_Id = email_Id2;
		this.Address = address2;
	}

	public int getUser_Id() {
		return User_Id;
	}
	public void setUser_Id(int user_Id) {
		User_Id = user_Id;
	}
	public String getUser_Name() {
		return User_Name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getPhone_number() {
		return Phone_number;
	}
	public void setPhone_number(String phone_number) {
		Phone_number = phone_number;
	}
	public String getEmail_Id() {
		return Email_Id;
	}
	public void setEmail_Id(String email_Id) {
		Email_Id = email_Id;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	} 
  
	
       
}


