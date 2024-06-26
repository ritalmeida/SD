/**
 * <p>
 * Title: Projecto SD</p>
 * <p>
 * Description: Projecto apoio aulas SD</p>
 * <p>
 * Copyright: Copyright (c) 2011</p>
 * <p>
 * Company: UFP </p>
 *
 * @author Rui Moreira
 * @version 2.0
 */
package edu.ufp.inf.sd.rmi._05_observer.client;

import edu.ufp.inf.sd.rmi._05_observer.server.State;
import edu.ufp.inf.sd.rmi._05_observer.server.SubjectRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * @author rjm
 */
public class ObserverGuiClient extends javax.swing.JFrame {

    /**
     * Context for connecting a RMI client to a RMI Servant
     */
    private SetupContextRMI contextRMI;
    /**
     * Remote interface that will hold the Servant proxy
     */
    private SubjectRI subjectRI;

    private ObserverImpl observer;

    /**
     * Creates new form ChatClientFrame
     *
     * @param args main args with ip, port and service name
     */
    public ObserverGuiClient(String args[]) {
        initComponents();
        initContext(args);
    }

    private void initContext(String args[]) {
        try {
            //List ans set args
            SetupContextRMI.printArgs(this.getClass().getName(), args);
            String registryIP=args[0];
            String registryPort=args[1];
            String serviceName=args[2];
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going to setup RMI context...");
            //Create a context for RMI setup
            this.contextRMI=new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
            lookupService();
        } catch (RemoteException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    private void initObserver(String args[]) {
        try {
            //String username=this.jTextFieldUsername.getText();
            this.observer = new ObserverImpl(this.jTextFieldUsername.getText(), this, this.subjectRI);
            System.out.println("Observer Created");
        } catch (Exception e) {
            Logger.getLogger(ObserverGuiClient.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    private void lookupService() {
        try {
            //Get proxy to rmiregistry
            Registry registry = this.contextRMI.getRegistry();
            //Lookup service on rmiregistry and wait for calls
            if (registry != null) {
                //Get service url (including servicename)
                String serviceUrl=contextRMI.getServicesUrl(0);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going to lookup service @ {0}", serviceUrl);

                //============ Get proxy to HelloWorld service ============
                this.subjectRI=(SubjectRI) registry.lookup(serviceUrl);
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException | NotBoundException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }
    private void initComponents() {
        this.setSize(400, 400);
        this.setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.pack();
        this.login();
    }

    protected void updateTextArea() {
        try {
            String msg = "[" + this.observer.getObserverState().getId() + "] " + this.observer.getObserverState().getInfo();
            this.jTextAreaChatHistory.append(msg + '\n');
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private void chat() {
        jFileChooser1            = new javax.swing.JFileChooser();
        jScrollPane1             = new javax.swing.JScrollPane();
        jTextAreaChatHistory     = new javax.swing.JTextArea();
        jTextAreaChatHistoryPane = new javax.swing.JTextPane();
        jButtonSend              = new javax.swing.JButton();
        jTextFieldMsg            = new javax.swing.JTextField();
        jLabelUserID             = new javax.swing.JLabel();
        jMenuBar1                = new javax.swing.JMenuBar();
        jMenu1                   = new javax.swing.JMenu();
        jMenuItemExit            = new javax.swing.JMenuItem();
        jMenuItemSave            = new javax.swing.JMenuItem();
        jMenu2                   = new javax.swing.JMenu();
        jMenuItemCopy            = new javax.swing.JMenuItem();
        jMenuItemPaste           = new javax.swing.JMenuItem();

        // chat history textarea
        // jTextAreaChatHistory.setColumns(20);
        // jTextAreaChatHistory.setLineWrap(true);
        // jTextAreaChatHistory.setRows(5);
        // jTextAreaChatHistory.setEnabled(false);
        // jScrollPane1.setViewportView(jTextAreaChatHistory);

        // chat history textpane
        jTextAreaChatHistoryPane.setContentType("text/html");
        jTextAreaChatHistoryPane.setEditable(false);
        jScrollPane1.setViewportView(jTextAreaChatHistoryPane);
        this.doc = jTextAreaChatHistoryPane.getStyledDocument();
        this.leftAlign = new SimpleAttributeSet();
        this.rightAlign = new SimpleAttributeSet();
        StyleConstants.setAlignment(rightAlign, StyleConstants.ALIGN_RIGHT);
        StyleConstants.setAlignment(leftAlign, StyleConstants.ALIGN_LEFT);

        jButtonSend.setText("Send");
        jButtonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendActionPerformed(evt);
            }
        });

        jTextFieldMsg.setText("msg");
        jTextFieldMsg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldMsgKeyPressed(evt);
            }
        });

        jLabelUserID.setText("User ID: " + this.jTextFieldUsername.getText());

        jMenu1.setText("File");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemExit);

        jMenuItemSave.setText("Save");
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveActionPerformed(evt);
            }
        });

        jMenu1.add(jMenuItemSave);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItemCopy.setText("Copy");
        jMenuItemCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCopyActionPerformed(evt);
            }
        });

        jMenu2.add(jMenuItemCopy);

        jMenuItemPaste.setText("Paste");
        jMenuItemPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPasteActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemPaste);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        this.getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabelUserID))
                                        .addComponent(jTextFieldMsg, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSend))
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextFieldMsg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButtonSend))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabelUserID))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
    }

    private void login(){
        // frame is already in place
        // only need to create panel and add it to frame
        JPanel login = new JPanel();
        login.setLayout(new FlowLayout());

        jLabelUserID = new javax.swing.JLabel();
        jLabelUserID.setText("User ID");
        jTextFieldUsername = new javax.swing.JTextField();
        jTextFieldUsername.setText("Enter Username");
        jButtonSend = new JButton();
        jButtonSend.setText("Login");
        jButtonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        login.add(jLabelUserID);
        login.add(jTextFieldUsername);
        login.add(jButtonSend);

        this.add(login);
        this.pack();
    }

    private void jButtonLoginActionPerformed(ActionEvent evt) {
        String username = this.jTextFieldUsername.getText();
        this.jTextFieldUsername.setText(username);
        this.getContentPane().removeAll(); // remove current panel
        this.initObserver(null);
        this.chat(); // call new panel
    }

    private void jMenuItemCopyActionPerformed(java.awt.event.ActionEvent evt) {
        this.jTextAreaChatHistory.selectAll();
        this.jTextAreaChatHistory.copy();
    }

    private void jMenuItemPasteActionPerformed(java.awt.event.ActionEvent evt) {
        this.jTextAreaChatHistory.paste();
    }

    private void jButtonSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendActionPerformed
        if (!this.jTextFieldMsg.getText().isBlank()) {
            try {
                State s = new State(this.jTextFieldUsername.getText(), this.jTextFieldMsg.getText());
                this.observer.getSubjectRI().setState(s);
                this.jTextFieldMsg.setText(""); // clear msg field
            } catch (RemoteException ex) {
                Logger.getLogger(ObserverGuiClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void jTextFieldMsgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMsgKeyPressed
        char c = evt.getKeyChar(); // retrieve keyboard input from user
        if (c == '\n' || c == '\r') {
            if (!this.jTextFieldMsg.getText().isBlank()) {
                try { // notify subject
                    State s = new State(this.jTextFieldUsername.getText(), this.jTextFieldMsg.getText());
                    this.observer.getSubjectRI().setState(s);

                    this.jTextFieldMsg.setText(""); // clear msg field
                } catch (RemoteException ex) {
                    Logger.getLogger(ObserverGuiClient.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        try {
            this.observer.getSubjectRI().detach(this.observer);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.dispose();
        System.exit(0);
    }

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void jMenuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {
        FileWriter fw = null;
        try {
            this.jFileChooser1 = new JFileChooser(new File("C:\\Temp"));
            this.jFileChooser1.showSaveDialog(this);
            File f = this.jFileChooser1.getSelectedFile();
            if (f != null) {
                fw = new FileWriter(f);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(this.jTextAreaChatHistory.getText());
                pw.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ObserverGuiClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(ObserverGuiClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (args.length >= 3) {
                    new ObserverGuiClient(args).setVisible(true);
                } else {
                    System.out.println(ObserverGuiClient.class + ": call must have the following args: <rmi_ip> <rmi_port> <rmi_service_prefix>");
                }
            }
        });
    }

    private javax.swing.JButton jButtonSend;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabelUserID;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemCopy;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemPaste;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JTextArea jTextAreaChatHistory;
    protected javax.swing.JTextPane jTextAreaChatHistoryPane;
    protected StyledDocument doc;
    protected SimpleAttributeSet leftAlign;
    protected SimpleAttributeSet rightAlign;
    private javax.swing.JTextField jTextFieldMsg;
    private javax.swing.JTextField jTextFieldUsername;
}
