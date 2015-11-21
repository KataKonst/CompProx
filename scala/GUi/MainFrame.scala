package GUi

import javax.swing.JFrame
import java.awt.Dimension
import javax.swing.JButton
import javax.swing.JTextField
import java.net.InetSocketAddress
import akka.actor.ActorSystem
import server.Server
import akka.actor.ActorSystem
import util.FilesUtilities
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import javax.swing.JPanel
import java.awt.GridBagLayout
import java.awt.GridBagConstraints
import java.awt.Insets
import client.Client
import akka.util.ByteString

/**
 * @author katakonst
 */
class MainFrame  extends JFrame {
    val but=new JButton("listen");
    val alias=new JTextField()
    val file=new JTextField()

    val panel=new JPanel();
    init();
    initGui();
    def init()
    {
     but.addActionListener(new ActionListener(){
         def actionPerformed(e: ActionEvent) { 
  val system = ActorSystem("echo-service-system")
  val endpoint = new InetSocketAddress("localhost", 1244)

    
 val a= system.actorOf(Server.props(endpoint,null,"/home/katakonst/alias.txt"), "service")

         }

     }) 
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

    }
    def initGui()
    {
       panel.setLayout(new GridBagLayout())
       panel.add(file,new GridBagConstraints(0,0,1,1,1.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0))

       panel.add(alias,new GridBagConstraints(0,1,1,1,1.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0))

       panel.add(but,new GridBagConstraints(0,2,1,1,1.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0))
       this.getContentPane.add(panel);
       this.setSize(new Dimension(400,200));
      
    }
  
}