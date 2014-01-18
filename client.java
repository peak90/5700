import java.net.*;
import java.util.Arrays;
import java.io.*;

public class client
{
    public static void main(String [] args)
    {
            String server="cs5700.ccs.neu.edu";
	    int port=27993;
	    ClientSocket s=null;
	    try
	    {
	        s=new ClientSocket(server,port);
	        s.connect();
	        s.sendMsg("cs5700spring2014 HELLO 001197411\n");
	        String solution=" ";
	        int count=0;
	        while(!s.isEnd())
	        {
	    	    count++;
	    	    System.out.println("/////////////////////////");
	            s.receiveMsg();
	            solution=s.getSolution();
	            s.sendMsg(solution);
	        }
	        System.out.println("counting time:"+count);
	    }
	    catch(Exception e)
	    {
	        System.out.println("Exception");
	    }
	    finally
	    {
                s.close();
	    }
    }
}

///create a class used to connect server and get solution
class ClientSocket
{
    private String server=null;
    private int port=0;
    private Socket socket=null; 
    private InputStream in=null;
    private OutputStream out=null;
    private String problem=" ";
    public ClientSocket(String server, int port)
    {
        this.server=server;
	this.port=port;
    }
    public void connect()
    {
        try
	    {
	        socket=new Socket(server,port);
	        in=socket.getInputStream();
	        out=socket.getOutputStream();
	    }
	    catch(Exception e)
	    {
		System.out.println("connect fails");
	    }
    }
    public void sendMsg(String msg) throws Exception
    {
    	System.out.println(msg);
	    try
	    {
	    	byte [] b=msg.getBytes();
	        out.write(b);
	        out.flush();
	    }
	    catch(Exception e)
	    {
		System.out.println("send msg fails");
	        throw e;
	    }
    }
    public void receiveMsg() throws Exception
    {
    	try
    	{
    	    byte [] rb=new byte[255];
    	    if(in.read(rb,0,rb.length)==-1)
    	    {
    		System.out.println("read fails");
    	    }
    	    else
    	    {
    	    	problem=new String(rb);
    		System.out.println(problem);
    	    }
	}
	catch(Exception e)
	{
		System.out.println("receive msg fails");
	    	throw e;
	}
    }
    public String getSolution()
    {
    	String [] strs=problem.split(" ");
    	String res=" ";
    	if(!problem.contains("BYE"))
    	{
    	    res=calculate(strs[2],strs[3],strs[4]);
    	}
    	return "cs5700spring2014 "+res+"\n";
    }
    public boolean isEnd()
    {
    	return problem.contains("BYE");
    }
    private String calculate(String opand1,String op,String opand2)
    {
    	int op1=Integer.parseInt(opand1.trim());
    	int op2=Integer.parseInt(opand2.trim());
    	System.out.println(op1);
    	System.out.println(op2);
    	int result=0;
    	if(op.equals("+"))
    	{
    		result=op1+op2;
    	}
    	else if(op.equals("-"))
    	{
    		result=op1-op2;
    	}
    	else if(op.equals("*"))
    	{
    		result=op1*op2;
    	}
    	else if(op.equals("/"))
    	{
    		result=op1/op2;
    	}
    	else
    	{
    		result=0;
    	}
    	System.out.println(result);
    	return Integer.toString(result);
    }
    public void close()
    {
	try
	{
	    socket.close();
	}
	catch(Exception e)
	{
	    System.out.println("socket close fails");
	}
    }
}
