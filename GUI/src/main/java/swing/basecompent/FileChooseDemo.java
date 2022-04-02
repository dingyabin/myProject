package swing.basecompent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author 丁亚宾
 * Date: 2021/8/4.
 * Time:0:52
 */
public class FileChooseDemo {


    private final JFrame jf = new JFrame("这是一个swing窗口");


    private JMenuBar jMenuBar = new JMenuBar();

    private JMenu fileMenu = new JMenu("文件");


    private JMenuItem open = new JMenuItem("打开");
    private JMenuItem save = new JMenuItem("另存为");


    private BufferedImage bufferedImage;

    private ImagePanel imagePanel = new ImagePanel(new Dimension(900,600));






    private void init(){

        open.addActionListener(e -> {
            JFileChooser jFileChooser = new JFileChooser("c:\\");
            jFileChooser.showOpenDialog(jf);
            File selectedFile = jFileChooser.getSelectedFile();
            if (selectedFile == null) {
                return;
            }
            bufferedImage = getBufferedImageFromFile(selectedFile);
            imagePanel.setBufferedImage(bufferedImage);
            imagePanel.setPreferredSize(new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight()));
            imagePanel.repaint();
        });

        save.addActionListener(e -> {



        });



        fileMenu.add(open);
        fileMenu.add(save);
        jMenuBar.add(fileMenu);

        jf.setJMenuBar(jMenuBar);

        jf.add(new JScrollPane(imagePanel));


        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);

    }



    private BufferedImage getBufferedImageFromFile(File file){
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




    private static class ImagePanel extends JPanel{

        private BufferedImage bufferedImage;


        public ImagePanel(Dimension dimension) {
            super.setPreferredSize(dimension);
        }


        public ImagePanel() {
        }

        public ImagePanel(BufferedImage bufferedImage) {
            this.bufferedImage = bufferedImage;
        }


        @Override
        public void paint(Graphics g) {
            g.drawImage(bufferedImage,0,0, null);
        }

        public BufferedImage getBufferedImage() {
            return bufferedImage;
        }

        public void setBufferedImage(BufferedImage bufferedImage) {
            this.bufferedImage = bufferedImage;
        }
    }


    public static void main(String[] args) {
        new FileChooseDemo().init();
    }


}
