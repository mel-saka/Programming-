package etudess;
//store multiple videos
//can delete/add videos
//video manager has 1 instance

import java.util.ArrayList;
import java.util.Scanner;



//empty proxy implementation for this class. 
class Image{
}

/*
 * Class which allows getting and setting of the video details.
 * These are used on an instance of VideoDetails.
 * There is also a toString method override to print all the information. 
 */
class VideoDetails{	
	
	   //Creating properties of videodetails class	 
	   private int startFrame, endFrame;
	   private float fps;
	   private String videoFileName, videoDescription;
	   private Image previewImg;
	   
	
	   public int getstartFrame(){
	      return startFrame;
	   }
	   public int getendFrame(){
		  return endFrame;
	   }
	   public float getfps() {
		   return fps;
	   }
	   public String getvideoFileName() {
		   return videoFileName;
	   }
	   public String getvideoDescription() {
		   return videoDescription;
	   }
	   public Image getpreviewImg() {
		   return previewImg;
	   }
	
	   public void setstartFrame(int startFrame) {
		   this.startFrame = startFrame;
	   }
	   public void setendFrame(int endFrame) {
		   this.endFrame = endFrame;
	   }
	   public void setfps(float fps) {
		   this.fps = fps;
	   }
	   public void setvideoFileName(String videoFileName) {
		   this.videoFileName = videoFileName;
	   }
	   public void setvideoDescription(String videoDescription) {
		   this.videoDescription = videoDescription;
	   } 
	   public void setpreviewImg(Image previewImg) {
		   this.previewImg = previewImg;
	   }
	  
	 //making the toString method to print out details.
	   public String toString(){
	        return " Video details: startFrame = "+ startFrame + "\n endFrame = " + endFrame + "\n fps = " + fps + "\n videoFileName = " + videoFileName + "\n videoDescription = " + videoDescription ;
	   }
}


public class etude11 {

	  public static void main(String[] args) {
	    Manager onlyInstance = new Manager();
	    
	    
	    
	    Image imagefile = null; //video image implementation is responsibility of another department
	    
	    //hash map, nested arrayList
	    Scanner sc = new Scanner(System.in);
	    System.out.println("type 'add' to add a video \ntype 'delete' to delete video \ntype 'list' to see the list of videos");
	    while (sc.hasNext()) {
	    	String word = sc.nextLine();
	    if(word.equals("heya")) {
	    	 System.out.println("heya");
	    }
	    //if add, then input all the details for a video file.
	    if (word.equals("add")) {
	    	//create nestled instance of a different class
	    	VideoDetails instancetrial = new VideoDetails();
		    
	    	//prompt user input for all the details 
		    System.out.println("enter the start frame:");
		    int startframeinput = sc.nextInt();
		    instancetrial.setstartFrame(startframeinput); 
		    System.out.println("enter the end frame:");
		    int endframeinput = sc.nextInt();
		    instancetrial.setendFrame(endframeinput);
		    System.out.println("enter the fps:");
		    int fpsinput = sc.nextInt();
		    instancetrial.setfps(fpsinput);
		    instancetrial.setpreviewImg(imagefile);
		    System.out.println("enter the video description:");
		    String viddescriptioninput = sc.next();
		    instancetrial.setvideoDescription(viddescriptioninput);
		    System.out.println("enter the file name :");
		    String filenameinput = sc.next();
		    instancetrial.setvideoFileName(filenameinput);
		    System.out.println(instancetrial.toString());	
		    onlyInstance.addVideo(instancetrial);
	    }
	    //if list, show the size and list the names of the files.
	    if (word.equals("list")) {
	    	int size = onlyInstance.videosList.size();
	    	System.out.println(size);
	    	if (size == 0) {
	    		System.out.println("no videos are in the list");
	    	}
	    	for (int x=0; x< size; x++) {
	    		System.out.println("video " + (x+1) + " filename is called: " + onlyInstance.videosList.get(x).getvideoFileName());
	    	}
	    	
	    }
	  //if delete, ask for the file name and delete that instance from arraylist. 
	    if (word.equals("delete")) {
	    	int size = onlyInstance.videosList.size();
	    	if (size == 0) {
	    		System.out.println("no videos are in the list");
	    	}
	    	else {
	    		for (int x=0; x< size; x++) {
	    			System.out.println("which video do you want to delete?");
		    		System.out.println("video " + (x+1) + " filename is called: " + onlyInstance.videosList.get(x).getvideoFileName());
		    	}
	    		int choice = sc.nextInt();
	    		if (choice == 1) {
	    			onlyInstance.videosList.remove(choice-1);
	    			
	    		}
	    		size = onlyInstance.videosList.size();
	    		for (int x=0; x< size; x++) {
	    			System.out.println("remaining videos");
		    		System.out.println("video " + (x+1) + " filename is called: " + onlyInstance.videosList.get(x).getvideoFileName());
		    	}
	    	}
	    }
	 }//end of while loop
   }
}


/*
 * Manager class which creates the public arraylist to store all the videos
 * Also methods for deleting and adding videos to the arraylist. 
 */
class Manager {
    public ArrayList<VideoDetails> videosList;
    private static final Manager onlyinstance = new Manager();
    
 
    Manager(){
    	videosList = new ArrayList<VideoDetails>();
    }
    
    public static Manager getInstance(){
        return onlyinstance;
    }
  
    public void addVideo(VideoDetails f)
    {
    	videosList.add(f);
    }

    public void deleteVideo(VideoDetails f)
    {
    	videosList.remove(f);
    }
}



