package cn.yznu.rzgskhgl.util;
import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.io.OutputStreamWriter;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
  
/** 
 * 功能：备份以及还原mysql数据库 
 * 需要将mysql安装的bin目录C:\Program Files\MySQL\MySQL Server 5.5\bin设置到环境变量，否则不知道怎么调用mysqldump[⊙﹏⊙b汗] 
 * 用java备份的话，不能使用">"了，所以调用cmd命令时，需要更改里面的参数 
 * mysqldump -h127.0.0.1 -uroot -proot erp > backupfile.sql 
 * mysqldump  -h 127.0.0.1 -u root -proot erp  --default-character-set=utf8  --result-file=D:backupfile.sql 
 * @author Administrator 
 * 
 */  
public class BackupsDB {  
  
    /** 
     * 备份mysql数据库 
     * @param root  mysql登录名 
     * @param rootPass  登录密码 
     * @param dbName  要备份的数据库名称 
     * @param backupsPath  备份的路径 
     * @param backupsSqlFileName  备份文件的名字 
     * @return 
     */  
    public static String dbBackUp(String root,String rootPass,String dbName,String backupsPath,String backupsSqlFileName)  
    {  
        //生成临时备份文件  
//      SimpleDateFormat sd=new SimpleDateFordckupsSqlFileName;  
        String pathSql = backupsPath+backupsSqlFileName;  
        try {  
            File fileSql = new File(pathSql);  
            if(!fileSql.exists()){  
                fileSql.createNewFile();  
            }  
            StringBuffer sbs = new StringBuffer();  
            sbs.append("mysqldump ");  
            sbs.append(" -h 180.173.241.30 ");  
            sbs.append(" -P 5011 ");  
            sbs.append(" -u ");  
            sbs.append(root+" ");  
            sbs.append("-p"+rootPass+" ");  
            sbs.append(dbName);  
            sbs.append(" --default-character-set=utf8 ");  
//          sbs.append(">"+pathSql);  
            sbs.append(" --result-file="+pathSql);  
            System.out.println("cmd命令为：——>>>"+sbs.toString());  
            Runtime runtime = Runtime.getRuntime();  
            Process child = runtime.exec(sbs.toString());  
              
            //读取备份数据并生成临时文件  
            InputStream in = child.getInputStream();  
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(pathSql), "utf8");  
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf8"));  
            String line=reader.readLine();  
            while (line != null) {  
                writer.write(line+"\n");  
                line=reader.readLine();  
             }  
             writer.flush();  
             System.out.println("数据库已备份到——>>"+pathSql);  
        } catch (Exception e) {  
              
        }  
        return pathSql;  
    }  
      
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
//      mysqldump -h 192.168.110.10 --user=root --password=fortune123 --lock-all-tables=true --result-file=D:tt.sql\ --default-character-set=utf8 ERP  
        SimpleDateFormat sd=new SimpleDateFormat("yyyyMMddHHmmss");  
        String root = "root";  
        String rootPass = "root";  
        String dbName = "rzgskhgl";  
        String backupsPath = "D:";  
        String backupsSqlFileName = "erpDB_"+sd.format(new Date())+".sql";  
        dbBackUp(root, rootPass, dbName, backupsPath, backupsSqlFileName);  
        backup();  
        load();  
    }  
  
    /** 
     * 备份erp数据库到D:db.sql 
     * @return 
     */  
    public static boolean backup() {//备份  
        try {  
            Runtime rt = Runtime.getRuntime();    
            String mysql = "mysqldump -uroot -proot erp --default-character-set=utf8 ";    
//          mysql ="C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\"+mysql;  
              
            System.out.println("mysql=============>>>>"+mysql);  
            // 调用 mysql 的 cmd:    
            Process child = rt    
            .exec("cmd /c "+mysql);// 设置导出编码为utf8。这里必须是utf8    
        
            // 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行    
            InputStream in = child.getInputStream();// 控制台的输出信息作为输入流    
        
            InputStreamReader xx = new InputStreamReader(in, "utf8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码    
        
            String inStr;    
            StringBuffer sb = new StringBuffer("");    
            String outStr;    
            // 组合控制台输出信息字符串    
            BufferedReader br = new BufferedReader(xx);    
            while ((inStr = br.readLine()) != null) {    
                sb.append(inStr + "\r\n");    
            }    
            outStr = sb.toString();    
        
            // 要用来做导入用的sql目标文件：    
            FileOutputStream fout = new FileOutputStream(    
                    "D:/db.sql");    
            OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");    
            writer.write(outStr);    
            // 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免    
            writer.flush();    
        
            // 别忘记关闭输入输出流    
            in.close();    
            xx.close();    
            br.close();    
            writer.close();    
            fout.close();    
        
            System.out.println("/* Output OK! */");    
                            
            String error="备份成功!";    
        
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
        
        return true;    
    }    
      
    /** 
     * 还原D:db.sql到erp数据库 
     * @return 
     */  
    public static boolean load() {//还原    
        try {  
            String fPath = "D:/db.sql";    
            Runtime rt = Runtime.getRuntime();    
        
            // 调用 mysql 的 cmd:    
            Process child = rt.exec("mysql -uroot -proot erp ");    
            OutputStream out = child.getOutputStream();//控制台的输入信息作为输出流    
            String inStr;    
            StringBuffer sb = new StringBuffer("");    
            String outStr;    
            BufferedReader br = new BufferedReader(new InputStreamReader(    
                    new FileInputStream(fPath), "utf8"));    
            while ((inStr = br.readLine()) != null) {    
                sb.append(inStr + "\r\n");    
            }    
            outStr = sb.toString();    
        
            OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");    
            writer.write(outStr);    
            // 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免    
            writer.flush();    
            // 别忘记关闭输入输出流    
            out.close();    
            br.close();    
            writer.close();    
                
            System.out.println("/* Load OK! */");    
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
        return true;    
    }     
}  