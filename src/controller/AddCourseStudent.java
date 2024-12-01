package controller;

import java.awt.AWTEvent;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AddCourseStudent extends JFrame implements ActionListener {
    //为课程添加学生


    JPanel contain;
    JButton submit;
    JLabel id, studentId;
    //JLabel studentName;
    JTextField idt, studentIdt;

    String courseName;
    String stuName;

    public AddCourseStudent() {
        super("添加新学生");
        setSize(400, 400);
        setLocation(600, 400);
        contain = new JPanel();
        contain.setLayout(null);
        id = new JLabel("课程号");
        studentId = new JLabel("学生学号");

        submit = new JButton("提交");

        idt = new JTextField();
        studentIdt = new JTextField();

        id.setBounds(42, 35, 75, 35);
        idt.setBounds(80, 35, 150, 35);
        studentId.setBounds(40, 90, 75, 35);
        studentIdt.setBounds(80, 90, 150, 35);

        submit.setBounds(102, 230, 70, 30);

        contain.add(id);
        contain.add(idt);
        contain.add(studentId);
        contain.add(studentIdt);
        contain.add(submit);
        submit.addActionListener(this);
        add(contain);
        setVisible(true);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    }

    public boolean hasStudent(String id) {
        String stuFile = System.getProperty("user.dir") + "/data/student.txt";
        try {
            BufferedReader br2 = new BufferedReader(new FileReader(stuFile));
            String s = null;
            while ((s = br2.readLine()) != null) {
                String[] result = s.split(" ");
                if (result[0].equals(id)) {
                    stuName = result[2];
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasCourse(String id) {  //  教师开课前检查课程是否已经存在

        String file = System.getProperty("user.dir") + "/data/course.txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file)); //构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                String[] result = s.split(" ");//以空格为间隔，将读入信息存入数组中
                if (result[0].equals(id)) {
                    courseName = result[1];
                    return true;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            if ((idt.getText().isEmpty()) || (studentIdt.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "信息不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (!hasCourse(idt.getText())) {
                    JOptionPane.showMessageDialog(null, "课程号为" + idt.getText() + "的课程不存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
                } else if (!hasStudent(studentIdt.getText())) {
                    JOptionPane.showMessageDialog(null, "学号为" + studentIdt.getText() + "的学生不存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String file = System.getProperty("user.dir") + "/data/course_student/" + courseName + "_student.txt";

                    ArrayList<String> modifiedContent = new ArrayList<String>();
                    // StringBuilder result = new StringBuilder();
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String s = null;
                        while ((s = br.readLine()) != null) {  // 先将原来存在的信息存储起来
                            String[] result = s.split(" ");

                            String s1 = "";
                            for (int i = 0; i < result.length - 1; i++) {
                                s1 = s1 + result[i];
                                s1 = s1 + " ";
                            }
                            s1 = s1 + result[result.length - 1];
                            // System.out.println(s1);
                            modifiedContent.add(s1);
                        }
                        br.close();

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    modifiedContent.add(idt.getText()+" "+courseName+" "+studentIdt.getText()+" "+stuName);
                    try{
                        FileWriter fw = new FileWriter(file);
                        BufferedWriter bw = new BufferedWriter(fw);

                        for(String s : modifiedContent){
                            bw.write(s);
                            bw.newLine();
                        }

                        bw.close();
                        fw.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(null, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    public void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            this.dispose();
            setVisible(false);
        }
    }
}
