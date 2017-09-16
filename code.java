package com.code.restaurant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class code {
public static int cu;
public static void main(String[] args) throws IOException, InterruptedException,NullPointerException,ArrayIndexOutOfBoundsException {
		
	  System.out.println("\nEnter no.of Customers : ");
	  Scanner sc=new Scanner(System.in);
	  cu=sc.nextInt();
	  
	  Host h = new Host();
	  h.getTable();
  }
}	

class Host{
	public static int[] table=new int[5];;
	static int tables=5;
	int emptytables;
	static int waitCust=0;
	static int resCu=0;
	int i,j;
	
	public void getTable() throws IOException, InterruptedException,ArrayIndexOutOfBoundsException{
		for(i=0;i<5;i++){
			table[i]=0;
		}
		if(code.cu<=tables){
			for(i=0;i<tables && i<code.cu;i++){
				table[i]=1;
			}
			emptytables = tables-code.cu;
			System.out.println("\n"+i+"tables are filled");		
		}
		else if(code.cu>tables){
			for(i=0;i<tables && i<code.cu;i++){
				table[i]=1;
			}
			waitCust=code.cu-tables;
			System.out.println("\n"+waitCust+" customer waiting");
			System.out.println("\n"+i+"tables are filled");
		}
		else{
			System.out.println("\nTables are empty");
		}
		resCu=code.cu-waitCust;
		TimeUnit.SECONDS.sleep(5);
		Server s = new Server();
		s.serve();
    }
}

class Server{
	
	void serve() throws IOException, InterruptedException{
	  if(Host.resCu!=0){
		 for(int j=0;j<Host.resCu;j++){
		     if(Host.table[j] == 1){
		        System.out.println("Serves Water to customer at table"+(j+1));
		        TimeUnit.SECONDS.sleep(5);
		     }
	     } 
	  }
	 else
		 System.exit(0);
	     
	 Manager m=new Manager();
	     m.showMenu();
	     m.makeBill();
    }
	
	void serveFood() throws IOException, InterruptedException{
	    System.out.println("Serving Food...");
	    TimeUnit.SECONDS.sleep(5);
	}
}

class  Manager{
	String line[]=new String[5];
    String items[][]=new String[5][];
    int bill[] = new int[100];
    static int payment[] = new int[100];
    String custfc[][]=new String[100][2];
    int cashReg[] = new int[100];
    int a=0;
    
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
		
		for(int k=0;k<Host.resCu;k++){
			 if(Host.table[k] == 1){
				 System.out.println("\nPlease Enter the names of items that you want at customer"+(k+1));
				 line[k] = sc.nextLine();
				 a++;
			     TimeUnit.SECONDS.sleep(5);
			 }
		 }
	     for(int i=0,j=0;i<Host.resCu;i++){
			items[j++]=line[i].split(",");   
	     }
         for(int i=0;i<Host.resCu;i++){
			   System.out.println("\nItems ordered at "+(i+1)+" Table");
			   for(int j=0;j<items[i].length;j++){
					System.out.println(items[i][j]);   
				}
			    TimeUnit.SECONDS.sleep(5);
		 }
		Chef c = new Chef();
		c.placeOrder(line,a);
	}
	
    void makeBill() throws IOException, InterruptedException{
    	int k;
		String menu[] = new String[]{"Potato Skins","Aztec Tomato Soup","Mushrooms con Ques","Chicken Wings","Veg Biryani" 
				   ,"Dal Rice","Chicken Biryani","Mutton Biryani","Russian Salad","Chicken Hawaiian Salad"};
	    int price[] = new int[]{100,100,100,200,200,150,250,300,100,100}; 
	    
	    
	    for(int i=0;i<100;i++){
	    	bill[i]=0;
	    }
	    for(int i=0;i<Host.resCu;i++){
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
    	int amt;
    	
    	for(int i=0;i<Host.resCu;i++){
    	 do{
    	  System.out.println("\nPlease enter amount to pay the bill "+(i+1));
    	  amt=sc.nextInt();
    	  if(amt==bill[i]){
    		  System.out.println("\nPayment is done");
    	      cashReg[i]=amt;
    	      payment[i]=1;
    	  }
    	  else
    		  System.out.println("Pay Correct amount");
    	 }while(amt!=bill[i]);
    	}
    	getFeedback();
     }	
     
     void getFeedback() throws ArrayIndexOutOfBoundsException, IOException, InterruptedException{
    	 int cust=0,j;
    	 
    	 for(int i=0;i<Host.resCu;i++){
    		 for(j=0;j<2;j++){
    			 if(j==0){
    		       System.out.println("\nGive Your Feedback");
    		       custfc[i][j]=sc.nextLine();
    			 }
    			 TimeUnit.SECONDS.sleep(5);
    			 if(j==1){
    		       System.out.println("\nTell if there are any complaints");
    		       custfc[i][j]=sc.nextLine();
    			 }
    			 TimeUnit.SECONDS.sleep(5);
    		 }
    	 }
    	 
    	cust=Host.resCu;
     	for(int i=0;i<cust;i++){
     		if(payment[i]==1){
     			code.cu--;
 				if(Host.waitCust==0){
 					Host.table[i]=0;
 					Host.resCu--;
 				}
 				else{
 				    Host.waitCust--;
 				    Host.table[i]=1;
 				}
     	    }
     	}
    	Host h=new Host();
     	h.getTable();
    }
 }

class Chef{
	public void placeOrder(String[] str,int l) throws IOException, InterruptedException {
      			
	  for(int i=0;i<l;i++){
         System.out.println("\nPrepareing Food...");	
         System.out.println(str[i]);
         TimeUnit.SECONDS.sleep(5);
         Server s=new Server();
	     s.serveFood();
	 }
	}
}
