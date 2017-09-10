import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class code {
	public static void main(String[] args) throws IOException, InterruptedException,NullPointerException {

	Host h = new Host();
	h.getTable();

	Server s = new Server();
	s.serve();

	Manager m=new Manager();
	m.showMenu();
	m.makeBill();
    }
}

class Host{
	public static int[] table;
	static int tables=5;
	static int cu=0;
	int emptytables;
	int waitCust;
	int i;

	public void getTable() throws IOException, InterruptedException,NullPointerException{
		System.out.println("\nEnter no.of Customers : ");
		Scanner sc=new Scanner(System.in);
		cu=sc.nextInt();

		table=new int[5];

		if(cu<=tables){
			for(i=0;i<cu;i++){
				table[i]=1;
			}
			emptytables = tables-cu;
			System.out.println(i+"tables are filled");
			TimeUnit.SECONDS.sleep(5);
		}
		else if(cu==0){
			for(i=0;i<cu;i++){
				table[i]=0;
			}
		}
		else{
			waitCust=cu-tables;
			System.out.println(waitCust+" customer waiting");
		}
    }
}

class Server{
	void serve() throws IOException, InterruptedException{
	  if(Host.cu!=0){
		 for(int j=0;j<Host.tables;j++){
		     if(Host.table[j] == 1){
		        System.out.println("Serves Water to customer at table"+(j+1));
		        TimeUnit.SECONDS.sleep(5);
		     }
	     }
	  }
	 else
		 System.exit(0);
    }

	void serveFood() throws IOException, InterruptedException{
	  System.out.println("Serving Food...");
	  TimeUnit.SECONDS.sleep(5);
	}
}

class  Manager{
	String[] str=new String[10];
	String line[]=new String[5];
    String items[][]=new String[5][];
    int bill[] = new int[100];

    Scanner sc = new Scanner(System.in);

	void showMenu() throws IOException, InterruptedException,NullPointerException{
		File fin = new File("menu.txt");
		BufferedReader br = new BufferedReader(new FileReader(fin));
		String line;
		 while ((line = br.readLine()) != null) {
		   System.out.println(line);
		 }
		 TimeUnit.SECONDS.sleep(5);
		 takeOrder();
	}

	void takeOrder() throws IOException, InterruptedException,NullPointerException{

		for(int k=0;k<Host.tables;k++){
			 if(Host.table[k] == 1){
				 System.out.println("\nPlease Order the items");
				 line[k] = sc.nextLine();
			     TimeUnit.SECONDS.sleep(5);
			 }
		 }
	     for(int i=0,j=0;i<Host.cu;i++){
			items[j++]=line[i].split(",");
	     }
         for(int i=0;i<Host.cu;i++){
			   System.out.println("\nItems ordered at "+(i+1)+" Table");
			   for(int j=0;j<items[i].length;j++){
					System.out.println(items[i][j]);
				}
			   TimeUnit.SECONDS.sleep(5);
		 }
		Chef c = new Chef();
		c.placeOrder(str);
	}

    void makeBill() throws IOException, InterruptedException{
    	int k;
		String menu[] = new String[]{"Potato Skins","Aztec Tomato Soup","Mushrooms con Ques","Chicken Wings","Veg Biryani"
				   ,"Dal Rice","Chicken Biryani","Mutton Biryani","Russian Salad","Chicken Hawaiian Salad"};
	    int price[] = new int[]{100,100,100,200,200,150,250,300,100,100};


	    for(int i=0;i<100;i++){
	    	bill[i]=0;
	    }
	    for(int i=0;i<Host.cu;i++){
	    	System.out.println("\nBill of Customer at "+(i+1)+" table");
	    	for(int j=0;j<items[i].length;j++){
	    		for(k=0;k<menu.length;k++){
	    		    if(items[i][j].equals(menu[k]))
	    		    	bill[i]+=price[k];
	    		}
	    	}
	    	System.out.println(bill[i]);
	    	TimeUnit.SECONDS.sleep(5);
	    }
	    takePayment();
     }

     void takePayment() throws IOException, InterruptedException{
    	int cashReg[] = new int[100];
    	int amt;

    	for(int i=0;i<Host.cu;i++){
    	 do{
    	  System.out.println("\nPlease enter amount to pay the bill "+(i+1));
    	  amt=sc.nextInt();
    	  if(amt==bill[i]){
    		  System.out.println("\nPayment is done");
    	      cashReg[i]=amt;
    	  }
    	  else
    		  System.out.println("Pay Correct amount");
    	 }while(amt!=bill[i]);
       }
     }
}

class Chef{
	public void placeOrder(String[] str) throws IOException, InterruptedException {
      System.out.println("\nPrepareing Food...");
      TimeUnit.SECONDS.sleep(5);

      Server s=new Server();
	  s.serveFood();
	}
}
