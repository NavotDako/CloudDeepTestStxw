package MyMain;
import ActionTests.Extendsession;
import ActionTests.Install;
import ActionTests.Monitors;
import ActionTests.Openlogs;
import ActionTests.Reboot;
import ActionTests.StartVideo;
import ActionTests.SwipeAndClick;

public class Enums {
	
	
	public String [] Users = {"ayoubAdminDeepTest1","ayoubAdminDeepTest2","ayoubAdminDeepTest3","ayoubAdminDeepTest4","ayoubAdminDeepTest5","ayoubUserDeepTest1","ayoubUserDeepTest2","ayoubUserDeepTest3","ayoubProjectAdminDeepTest1","ayoubProjectAdminDeepTest2"};

	public String [] Project = {"ayoubProjectDeepTest1","ayoubProjectDeepTest2"};
	//	public String [] Devices = {"hadar.zarihan�s iPhone","Sony F3116","iPad(2)","LGE Nexus 5","samsung SM-G920F","samsung SM-G920F","iPhone","LGE LG-F180L","Green iPhone","LGE Nexus 4","samsung SM-G900H-mac","samsung SM-T560 -mac"};

	public Class [] Actions = {Install.class,Reboot.class,Extendsession.class,Openlogs.class,StartVideo.class,SwipeAndClick.class,Monitors.class};

	public String Password = "Experitest2012";

	public String hostName = "https://qacloud.experitest.com/index.html#/login";
	

}
