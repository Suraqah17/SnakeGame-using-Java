import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class Main extends JFrame {
    Connection con;
    public Main() {
    }
    int connect(String un){
        int k=0;
        try {
            //load driver:
            Class.forName("com.mysql.cj.jdbc.Driver");
            //creating a connection
            String url="jdbc:mysql://localhost:3306/game";
            String username="root";
            String password="root";
            con=DriverManager.getConnection(url,username,password);
            if(con.isClosed())
            {
                System.out.println("Connection is closed");
                System.exit(0);
            }
            else
            {
                System.out.println("Connection Created...");
            }
            String cmnd="SELECT highscore FROM highscore where username = \""+un+"\"";
            PreparedStatement stmt=con.prepareStatement(cmnd);
            ResultSet rs=stmt.executeQuery();
            if(rs.next())
                k=rs.getInt("highscore");

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return k;
    }
    private void initUI(int hs,String un) {

        add(new Game(hs,un,con));
        setTitle("Snake");
        setSize(800, 610);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            JFrame jf = new JFrame("Login");
            JLabel j11=new JLabel("Enter Username:");
            JTextField jt = new JTextField(30);
            JButton jb=new JButton("Login");
            jf.add(j11);
            jf.add(jt);
            jf.add(jb);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jf.setLayout(new FlowLayout());
            jf.setSize(400,400);
            jb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String un=jt.getText();
                    Main ex = new Main();
                    int n=ex.connect(un);
                    ex.initUI(n,un);
                    jf.setVisible(false);
                    ex.setVisible(true);
                }
            });
            jf.setVisible(true);
        });
    }
}
