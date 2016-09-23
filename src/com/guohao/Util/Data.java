package com.guohao.Util;

public class Data {
	//是否检查更新 APP
	public static final String K_Auto_Update = "auto_update";
	public static final Boolean V_Auto_Updtae_Yes = true;
	public static final Boolean V_Auto_Updtae_No = false;
	
	//手机防盗，检查是否设置了密码
	public static final String K_Phone_Pwd = "phone_pwd";
	public static final String K_Temp_Phone_Pwd = "phone_pwd";
	public static final String V_Phone_Pwd_Default = "";
	
	//绑定 SIM 卡
	public static final String K_Phone_SIM_Serial = "sim_serial";
	public static final String K_Phone_SIM_Serial_Temp = "sim_serial_temp";
	public static final String V_Phone_SIM_Serial = "";
	//安全号码
	public static final String K_Phone_Security_Num = "security_num";
	public static final String K_Phone_Security_Num_Temp = "security_num_temp";
	public static final String V_Phone_Security_Num = "";
	//是否开启防盗保护
	public static final String K_Phone_Security_Start = "security_start";
	public static final String K_Phone_Security_Start_Temp = "security_start_temp";
	public static final Boolean V_Phone_Security_Start = false;
	//以上3个设置是否生效
	public static final String K_Phone_Security_Set_OK = "security_ok";
	public static final String K_Phone_Security_Set_OK_Temp = "security_ok_temp";
	public static final Boolean V_Phone_Security_Set_OK = false;
}
