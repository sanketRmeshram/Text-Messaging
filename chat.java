import javax.swing.*;  
import java.awt.event.*;  
import java.awt.Color;
import java.net.*;
import java.util.Scanner; 
public class chat 
{  
    public static	int Xlen=1000  , Ylen=1000 ;
    public static JTextField message;
    public static DefaultListModel<String> l1 = new DefaultListModel<>();     
    public static JButton b1; 
    public static JList<String> list; 
    public static String ip;
    public static String friend_name;
    public static int port;
    
public static void Send_other(String sentence) throws Exception
{
    InetAddress Friend_ip_Address = InetAddress.getByName(ip);
    byte[] sendData = new byte[1024];
    sendData=sentence.getBytes();
    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, Friend_ip_Address, 9876);
    DatagramSocket clientSocket = new DatagramSocket();
    clientSocket.send(sendPacket);

}

public static void Recieve() throws Exception
{
    DatagramSocket serverSocket = new DatagramSocket(9876);


    while(true)
    {
      byte[] receiveData = new byte[1024];
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      serverSocket.receive(receivePacket);
      String sentence = new String( receivePacket.getData());
      if( l1.getSize() > 52) l1.removeAllElements();
      l1.addElement(friend_name +" : " + sentence);
    }

}       

public static void main(String[] args) throws Exception {  


  Scanner sc = new Scanner(System.in); 
  System.out.println("Enter your friend's name :" );
  friend_name=sc.nextLine();
  System.out.println("Enter your friend's ip_address :" );
  ip=sc.nextLine();
  Thread t1=new Thread(){  
  public void run() {  
      try
      {
        Recieve();
      }
      catch(Exception e9){}
    }  
  };   
  list = new JList<>(l1);  
  list.setBounds(0,0,Xlen,Ylen-50-50);  
  JFrame chat_system= new JFrame("Chat System");  
  message=new JTextField("Enter the message.");  
  message.setBounds(0,Ylen-50-50,Xlen -100,50);  
  b1=new JButton("SEND");
  b1.setBounds(Xlen-100,Ylen-50-50,100,50);  
 
  b1.addActionListener(new ActionListener(){  
  public void actionPerformed(ActionEvent e)
  { 
      String s1=message.getText();   
      try
      {
        Send_other(s1) ;
      }
      catch(Exception e1)
      {

      }       
      if( l1.getSize() > 52) l1.removeAllElements();   
      l1.addElement("YOU : "+s1);
      message.setText("");  
  }

    });    
  t1.start(); 
  chat_system.add(message);chat_system.add(b1); 
  chat_system.add(list);
  chat_system.setSize(Xlen ,Ylen);  
  chat_system.setLayout(null);  
  chat_system.setVisible(true);   

} }  