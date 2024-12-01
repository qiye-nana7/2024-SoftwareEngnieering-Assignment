package controller;


import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

import model.Course;


@SuppressWarnings("serial")
public class AddCourse extends JFrame implements ActionListener {
	/*
	 * 教师增加课程
	 */
	
	
	JPanel contain;
	JButton submit;
	JLabel id, name, gredit, classH, teacherId, teacherName;
	JTextField idt, namet, greditt, classHt, teacherIdt, teacherNamet;

	public AddCourse() {
		super("增加课程");
		setSize(400, 400);
		setLocation(600, 400);
		contain = new JPanel();
		contain.setLayout(null);
		id = new JLabel("课程号");
		name = new JLabel("课程名");
		gredit = new JLabel("学分");
		classH = new JLabel("学时");
		
		teacherId = new JLabel("教师");
		teacherName = new JLabel("教师号");
		
		submit = new JButton("提交");
		idt = new JTextField();
		namet = new JTextField();
		greditt = new JTextField();
		classHt = new JTextField();
		teacherIdt = new JTextField();
		teacherNamet = new JTextField();
		
		id.setBounds(42, 35, 75, 35);
		idt.setBounds(80, 35, 150, 35);
		name.setBounds(40, 90, 75, 35);
		namet.setBounds(80, 90, 150, 35);
		gredit.setBounds(45, 145, 75, 35);
		greditt.setBounds(80, 145, 150, 35);
		classH.setBounds(45, 200, 75, 35);
		classHt.setBounds(80, 200, 150, 35);
		
		teacherId.setBounds(45, 245, 75, 35);
		teacherIdt.setBounds(85, 245, 150, 35);
		
		teacherName.setBounds(45, 280, 75, 35);
		teacherNamet.setBounds(80, 280, 75, 35);
		
		submit.setBounds(102, 320, 70, 30);
		contain.add(id);
		contain.add(idt);
		contain.add(name);
		contain.add(namet);
		contain.add(gredit);
		contain.add(greditt);
		contain.add(classH);
		contain.add(classHt);
		contain.add(teacherId);
		contain.add(teacherIdt);
		contain.add(teacherName);
		contain.add(teacherNamet);
		contain.add(submit);
		submit.addActionListener(this);
		add(contain);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}
	
	public int hasCourse(String id){  //  教师开课前检查课程是否已经存在
		
		String file = System.getProperty("user.dir")+"/data/course.txt";
		try{
            BufferedReader br = new BufferedReader(new FileReader(file)); //构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	String[] result = s.split(" ");//以空格为间隔，将读入信息存入数组中
            	if(result[0].equals(id)){
            		return 1;
            		
            	}
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
		
		return 0; 
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			if ((idt.getText().equals("")) || (namet.getText().equals("")) || (greditt.getText().equals(""))
					|| (classHt.getText().equals("")) || teacherIdt.getText().equals("") || teacherNamet.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "信息不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				if ((hasCourse(idt.getText())) == 1) {
					JOptionPane.showMessageDialog(null, "此课程已经存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					String a = idt.getText();
					String b = namet.getText();
					String c = greditt.getText();
					String d = classHt.getText();
					String t = teacherIdt.getText();
					String f = teacherNamet.getText();

					String file = System.getProperty("user.dir")+"/data/course.txt";
					
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
					
					Course course = new Course(idt.getText(), namet.getText(), greditt.getText(), classHt.getText(), teacherIdt.getText(), teacherNamet.getText());
					
					
					modifiedContent.add(course.getCourseId()+" "+course.getCourseName()+" "+course.getCredit()+" "+course.getHour()+" "+course.getTeacherId()+" "+course.getTeacherName());

					try {
						FileWriter fw = new FileWriter(file);
						BufferedWriter bw = new BufferedWriter(fw);

                        for (String s : modifiedContent) {
                            bw.write(s);

                            bw.newLine();
                        }

						bw.close();
						fw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					//  除了添加对应课程文件外，还需要添加课程成绩文件以及课程学生文件
					File gradeFile = new File(System.getProperty("user.dir")+"/data/grade/"+course.getCourseName()+".txt");
					File studentFile = new File(System.getProperty("user.dir")+"/data/course_student/"+course.getCourseName()+"_student.txt");
					if(!gradeFile.exists()){
                        try {
                            gradeFile.createNewFile();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
					if(!studentFile.exists()){
						try {
							studentFile.createNewFile();
						} catch (IOException ex) {
							throw new RuntimeException(ex);
						}
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
