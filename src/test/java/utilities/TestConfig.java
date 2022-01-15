package utilities;
public class TestConfig{


	
	public static String server="smtp.gmail.com";
	public static String from = "denise.bumbacea@gmail.com";
	public static String password = "XXXXXXX";
	public static String[] to ={"denise093@yahoo.com","denise.bumbacea@yahoo.com"};
	public static String subject = "Extent Project Report";
	
	public static String messageBody ="TestMessage";
	public static String attachmentPath="D:\\WorkSpace\\DataDrivenFramework\\target\\surefire-reports\\html\\extent.html";
	public static String attachmentName="extent.html";
	
	
	
	//SQL DATABASE DETAILS	
	public static String driver="net.sourceforge.jtds.jdbc.Driver"; 
	public static String dbConnectionUrl="jdbc:jtds:sqlserver://192.101.44.22;DatabaseName=monitor_eval"; 
	public static String dbUserName="sa"; 
	public static String dbPassword="$ql$!!1"; 
	
	
	//MYSQL DATABASE DETAILS
	public static String mysqldriver="com.mysql.jdbc.Driver";
	public static String mysqluserName = "root";
	public static String mysqlpassword = "selenium";
	public static String mysqlurl = "jdbc:mysql://localhost:3306/acs";
	
	
	
	
	
	
	
	
	
}
