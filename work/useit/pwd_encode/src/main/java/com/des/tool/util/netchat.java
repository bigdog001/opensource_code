package com.des.tool.util;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FileDialog;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.des.tool.jiami;
import com.des.tool.jiemi;

public class netchat {
    JFrame jf = new JFrame("密文处理程序");
    //List l = new List(6);
    TextArea ta=new TextArea(20,20);

    JPanel jp = new JPanel();
    Button b1 = new Button("载入密文");
    //Button b2 = new Button("解密");
    Button b3 = new Button("明文加密");

    JPasswordField mm=new JPasswordField(15);
    public static void main(String[] args) {
        new netchat().init();
    }
    private void init()
    {
        jf.add(ta);
        jp.add(b1, BorderLayout.WEST);
        //jp.add(b2, BorderLayout.EAST);
        jp.add(b3, BorderLayout.EAST);
        jp.add(mm);
        jf.add(jp, BorderLayout.SOUTH);
        jf.setSize(600, 600);
        jf.setLocation(100, 70);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        b1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ta.setText("");
                String filePath="";
                FileDialog fd = new FileDialog(jf,"", 0);//打开
                fd.setTitle("选择密文文件");
                fd.setSize(400, 300);
                fd.setVisible(true);
                filePath = fd.getDirectory() + fd.getFile();
                String selected = fd.getDirectory();
                //System.out.println();
                ta.setText(jiemi.main(selected+fd.getFile(),  mm.getText().trim()));
                mm.setText("");
            }
        });
        b3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String filePath="";
                FileDialog fd = new FileDialog(jf,"", 1);//打开
                fd.setTitle("选择密文存储位置");
                // fd.setFilterPath("C:/");
                // String[] filterExt = { "*.txt", "*.doc", ".rtf", "*.*" };
                // fd.setFilterExtensions(filterExt);
                fd.setSize(400, 300);
                fd.setVisible(true);
                //  filePath = fd.getDirectory() + fd.getFile();
                //System.out.println(filePath);
                // ta.setText(filePath);
                String selected = fd.getDirectory();
                // System.out.println();
                jiami.main(ta.getText().trim(), selected+fd.getFile(), mm.getText().trim());
                mm.setText("");
            }
        });
    }
}