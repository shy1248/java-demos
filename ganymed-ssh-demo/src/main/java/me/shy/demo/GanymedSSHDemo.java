package me.shy.demo;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.*;

public class GanymedSSHDemo {

    public static void main(String[] args) {
        // 远程机器IP
        String hostname = "192.168.136.20";
        // 登录用户名
        String username = "idomp";
        // 登录密码
        String password = "8RcDKn08zSNaVZY";

        try {
            Connection conn = new Connection(hostname);

            conn.setServerHostKeyAlgorithms(new String[]{
                    "ssh-ed25519-cert-v01@openssh.com",
                    "ecdsa-sha2-nistp256-cert-v01@openssh.com",
                    "ecdsa-sha2-nistp384-cert-v01@openssh.com",
                    "ecdsa-sha2-nistp521-cert-v01@openssh.com",
                    "sk-ssh-ed25519-cert-v01@openssh.com",
                    "sk-ecdsa-sha2-nistp256-cert-v01@openssh.com",
                    "rsa-sha2-512-cert-v01@openssh.com",
                    "rsa-sha2-256-cert-v01@openssh.com",
                    "ssh-ed25519",
                    "ecdsa-sha2-nistp256",
                    "ecdsa-sha2-nistp384",
                    "ecdsa-sha2-nistp521",
                    "sk-ssh-ed25519@openssh.com",
                    "sk-ecdsa-sha2-nistp256@openssh.com",
                    "rsa-sha2-512",
                    "rsa-sha2-256"});

            conn.connect();


//            boolean isAuthed = conn.authenticateWithPublicKey("idox", new File("C:/Users/shy/.ssh/id_rsa"), null);
//            if (isAuthed) {
//                System.out.println("Successed.");
//            } else {
//                System.out.println("Failed.");
//            }

            boolean isAuthenticated = conn.authenticateWithPassword(username, password);
            // 是否登录成功
            if (!isAuthenticated) {
                throw new IOException("Authenticated failed!");
            }
            Session sess = conn.openSession();
            //执行命令
            sess.execCommand("uname -a && date && uptime && who && hostname -f");

            System.out.println("Here is some information about the remote host:");

            //创建输入流
            InputStream stdout = new StreamGobbler(sess.getStdout());
            //字符流
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));

            while (true) {
                String line = br.readLine();
                if (line == null)
                    break;
                System.out.println(line);
            }
            System.out.println("ExitCode: " + sess.getExitStatus());
            //关闭连接
            sess.close();
            conn.close();
        } catch (IOException e) {
            e.printStackTrace(System.err);
            System.exit(2);
        }
    }
}
