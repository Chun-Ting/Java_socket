import java.io.*;
 
import java.net.*;
 
public class Client{
 
    public static void main(String[] args)throws Exception{
 
        //使用本地文件系統接受網絡數據並存為新文件
    	String filename="newFile.mp4";
        int port=8080;

 
        // 通過Socket連接文件服務器
        System.out.println("準備連接server端");
        Socket server=null;
        try {
        	server=new Socket(InetAddress.getLocalHost(),port);
        } catch (java.io.IOException e) {
        	 System.out.println("與Server連線失敗，可能是Server尚未開啟");
        	 System.out.println("錯誤訊息IOException :" + e.toString());
        	 System.out.println("\n關閉程式");
        	 return;
        }
        System.out.println("連線成功");
 
        //創建網絡接受流接受服務器文件數據 
        System.out.println("準備接收檔案");
        InputStream netIn=server.getInputStream();
 
    	System.out.println("接收檔案的新檔名為:"+filename);
        File file=new File(filename);//生成的文件名
        file.createNewFile();
        
        InputStream in=new DataInputStream(new BufferedInputStream(netIn));
        RandomAccessFile raf=new RandomAccessFile(file,"rw");
 
        //創建緩衝區緩衝網絡數據
        System.out.println("開始接收檔案");
        byte[] buf=new byte[2048];
 
        int num=in.read(buf);         
 
        while(num!=(-1)){//是否讀完所有數據
 
              raf.write(buf,0,num);//將數據寫往文件
 
              raf.skipBytes(num);//順序寫文件字節
 
              num=in.read(buf);//繼續從網絡中讀取文件
 
        }
        System.out.println("接收檔案完成");
        in.close();
 
        raf.close();
 
    }
}