package gameBoard;

//회원 -> 이름, 나이, 폰 번호, 아이디, 비밀번호

public class UserInfo {
	String name;
	int age;
	String mobile;
	String id;
	String pw;
	int omok_win;  //***
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public void setAge(int age)
	{
		this.age = age;
	}
	
	public String getMobile()
	{
		return mobile;
	}
	
	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getPw()
	{
		return pw;
	}
	
	public void setPw(String pw)
	{
		this.pw = pw;
	}
	
	public int getOmokWin()   //***
	{ 
		return omok_win;
	}
	
	public void setOmokWin(int win)  //***
	{
		this.omok_win = win;
	}
	
}