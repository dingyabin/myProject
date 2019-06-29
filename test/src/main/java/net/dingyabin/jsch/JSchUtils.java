package net.dingyabin.jsch;

import com.google.common.collect.Lists;
import com.jcraft.jsch.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * Created by MrDing
 * Date: 2019/5/16.
 * Time:0:28
 */
public class JSchUtils {

    private static Session session;

    private OperateChain operateChain;


    public JSchUtils() {
        operateChain = new OperateChain();
        operateChain.addOperate(new RedisOperate());
    }


    protected void connect(String user, String passwd, String host, int port) throws JSchException {
        JSch jsch = new JSch();// 创建JSch对象
        session = jsch.getSession(user, host, port);// 根据用户名、主机ip、端口号获取一个Session对象
        session.setPassword(passwd);// 设置密码

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);// 为Session对象设置properties
        session.setTimeout(150000);// 设置超时
        session.connect();// 通过Session建立连接


    }


    /**
     * 50      * 执行相关的命令
     * 51      *
     * 52      * @throws JSchException
     * 53
     */
    protected void execCmd(List<String> commands) throws JSchException {
        Channel channel = null;
        try {
            if (CollectionUtils.isNotEmpty(commands)) {
                channel = session.openChannel("exec");
                if (operateChain != null) {
                    operateChain.doPreHandle(channel, commands);
                }
                ((ChannelExec) channel).setCommand(String.join("&&", commands));
                ((ChannelExec) channel).setErrStream(System.err);
                channel.connect();
                InputStream in = channel.getInputStream();
                BufferedReader cr = new BufferedReader(new InputStreamReader(in));
                String tmp;
                StringBuilder result = new StringBuilder();
                while ((tmp = cr.readLine()) != null) {
                    result.append(tmp);
                    result.append("\n");
                }
                handleResult(result.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            channel.disconnect();
        }
    }


    /**
     * 50      * 执行相关的命令
     * 51      *
     * 52      * @throws JSchException
     * 53
     */
    public void execCmd2(String command) throws JSchException {
        ChannelShell channelShell = null;
        try {
            if (command != null) {
                channelShell = (ChannelShell) session.openChannel("shell");
                channelShell.setPty(true);
                channelShell.connect();

                InputStream in = channelShell.getInputStream();//从远端到达的数据  都能从这个流读取到
                OutputStream outputStream = channelShell.getOutputStream();//写入该流的数据  都将发送到远程端
                //使用PrintWriter 就是为了使用println 这个方法
                //好处就是不需要每次手动给字符加\n
                PrintWriter printWriter = new PrintWriter(outputStream);
                printWriter.println("cd /data");
                printWriter.flush();//把缓冲区的数据强行输出
                printWriter.println("pwd");
                printWriter.flush();//把缓冲区的数据强行输出
                printWriter.println("ls");//为了结束本次交互
                printWriter.flush();//把缓冲区的数据强行输出

                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            BufferedReader cr = new BufferedReader(new InputStreamReader(in));
                            String tmp;
                            while ((tmp = cr.readLine()) != null) {
                                System.out.println(tmp);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                Thread.sleep(5000);
                printWriter.println("cd redis");//为了结束本次交互
                printWriter.flush();//把缓冲区的数据强行输出

                Thread.sleep(5000);
                printWriter.println("date");//为了结束本次交互
                printWriter.flush();//把缓冲区的数据强行输出

                Thread.sleep(5000);
                printWriter.println("exit");//为了结束本次交互
                printWriter.flush();//把缓冲区的数据强行输出
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            channelShell.disconnect();
        }
    }


    /**
     * 43      * 关闭连接
     * 44
     */
    protected void close() {
        session.disconnect();
    }


    public void execute(List<String> likst) throws Exception {
        // 1.连接到指定的服务器
        connect("root", "123456", "192.168.1.104", 22);
        // 2.执行相关的命令
       // execCmd(likst);
        execCmd2("");
        //
        finalComand(likst );
        // 4.关闭连接
        close();
    }


    public void finalComand(List<String> likst){
        likst.add("exit");
    }


    protected void handleResult(String result) {
        System.out.println(result);
    }


    public static void main(String[] args) {

        try {
            JSchUtils jSchUtils=new JSchUtils();

            jSchUtils.execute(Lists.newArrayList("pwd", "date"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
