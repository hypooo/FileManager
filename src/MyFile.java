import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class MyFile extends JFrame {

    private JTextField jtfPath;
    private JTextPane textPane;
    private JScrollPane jsp;

    private MyFile() {
        super("简单文件管理");

        jtfPath = new JTextField(18);//实例化文件路径输入框
        textPane = new JTextPane();
        jsp = new JScrollPane(textPane);
        JButton jbFile = new JButton("浏览");//实例化文件选择按钮

        JPanel panel = new JPanel();//实例化面板,用于容纳输入框和按钮
        panel.add(new JLabel("文件名: "));//增加组件到面板
        panel.add(jtfPath);
        panel.add(jbFile);

        JPanel panel2 = new JPanel();
        //实例化按钮,并增加到面板2上
        panel2.add(new JButton(new CreateFileAction()));
        panel2.add(new JButton(new CreateFolderAction()));
        panel2.add(new JButton(new DeleteFileAction()));
        panel2.add(new JButton(new ReadFileAction()));
        panel2.add(new JButton(new WriteFileAction()));
        panel2.add(new JButton(new SearchFileAction()));

        jbFile.addActionListener(new ActionListener() {
            //选择文件按钮事件处理
            public void actionPerformed(ActionEvent event) {
                JFileChooser fileChooser = new JFileChooser("D:/");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                //设置文件选择模式,此处为文件和目录均可
                if (fileChooser.showOpenDialog(MyFile.this) == JFileChooser.APPROVE_OPTION) {
                    //弹出文件选择器,并判断是否点击了打开按钮
                    String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                    //得到选择文件或目录的绝对路径
                    jtfPath.setText(fileName);
                }
            }
        });

        Container container1 = getContentPane();
        container1.add(panel, BorderLayout.NORTH);
        container1.add(jsp, BorderLayout.CENTER);
        container1.add(panel2, BorderLayout.SOUTH);


        setSize(580, 400);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /*创建文件*/
    class CreateFileAction extends AbstractAction {
        public CreateFileAction() {
            super("创建文件");
        }

        public void actionPerformed(ActionEvent e) {
            String filename = jtfPath.getText(); //从输入框得到文件名
            File f = new File("D:/", filename);
            try {
                if (!f.exists()) {
                    f.createNewFile();
                    JOptionPane.showMessageDialog(MyFile.this, filename + " 新文件创建成功.");
                } else {
                    JOptionPane.showMessageDialog(MyFile.this, filename + " 文件已存在.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /*创建文件夹*/
    class CreateFolderAction extends AbstractAction {
        private CreateFolderAction() {
            super("创建文件夹");
        }

        public void actionPerformed(ActionEvent e) {
            String filename = jtfPath.getText(); //从输入框得到文件名
            File f = new File("D:/", filename);
            try {
                if (!f.exists()) {
                    f.mkdir();
                    JOptionPane.showMessageDialog(MyFile.this, filename + " 文件夹创建成功.");
                } else {
                    JOptionPane.showMessageDialog(MyFile.this, filename + " 文件夹已存在.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /*读文件*/
    class ReadFileAction extends AbstractAction {
        public ReadFileAction() {
            super("读文件");
        }

        public void actionPerformed(ActionEvent e) {
            String filename = jtfPath.getText();
            File f = new File(filename);
            try {
                FileInputStream fis = new FileInputStream(f);
                textPane.read(fis, "t");//desc描述流的对象
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(MyFile.this, "文件读失败");
            }
        }

    }

    /*写文件*/
    class WriteFileAction extends AbstractAction {
        private WriteFileAction() {
            super("写文件");
        }

        public void actionPerformed(ActionEvent e) {
            String filename = jtfPath.getText();
            File f = new File(filename);
            try {
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(textPane.getText().getBytes());
                JOptionPane.showMessageDialog(MyFile.this, "文件写完成");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(MyFile.this, "文件写失败");
            }
        }
    }

    /*删除文件*/
    class DeleteFileAction extends AbstractAction {
        private DeleteFileAction() {
            super("删除");
        }

        public void actionPerformed(ActionEvent e) {
            File f = new File(jtfPath.getText());
            try {
                f.delete();
                JOptionPane.showMessageDialog(MyFile.this, f.getName() + " 删除成功.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(MyFile.this, f.getName() + " 删除失败.");
                ex.printStackTrace();
            }
        }
    }

    /*搜索文件*/
    class SearchFileAction extends AbstractAction {
        private SearchFileAction() {
            super("文件搜索");
        }

        private void searchFile(File f, String s) {
            File[] files = f.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    searchFile(file, s);
                } else {
                    if (file.getName().contains(s)) {
                        JOptionPane.showMessageDialog(MyFile.this, "搜索到了" + file);
                    } else {
                        JOptionPane.showMessageDialog(MyFile.this, "没有找到相关文件");
                        return;
                    }
                }
            }
        }

        public void actionPerformed(ActionEvent e) {
            String s = jtfPath.getText();
            File f = new File("C:\\Users\\HYPO\\Pictures");
            searchFile(f, s);
        }
    }

    public static void main(String[] args) {
        new MyFile();
    }
}