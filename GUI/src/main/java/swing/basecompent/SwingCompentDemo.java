package swing.basecompent;

import com.sun.java.swing.plaf.motif.MotifLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import com.sun.media.jfxmedia.events.PlayerTimeListener;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.multi.MultiLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.xml.transform.Source;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author 丁亚宾
 * Date: 2021/8/1.
 * Time:23:47
 */
public class SwingCompentDemo {


    //窗口
    private final JFrame jf = new JFrame("这是一个swing窗口");


    //JMenuBar
    private JMenuBar jMenuBar = new JMenuBar();

    //菜单
    private JMenu fileMenu = new JMenu("文件");
    private JMenu editMenu = new JMenu("编辑");


    private JMenuItem auto = new JMenuItem("自动换行");
    private JMenuItem copy = new JMenuItem("复制");
    private JMenuItem paste = new JMenuItem("粘贴");
    private JMenu format = new JMenu("格式");


    private JMenuItem comment = new JMenuItem("注释");
    private JMenuItem cancelComment = new JMenuItem("取消注释");



    private JPopupMenu jPopupMenu = new JPopupMenu();

    private JMenuItem theam1 = new JMenuItem("主题1");
    private JMenuItem theam2 = new JMenuItem("主题2");
    private JMenuItem theam3 = new JMenuItem("主题3");



    private JComboBox<String> colorSelect = new JComboBox<>(new String[]{"红色", "白色", "绿色"});


    private ButtonGroup manWomanGroup = new ButtonGroup();
    private JRadioButton man = new JRadioButton("男");
    private JRadioButton woman = new JRadioButton("女");


    private JCheckBox isMarry = new JCheckBox("是否已婚");


    private JList<String> jList = new JList<>(new String[]{"红色", "白色", "绿色"});

    private JTextArea ta = new JTextArea(10,70);

    private JTextField jt = new JTextField(60);
    private JButton ok = new JButton("确定");


    private void init() {

        jMenuBar.add(fileMenu);


        editMenu.add(auto);
        editMenu.addSeparator();
        editMenu.add(copy);
        editMenu.add(paste);
        editMenu.addSeparator();


        comment.setToolTipText("将程序代码注释起来");
        format.add(comment);
        format.add(cancelComment);
        editMenu.add(format);

        jMenuBar.add(editMenu);


        jf.setJMenuBar(jMenuBar);



        JPanel leftTop = new JPanel();

        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(ta);


        JPanel select = new JPanel();
        select.add(colorSelect);

        manWomanGroup.add(man);
        manWomanGroup.add(woman);

        select.add(man);
        select.add(woman);
        select.add(isMarry);

        verticalBox.add(select);

        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(verticalBox);
        horizontalBox.add(jList);

        leftTop.add(horizontalBox);


        JPanel bottom = new JPanel();
        Box horizontalBox2 = Box.createHorizontalBox();

        horizontalBox2.add(jt);
        horizontalBox2.add(ok);

        bottom.add(horizontalBox2);



        jf.add(leftTop);

        jf.add(bottom , BorderLayout.SOUTH);


        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                change(e.getActionCommand());
            }
        };

        theam1.addActionListener(actionListener);
        theam2.addActionListener(actionListener);
        theam3.addActionListener(actionListener);

        jPopupMenu.add(theam1);
        jPopupMenu.addSeparator();
        jPopupMenu.add(theam2);
        jPopupMenu.addSeparator();
        jPopupMenu.add(theam3);

        ta.setComponentPopupMenu(jPopupMenu);


        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }



    private void change(String context){
        try {
            if (context.equals(theam1.getText())) {
                UIManager.setLookAndFeel(MetalLookAndFeel.class.getName());
            }
            if (context.equals(theam2.getText())) {
                UIManager.setLookAndFeel(SynthLookAndFeel.class.getName());
            }
            if (context.equals(theam3.getText())) {
                UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
            }

            SwingUtilities.updateComponentTreeUI(jf.getContentPane());
            SwingUtilities.updateComponentTreeUI(jMenuBar);
            SwingUtilities.updateComponentTreeUI(jPopupMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new SwingCompentDemo().init();
    }


}
