package java;

import java.SplashScreen;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;

public class Die extends JFrame implements MouseListener, MouseMotionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JLabel lblNewLabel_1, lblNewLabel_2, lblNewLabel, lblNewLabel_3, dis2, label, label_1, lblSave, label_2,
            lblSizeOnDisk, lblNewLabel_5, dis5, lblNewLabel_6, lblNewLabel_7, lblNewLabel_8, lblNewLabel_4;
    static SplashScreen splash;
    static Die f;
    JSlider slider, slider_1, slider_2;
    Character rotation = null;
    Float imgRatio = 0.0F;
    float imgHeight, imgWidth, label1_width, label1_height;
    ImageIcon editIcon = null, originalIcon = null, instanceCapture[] = new ImageIcon[500];
    Integer red[] = new Integer[256], green[] = new Integer[256], blue[] = new Integer[256];
    int drag_status = 0, c1, c2, c3, c4, tx, ty, redlar, greenlar, bluelar, counter = 0;
    JButton btnSave, btnNegative, btnNewButton_1, btnNewButton_2, btnRotate, btnNewButton, btnCrop, btnBlur, btnSharpen,
            btnEdge, btnRed;
    private JMenuBar menuBar;
    private JMenu mnEdit, mnAction, mnRotation;
    private JMenuItem mnItmLeft, mnItmRight, mnItmOpen;
    private JMenuItem mnItmExit, mnItmUndo, mnItmRedo, mnItmCrop, mnItmBlackAndWhite, mnItmNegative, mnItmBlurring,
            mnItmEdgeDetection, mnItmSharpening;

    public static void main(String[] args) {
        splash = new SplashScreen();
        splash.setVisible(true);

    }

    /**
     * Create the frame.
     *
     * @throws UnsupportedLookAndFeelException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public Die() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("../DIE/src/images/EditorLogo.png"));


        setTitle("IE");
        setType(Window.Type.NORMAL);
        setResizable(true);
        // setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(10, 10, 868, 709);
        getContentPane().setLayout(null);

        label = new JLabel("");
        label.setBackground(SystemColor.activeCaption);
        label.setBounds(726, 63, 140, 140);
        // label.setBorder();
        getContentPane().add(label);

        JButton btnUploadImage = new JButton("");
        ImageIcon temp = new ImageIcon(SplashScreen.class.getResource("openimage.jpg"));


        btnUploadImage.setIcon(temp);

        btnUploadImage.setToolTipText("Upload image (Alt + U)");
        btnUploadImage.setMnemonic('U');
        btnUploadImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                uploadButton();
            }
        });
        btnUploadImage.setBounds(5, 33, 50, 50);
        getContentPane().add(btnUploadImage);

        btnRotate = new JButton("");
        temp = new ImageIcon(SplashScreen.class.getResource("r.png"));
        /*
		 * lblNewLabel = new JLabel(); lblNewLabel.setToolTipText(
		 * "Rotate 90` left( Alt + L )"); lblNewLabel.setBounds(589, 356, 75,
		 * 68); getContentPane().ad d(lblNewLabel);
		 */
        btnRotate.setIcon(temp);
        // lblNewLabel.setIcon(temp);
        // lblNewLabel.setVisible(true);
        // btnRotate.setVisible(false);
        btnRotate.setToolTipText("Rotate 90` left( Alt + L )");
        btnRotate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (editIcon != null) {
                    rotation = 'l';
                    imageRotate();
                }
            }
        });
        btnRotate.setBounds(295, 33, 50, 50);
        getContentPane().add(btnRotate);

        btnNewButton = new JButton("");
        btnNewButton.setToolTipText("Rotate 90` Right( Alt + R )");
        temp = new ImageIcon(SplashScreen.class.getResource("r1.png"));
        btnNewButton.setIcon(temp);

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (editIcon != null) {
                    rotation = 'r';
                    imageRotate();
                }
            }
        });
        btnNewButton.setBounds(346, 33, 50, 50);
        getContentPane().add(btnNewButton);

        label_1 = new JLabel("");
        label_1.setBounds(20, 131, 600, 449);

        getContentPane().add(label_1);

        JLabel lblOriginalImage = new JLabel("ORIGINAL IMAGE");
        lblOriginalImage.setBackground(SystemColor.textHighlight);
        lblOriginalImage.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblOriginalImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblOriginalImage.setBounds(716, 33, 150, 30);
        getContentPane().add(lblOriginalImage);

        JLabel lblEditedImage = new JLabel("EDITING IMAGE");
        lblEditedImage.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblEditedImage.setBackground(SystemColor.textHighlight);
        lblEditedImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblEditedImage.setBounds(15, 98, 150, 25);
        getContentPane().add(lblEditedImage);

        btnNewButton_1 = new JButton("");
        btnNewButton_1.setToolTipText("UNDO ( Alt + Z )");
        btnNewButton_1.setMnemonic('Z');

        temp = new ImageIcon(SplashScreen.class.getResource("w.png"));

        btnNewButton_1.setIcon(temp);

        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (counter > 1)
                    if (editIcon != null)
                        undo();
            }
        });
        btnNewButton_1.setBounds(62, 33, 67, 50);
        getContentPane().add(btnNewButton_1);

        btnNewButton_2 = new JButton("");
        btnNewButton_2.setToolTipText("Alt + Y");
        temp = new ImageIcon(SplashScreen.class.getResource("untitled.png"));
		/*
		 * lblNewLabel_2 = new JLabel(""); lblNewLabel_2.setToolTipText(
		 * "REDO( Alt + Y )"); lblNewLabel_2.setBounds(641, 451, 108, 48);
		 * getContentPane().add(lblNewLabel_2); lblNewLabel_2.setIcon(temp);
		 */
        btnNewButton_2.setIcon(temp);
        btnNewButton_2.setMnemonic('Y');
        // btnNewButton_2.setVisible(false);
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (instanceCapture[counter + 1] != null)
                    if (editIcon != null)
                        redo();
            }
        });
        btnNewButton_2.setBounds(125, 33, 67, 50);
        getContentPane().add(btnNewButton_2);

        btnCrop = new JButton("");
        btnCrop.setToolTipText("Crop(Ctrl + C)");
        btnCrop.setMnemonic('C');

        temp = new ImageIcon(SplashScreen.class.getResource("crop.jpg"));
        btnCrop.setIcon(temp);
        // btnCrop.setVisible(false);
        btnCrop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (editIcon != null)
                    crop();
            }
        });
        btnCrop.setBounds(194, 33, 50, 50);
        getContentPane().add(btnCrop);

        btnSave = new JButton("Save");
        btnSave.setToolTipText("Alt + S");
        btnSave.setBackground(Color.WHITE);
        btnSave.setVisible(false);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (editIcon != null)
                    save();
            }
        });
        btnSave.setBounds(291, 95, 105, 34);
        getContentPane().add(btnSave);

        lblSave = new JLabel("Save");
        lblSave.setHorizontalAlignment(SwingConstants.CENTER);
        lblSave.setToolTipText("Alt + S");
        lblSave.setBackground(Color.GREEN);
        lblSave.setForeground(Color.BLACK);
        lblSave.setBounds(269, 100, 86, 25);
        getContentPane().add(lblSave);

        btnRed = new JButton("");
        btnRed.setToolTipText("BLACK and WHITE(Alt + W)");

        temp = new ImageIcon(SplashScreen.class.getResource("gray.jpg"));
        btnRed.setIcon(temp);
        btnRed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                if (editIcon != null)
                    red();
            }
        });
        btnRed.setBounds(246, 33, 50, 50);
        getContentPane().add(btnRed);
        // btnRed.setVisible(false);
        btnSharpen = new JButton("Sharpening");
        btnSharpen.setToolTipText("Ctrl + h");
        btnSharpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                if (editIcon != null)
                    sharpen();
            }
        });
        // btnSharpen.setVisible(false);
        btnSharpen.setBounds(610, 30, 100, 30);
        getContentPane().add(btnSharpen);

        btnEdge = new JButton("Edge Detection");
        btnEdge.setToolTipText("Alt + E");
        btnEdge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (editIcon != null)
                    edge();
            }
        });
        btnEdge.setBounds(485, 30, 125, 30);
        getContentPane().add(btnEdge);

        btnBlur = new JButton("Bluuring");
        btnBlur.setToolTipText("Alt + B");
        // btnBlur.setVisible(false);
        btnBlur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (editIcon != null)
                    blur();
            }
        });
        btnBlur.setBounds(485, 62, 125, 30);
        getContentPane().add(btnBlur);

        dis5 = new JLabel("");
        dis5.setBounds(740, 505, 160, 160);
        getContentPane().add(dis5);

        JButton btnSepia = new JButton("sepia");
        btnSepia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (editIcon != null)
                    sepia();
            }
        });
        btnSepia.setBounds(610, 62, 100, 30);
        getContentPane().add(btnSepia);

        dis2 = new JLabel("");
        dis2.setBounds(726, 253, 140, 140);
        getContentPane().add(dis2);

        slider = new JSlider();
        slider.setBackground(new Color(255, 69, 0));
        slider.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent arg0) {
                if (editIcon != null)
                    adjustRED();
            }
        });
        slider.setValue(0);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMinimum(-10);
        slider.setMaximum(10);
        slider.setBounds(634, 615, 218, 50);
        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        getContentPane().add(slider);

        JButton btnEq = new JButton("histogram equalisation");
        btnEq.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (editIcon != null)
                    eq();
            }
        });
        btnEq.setBounds(410, 95, 300, 34);
        getContentPane().add(btnEq);

        slider_1 = new JSlider();
        slider_1.setBackground(new Color(72, 209, 204));
        slider_1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent arg0) {
                if (editIcon != null)
                    adjustGREEN();
            }
        });

        slider_1.setValue(0);
        slider_1.setPaintTicks(true);
        slider_1.setPaintLabels(true);
        slider_1.setMinorTickSpacing(1);
        slider_1.setMinimum(-10);
        slider_1.setMaximum(10);
        slider_1.setMajorTickSpacing(5);
        slider_1.setBounds(634, 481, 218, 50);
        getContentPane().add(slider_1);

        slider_2 = new JSlider();
        slider_2.setBackground(new Color(30, 144, 255));
        slider_2.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent arg0) {
                if (editIcon != null)
                    adjustBLUE();
            }
        });
        slider_2.setValue(0);
        slider_2.setPaintTicks(true);
        slider_2.setPaintLabels(true);
        slider_2.setMinorTickSpacing(1);
        slider_2.setMinimum(-10);
        slider_2.setMaximum(10);
        slider_2.setMajorTickSpacing(5);
        slider_2.setBounds(634, 553, 218, 50);
        getContentPane().add(slider_2);

        btnNegative = new JButton("");
        btnNegative.setToolTipText("Image Negative");
        temp = new ImageIcon(SplashScreen.class.getResource("neg.jpg"));
        btnNegative.setIcon(temp);

        btnNegative.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (editIcon != null)
                    negative();
            }
        });
        btnNegative.setBounds(406, 33, 50, 50);
        getContentPane().add(btnNegative);

        label_2 = new JLabel("");
        label_2.setBounds(392, 646, 96, 19);
        getContentPane().add(label_2);

        lblSizeOnDisk = new JLabel("Size on Disk");
        lblSizeOnDisk.setBackground(new Color(255, 218, 185));
        lblSizeOnDisk.setBounds(295, 651, 74, 19);
        lblSizeOnDisk.setVisible(false);
        getContentPane().add(lblSizeOnDisk);
        menuBar = new JMenuBar();
        menuBar.setBounds(2, 3, 860, 20);
        getContentPane().add(menuBar);
        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);
        mnItmOpen = new JMenuItem("Open");
        mnItmOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                uploadButton();
            }
        });

        mnFile.add(mnItmOpen);
        JMenuItem mnItmSave = new JMenuItem("Save");
        mnFile.add(mnItmSave);
        JMenuItem mnItmSaveAs = new JMenuItem("Save As");
        mnItmSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (editIcon != null)
                    save();
            }
        });
        mnFile.add(mnItmSaveAs);
        mnItmExit = new JMenuItem("Exit");
        mnItmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                close();
            }
        });
        mnFile.add(mnItmExit);
        mnEdit = new JMenu("Edit");
        menuBar.add(mnEdit);
        mnItmUndo = new JMenuItem("Undo");
        mnItmUndo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (counter > 1)
                    if (editIcon != null)
                        undo();
            }
        });
        mnEdit.add(mnItmUndo);
        mnItmRedo = new JMenuItem("Redo");
        mnItmRedo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (instanceCapture[counter + 1] != null)
                    if (editIcon != null)
                        redo();
            }
        });
        mnEdit.add(mnItmRedo);
        mnRotation = new JMenu("Rotation");
        mnEdit.add(mnRotation);
        mnItmLeft = new JMenuItem("90' Left");
        mnItmLeft.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (editIcon != null) {
                    rotation = 'l';
                    imageRotate();
                }
            }
        });
        mnRotation.add(mnItmLeft);
        mnItmRight = new JMenuItem("90' Right");
        mnItmRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (editIcon != null) {
                    rotation = 'r';
                    imageRotate();
                }

            }
        });
        mnRotation.add(mnItmRight);
        mnItmCrop = new JMenuItem("Crop");
        mnItmCrop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (editIcon != null)
                    crop();
            }
        });
        mnEdit.add(mnItmCrop);
        mnAction = new JMenu("Action");
        menuBar.add(mnAction);
        mnItmBlackAndWhite = new JMenuItem("Black And White");
        mnItmBlackAndWhite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (editIcon != null)
                    red();
            }
        });
        mnAction.add(mnItmBlackAndWhite);
        mnItmNegative = new JMenuItem("Negative");
        mnItmNegative.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (editIcon != null)
                    negative();
            }
        });
        mnAction.add(mnItmNegative);
        mnItmBlurring = new JMenuItem("Blurring");
        mnItmBlurring.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (editIcon != null)
                    blur();
            }
        });
        mnAction.add(mnItmBlurring);
        mnItmEdgeDetection = new JMenuItem("Edge Detection");
        mnItmEdgeDetection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (editIcon != null)
                    edge();
                ;
            }
        });
        mnAction.add(mnItmEdgeDetection);
        mnItmSharpening = new JMenuItem("Sharpening");
        mnItmSharpening.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (editIcon != null)
                    sharpen();
            }
        });
        mnAction.add(mnItmSharpening);

        temp = new ImageIcon(SplashScreen.class.getResource("test.jpg"));
        BufferedImage img = null;

        ImageIcon icon = temp;
        Image i = icon.getImage();
        img = new BufferedImage(i.getWidth(this), i.getHeight(this), BufferedImage.TYPE_INT_RGB);

        Graphics g1 = img.createGraphics();
        g1.drawImage(i, 0, 0, null);
        g1.dispose();
        temp = new ImageIcon(img);
        label_1.setIcon(temp);
        setBounds(10, 10, 1250, 709);
        editIcon = temp;
        BufferedImage bout = new BufferedImage(140, 140, img.getType());
        g1 = bout.createGraphics();
        g1.drawImage(img, 0, 0, 140, 140, null);

        g1.dispose();
        originalIcon = new ImageIcon(bout);
        repaint();
        label.setIcon(originalIcon);

    }

    public void close() {
        System.exit(0);
    }

    public void Calcsize() {
        BufferedImage img = null;
        // File f = null;
        ImageIcon icon = editIcon;
        Image i = icon.getImage();
        img = new BufferedImage(i.getWidth(this), i.getHeight(this), BufferedImage.TYPE_INT_RGB);

        Graphics g1 = img.createGraphics();
        g1.drawImage(i, 0, 0, null);
        g1.dispose();
        File currentDirFile = new File(" ");
        String path = currentDirFile.getAbsolutePath() + "temp.jpg";
        File f = new File(path);
        System.out.println(path);

        try {
            ImageIO.write(img, "jpg", f);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        File file = new File(path);

        Long size = file.length() / 1024;
        // System.out.println(size);
        label_2.setText(size.toString() + " KB");
    }

    private void uploadButton() {

        FileDialog d = new FileDialog(this, "Open Dialog", FileDialog.LOAD);
        d.setFile("*.jpg;*.jpeg;*.png");
        d.setVisible(true);
        String path = d.getDirectory() + d.getFile();
        BufferedImage bufferedInputImage = null, bufferedOutputImage = null;

        try {
            bufferedInputImage = FileDemo.readFile(path);
            imgHeight = bufferedInputImage.getHeight();
            imgWidth = bufferedInputImage.getWidth();
            float temp1 = imgHeight, temp2 = imgWidth;
            imgRatio = (temp2 / temp1);
            // System.out.println(imgRatio);
            label1_height = 400;
            label1_width = (int) (label1_height * imgRatio);
            // System.out.println(label1_height + "" +label1_width);
            label_1.setBounds(20, 130, (int) label1_width, (int) label1_height);

            // System.out.println(bufferedInputImage.getType() );

            bufferedOutputImage = new BufferedImage((int) label1_width, (int) label1_height,
                    bufferedInputImage.getType());

            Graphics2D g2d = bufferedOutputImage.createGraphics();

            g2d.drawImage(bufferedInputImage, 0, 0, (int) label1_width, (int) label1_height, null);
            g2d.dispose();

            editIcon = new ImageIcon(bufferedOutputImage);
            // editIcon = (ImageIcon)img.getImage();
            // System.out.println(editIcon);
            // editIcon = originalIcon;
            counter = counter + 1;
            instanceCapture[counter] = editIcon;

            bufferedOutputImage = new BufferedImage(140, 140, bufferedInputImage.getType());
            g2d = bufferedOutputImage.createGraphics();
            g2d.drawImage(bufferedInputImage, 0, 0, 140, 140, null);

            g2d.dispose();
            originalIcon = new ImageIcon(bufferedOutputImage);

            label.setIcon(originalIcon);

            lblSave.setVisible(false);
            btnSave.setVisible(true);
            label_1.setIcon(editIcon);
            btnNewButton.setVisible(true);
            btnRotate.setVisible(true);
            btnBlur.setVisible(true);
            btnEdge.setVisible(true);
            btnRed.setVisible(true);
            btnSharpen.setVisible(true);
            btnCrop.setVisible(true);
            btnNewButton_1.setVisible(true);
            btnNewButton_2.setVisible(true);
            lblSizeOnDisk.setVisible(true);

            displayUndo();
            repaint();
            Calcsize();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sharpen() {
        BufferedImage bisrc, bidest, bi;
        Image img = editIcon.getImage();

        bisrc = new BufferedImage(img.getWidth(this), img.getHeight(this), BufferedImage.TYPE_INT_RGB);
        Graphics2D big = bisrc.createGraphics();
        big.drawImage(img, 0, 0, this);
        bidest = new BufferedImage(img.getWidth(this), img.getHeight(this), BufferedImage.TYPE_INT_RGB);
        bi = bisrc;
        float data[] = {-1.0f, -1.0f, -1.0f, -1.0f, 9.0f, -1.0f, -1.0f, -1.0f, -1.0f};
        Kernel kernel = new Kernel(3, 3, data);
        ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        convolve.filter(bisrc, bidest);
        bi = bisrc;
        ImageIcon temp = new ImageIcon(bidest);
        editIcon = temp;
        counter = counter + 1;
        instanceCapture[counter] = editIcon;
        System.out.println("image at index   " + counter + "is" + instanceCapture[counter]);

        label_1.setIcon(temp);
        label_1.setIcon(temp);
        repaint();
        displayUndo();
        Calcsize();
    }

    void undo() {

        counter = counter - 1;
        System.out.println("image at index   " + counter + "is" + instanceCapture[counter]);

        editIcon = instanceCapture[counter];

        ImageIcon temp = instanceCapture[counter];

        label_1.setBounds(20, 130, temp.getIconWidth(), temp.getIconHeight());
        label_1.setIcon(temp);
        repaint();
        Calcsize();
        displayUndo();

    }

    void redo() {
        counter = counter + 1;

        editIcon = instanceCapture[counter];
        label_1.setIcon(instanceCapture[counter]);
        displayUndo();
        repaint();
        Calcsize();
    }

    public void mouseClicked(MouseEvent arg0) {
    }

    public void mouseEntered(MouseEvent arg0) {
        label_1.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }

    public void mouseExited(MouseEvent arg0) {
        label_1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void mousePressed(MouseEvent arg0) {

        tx = c1 = arg0.getX(); // /work on label
        ty = c2 = arg0.getY();
        System.out.println(arg0.getX() + "in press");

    }

    public void mouseDragged(MouseEvent arg0) {

        drag_status = 1;
        c3 = arg0.getX();
        c4 = arg0.getY();
        if (c3 > (int) label_1.getWidth())
            c3 = (int) label_1.getWidth();
        if (c3 < 0)
            c3 = 0;
        if (c4 > (int) label_1.getHeight())
            c4 = (int) label_1.getHeight();

        if (c4 < 0)
            c4 = 0;

        repaint();

    }

    public void paint(Graphics g) {
        super.paint(g);
        int w = 0, h = 0;
        w = c1 - c3;
        h = c2 - c4;
        if (w < 0)
            w = w * (-1);
        if (h < 0)
            h = h * (-1);

        if (c3 < c1)
            tx = c3 + 28; // 28 is position of label in frame including header
        else
            tx = c1 + 28;
        if (c4 < c2)
            ty = c4 + 152;
        else
            ty = c2 + 152;

        g.drawRect(tx, ty, w, h);
        // if(drag_status!=1){

        if (editIcon != null) {
            calcHIST();

            g.setColor(Color.BLACK);
            g.fillRect(870, 10, 870, 710);

            // for red
            g.setColor(Color.WHITE);
            g.drawLine(918, 245, 1200, 245);
            g.drawLine(918, 246, 1200, 246);
            g.drawLine(918, 247, 1200, 247);// horizontal
            g.drawLine(918, 245, 918, 35);
            g.drawLine(917, 245, 917, 35);
            g.drawLine(916, 245, 916, 35);

            // for blue
            // g.drawLine(820, 450, 1076, 445);
            g.drawLine(918, 450, 1200, 450);
            g.drawLine(918, 451, 1200, 451);
            g.drawLine(918, 452, 1200, 452);// horizontal
            g.drawLine(918, 450, 918, 250);
            g.drawLine(917, 450, 917, 250);
            g.drawLine(916, 450, 916, 250);

            // for green
            g.drawLine(918, 655, 1200, 655);
            g.drawLine(918, 656, 1200, 656);
            g.drawLine(918, 657, 1200, 657);// horizontal
            g.drawLine(918, 655, 918, 455);
            g.drawLine(917, 655, 918, 455);
            g.drawLine(916, 655, 916, 455);
            int temp;
            long no_of_pixel = editIcon.getIconHeight() * editIcon.getIconWidth();
            float normailsed_temp, divider = (float) redlar;
            float divider1 = (float) greenlar, divider2 = (float) bluelar;
            for (int i = 0; i <= 255; i++) {

                normailsed_temp = red[i];

                normailsed_temp = normailsed_temp / divider;

                normailsed_temp = normailsed_temp * 200.0F;

                red[i] = (int) normailsed_temp;

                normailsed_temp = green[i];

                normailsed_temp = normailsed_temp / divider1;

                normailsed_temp = normailsed_temp * 200.0F;

                green[i] = (int) normailsed_temp;

                normailsed_temp = blue[i];

                normailsed_temp = normailsed_temp / divider2;

                normailsed_temp = normailsed_temp * 200.0F;

                blue[i] = (int) normailsed_temp;

            }

            g.setColor(Color.green);
            for (int i = 1; i <= 255; i++) {
                // System.out.println(blue[i]);
                // temp = (655 - green[i] / 60);

                g.drawLine(920 + i, 655, 920 + i, 655 - green[i]);

            }

            g.setColor(Color.BLUE);
            for (int i = 1; i <= 255; i++) {
                // System.out.println(blue[i]);
                // temp = (450 - blue[i] / 60);
                g.drawLine(920 + i, 450, 920 + i, 450 - blue[i]);

            }

            g.setColor(Color.RED);

            for (int i = 1; i <= 255; i++) {
                // System.out.println(red[i]);
                // temp = (245 - red[i] / 60);
                g.drawLine(920 + i, 245, 920 + i, 245 - red[i]);
            }
        }
        // System.out.println(c2+66+f.getY());

        // System.out.println(w+ "," +h);

    }

    public void calcHIST() {
        BufferedImage img = null;
        // File f = null;
        ImageIcon icon = editIcon;
        Image i = icon.getImage();
        img = new BufferedImage(i.getWidth(this), i.getHeight(this), BufferedImage.TYPE_INT_RGB);

        Graphics g1 = img.createGraphics();
        g1.drawImage(i, 0, 0, null);
        g1.dispose();

        // get width and height of the image
        int width = img.getWidth();
        int height = img.getHeight();

        for (int i1 = 0; i1 <= 255; i1++) {
            red[i1] = 0;
            blue[i1] = 0;
            green[i1] = 0;
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                red[r] = red[r] + 1;
                green[g]++;
                blue[b]++;

                p = (a << 24) | (r << 16) | (g << 8) | b;

            }
        }
        redlar = largest(red);
        greenlar = largest(green);
        bluelar = largest(blue);

    }

    public void mouseReleased(MouseEvent arg0) {

        c3 = arg0.getX();
        c4 = arg0.getY();
        if (c3 > (int) label_1.getWidth())
            c3 = (int) label_1.getWidth();
        if (c4 > (int) label_1.getHeight())
            c4 = (int) label_1.getHeight();
        if (c3 < 0)
            c3 = 0;
        if (c4 < 0)
            c4 = 0;
        try {
            draggedScreen();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		/*
		 * if(c3<0){ c3=c1; c1=0;}
		 * 
		 * if(c4<0){ c4=c2; c2=0;}
		 */
        // repaint();
        // System.out.println(c3);
    }

    public void mouseMoved(MouseEvent arg0) {

    }

    public void draggedScreen() throws Exception {
        int w = c1 - c3;
        int h = c2 - c4;
        w = w * -1;
        h = h * -1;
        Robot robot = new Robot();
        int frame_X_cord = f.getX();

        int frame_Y_cord = f.getY();
        if (c3 < c1) {
            w = w * -1;
            c1 = c3;
        }
        if (c4 < c2) {
            h = h * -1;
            c2 = c4;
        }

        BufferedImage img = robot
                .createScreenCapture(new Rectangle(c1 + 28 + frame_X_cord, c2 + 152 + frame_Y_cord, w - 1, h - 1));

        float w1 = w, h1 = h;
        BufferedImage croppedImage;

        float cropratio = w1 / h1;
        System.out.println("cropratio   is  " + cropratio);
        if (cropratio > 1.5) {
            label1_width = 534;
            label1_height = (int) (label1_width / cropratio);
        } else {
            label1_height = 400;
            label1_width = (int) (label1_height * cropratio);
        }

        label_1.setBounds(20, 130, (int) label1_width, (int) label1_height);
        croppedImage = new BufferedImage((int) label1_width, (int) label1_height, img.getType());
        Graphics2D g2d = croppedImage.createGraphics();
        g2d.drawImage(img, 0, 0, (int) (label1_width), (int) label1_height, null);
        g2d.dispose();
        editIcon = new ImageIcon(croppedImage);
        counter = counter + 1;
        instanceCapture[counter] = editIcon;

        label_1.setIcon(editIcon);
        label_1.removeMouseListener(this);
        label_1.removeMouseMotionListener(this);
        label_1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        tx = 0;
        ty = 0;
        c1 = 0;
        c2 = 0;
        c3 = 0;
        c4 = 0;
        drag_status = 0;
        repaint();
        Calcsize();
        displayUndo();
    }

    void crop() {

        // Crop cropping =new Crop();
        label_1.addMouseListener(this);
        label_1.addMouseMotionListener(this);
        // / cropping.start(this.f);

    }

    void displayUndo() {
        System.out.println(counter + "" + instanceCapture[counter]);
        ImageIcon img = instanceCapture[counter];
        // System.out.println(img);
        Image in = img.getImage();
        // System.out.println(in);
        BufferedImage bisrc = new BufferedImage(in.getWidth(this), in.getHeight(this), BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bisrc.createGraphics();
        // g2d.drawImage(bisrc, 0, 0, 160, 160, null);
        g2d.drawImage(in, 0, 0, null);

        g2d.dispose();
        float dislen = 140.0F;
        float dishght = 140.0F;
        float editiconRatio;
        System.out.println(bisrc.getWidth() + "and is  " + bisrc.getHeight());
        if (bisrc.getWidth() > bisrc.getHeight()) {

            editiconRatio = bisrc.getWidth() / bisrc.getHeight();
            dishght = dislen / editiconRatio;
            System.out.println(dislen + " and " + dishght);
        } else {
            editiconRatio = bisrc.getHeight() / bisrc.getWidth();
            dislen = dishght / editiconRatio;
        }
        BufferedImage bout = null;
        bout = new BufferedImage((int) dislen, (int) dishght, bisrc.getType());
        g2d = bout.createGraphics();
        g2d.drawImage(bisrc, 0, 0, (int) dislen, (int) dishght, null);

        g2d.dispose();

        ImageIcon img1 = new ImageIcon(bout);
        System.out.println(bisrc + "  " + img1);

        dis2.setIcon(img1);

    }

    void edge() {
        BufferedImage bisrc, bidest, bi;
        Image img = editIcon.getImage();

        bisrc = new BufferedImage(img.getWidth(this), img.getHeight(this), BufferedImage.TYPE_INT_RGB);
        Graphics2D big = bisrc.createGraphics();
        big.drawImage(img, 0, 0, this);
        bidest = new BufferedImage(img.getWidth(this), img.getHeight(this), BufferedImage.TYPE_INT_RGB);
        bi = bisrc;
        float data[] = {1.0f, 0.0f, -1.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, -1.0f};
        Kernel kernel = new Kernel(3, 3, data);
        ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        convolve.filter(bisrc, bidest);
        bi = bisrc;
        ImageIcon temp = new ImageIcon(bidest);
        editIcon = temp;
        counter = counter + 1;
        instanceCapture[counter] = editIcon;
        System.out.println("image at index   " + counter + "is" + instanceCapture[counter]);

        label_1.setIcon(temp);
        label_1.setIcon(temp);
        repaint();
        displayUndo();
        Calcsize();
    }

    void blur() {
        BufferedImage bisrc, bidest, bi;
        Image img = editIcon.getImage();

        bisrc = new BufferedImage(img.getWidth(this), img.getHeight(this), BufferedImage.TYPE_INT_RGB);
        Graphics2D big = bisrc.createGraphics();
        big.drawImage(img, 0, 0, this);
        bidest = new BufferedImage(img.getWidth(this), img.getHeight(this), BufferedImage.TYPE_INT_RGB);
        bi = bisrc;
        float data[] = {0.0625f, 0.125f, 0.0625f, 0.125f, 0.25f, 0.125f, 0.0625f, 0.125f, 0.0625f};
        Kernel kernel = new Kernel(3, 3, data);
        ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        convolve.filter(bisrc, bidest);
        bi = bisrc;
        ImageIcon temp = new ImageIcon(bidest);
        editIcon = temp;
        counter = counter + 1;
        instanceCapture[counter] = editIcon;
        System.out.println("image at index   " + counter + "is" + instanceCapture[counter]);

        label_1.setIcon(temp);
        repaint();
        displayUndo();
        Calcsize();
    }

    void red() {
        ImageIcon img = editIcon;
        BufferedImage bufferedInputImage = null, bufferedOutputImage = null;
        bufferedInputImage = new BufferedImage(img.getIconWidth(), // /image to
                // buffered
                // image
                img.getIconHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = bufferedInputImage.createGraphics();

        AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
        g2d.drawImage(img.getImage(), at, label_1);
        g2d.dispose(); // /image to buffered image

        img = new ImageIcon(bufferedInputImage);
        editIcon = img;
        counter = counter + 1;
        instanceCapture[counter] = editIcon;
        System.out.println("image at index   " + counter + "is" + instanceCapture[counter]);

        label_1.setIcon(img);
        repaint();
        displayUndo();
        Calcsize();
    }

    void imageRotate() {
        int angle = 0;
        if (rotation == 'l')
            angle = -90;
        else if (rotation == 'r')
            angle = 90;
        ImageIcon img = editIcon;
        BufferedImage bufferedInputImage = null, bufferedOutputImage = null;
        bufferedInputImage = new BufferedImage(img.getIconHeight(), img.getIconWidth(), BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedInputImage.createGraphics();
        double x = -(img.getIconWidth() - img.getIconHeight()) / 2.0;
        System.out.println(x);
        double y = -(img.getIconHeight() - img.getIconWidth()) / 2.0;
        AffineTransform at = AffineTransform.getTranslateInstance(x, y);
        g2d.rotate(Math.toRadians(angle), img.getIconHeight() / 2.0, img.getIconWidth() / 2.0);
        g2d.drawImage(img.getImage(), at, label_1);
        g2d.dispose();
        img = new ImageIcon(bufferedInputImage);
        editIcon = img;
        counter = counter + 1;
        instanceCapture[counter] = editIcon;
        // System.out.println("image at index " + counter + "is"
        // + instanceCapture[counter]);
        int temp = label_1.getHeight(), temp1 = label_1.getWidth();
        label_1.setBounds(20, 130, temp, temp1);
        label_1.setIcon(img);
        repaint();
        displayUndo();
        Calcsize();
        // img.paintIcon(null, g, 0, 0);
        // g.dispose();

        // Graphics2D g2d=(Graphics2D)bufferedInputImage.getGraphics();

		/*
		 * bufferedOutputImage = new BufferedImage(256, 192,
		 * bufferedInputImage.getType());
		 * g2d=bufferedOutputImage.createGraphics();
		 * g2d.drawImage(bufferedInputImage, 0, 0, 256, 192, null);
		 * g2d.dispose(); img=new ImageIcon(bufferedOutputImage);
		 * label_1.setIcon(img);
		 */
    }

    public void sepia() {

        BufferedImage img = null;

        ImageIcon icon = editIcon;
        Image i = icon.getImage();
        img = new BufferedImage(i.getWidth(this), i.getHeight(this), BufferedImage.TYPE_INT_RGB);

        Graphics g1 = img.createGraphics();
        g1.drawImage(i, 0, 0, null);
        g1.dispose();

        // get width and height of the image
        int width = img.getWidth();
        int height = img.getHeight();

        // convert to sepia
        for (int i1 = 0; i1 <= 255; i1++) {
            red[i1] = 0;
            blue[i1] = 0;
            green[i1] = 0;
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                red[r] = red[r] + 1;
                green[g]++;
                blue[b]++;
                // System.out.println(r + " " + g + " " + b);
                // calculate tr, tg, tb
                int tr = (int) (0.393 * r + 0.769 * g + 0.189 * b);
                int tg = (int) (0.349 * r + 0.686 * g + 0.168 * b);
                int tb = (int) (0.272 * r + 0.534 * g + 0.131 * b);
				/*
				 * int tr = (int )255-r; int tg = (int )255-g; int tb = (int
				 * )255-b;
				 */
                // check condition
                if (tr > 255) {
                    r = 255;
                } else {
                    r = tr;
                }

                if (tg > 255) {
                    g = 255;
                } else {
                    g = tg;
                }

                if (tb > 255) {
                    b = 255;
                } else {
                    b = tb;
                }

                // set new RGB value
                p = (a << 24) | (r << 16) | (g << 8) | b;

                img.setRGB(x, y, p);
            }
        }
        int redlar = largest(red);
        System.out.println(redlar + "                      red largest is");
        ImageIcon newI = new ImageIcon(img);
        ;
        label_1.setIcon(newI);

        editIcon = newI;
        counter = counter + 1;
        instanceCapture[counter] = editIcon;
        repaint();
        ;
        displayUndo();
        Calcsize();
		/*
		 * File currentDirFile = new File(" "); String path =
		 * currentDirFile.getAbsolutePath()+"temp.jpg"; f = new File(path );
		 * System.out.println(path );
		 * 
		 * try { ImageIO.write(img, "jpg", f); BufferedImage bufferedInputImage
		 * = FileDemo.readFile(path); ImageIcon temp = new
		 * ImageIcon(bufferedInputImage); // label_1.setIcon(temp); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
        // write image

    }

    public void negative() {

        BufferedImage img = null;
        // File f = null;
        ImageIcon icon = editIcon;
        Image i = icon.getImage();
        img = new BufferedImage(i.getWidth(this), i.getHeight(this), BufferedImage.TYPE_INT_RGB);

        Graphics g1 = img.createGraphics();
        g1.drawImage(i, 0, 0, null);
        g1.dispose();
        // read image
        // try {
        // f = new File("D:\\Image\\zeenat.jpg");
        // img = ImageIO.read(f);

        // } catch (IOException e) {
        // System.out.println(e);
        // }

        // get width and height of the image
        int width = img.getWidth();
        int height = img.getHeight();

        // convert to sepia
        for (int i1 = 0; i1 <= 255; i1++) {
            red[i1] = 0;
            blue[i1] = 0;
            green[i1] = 0;
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                red[r] = red[r] + 1;
                green[g]++;
                blue[b]++;
                // System.out.println(r + " " + g + " " + b);
                // calculate tr, tg, tb
				/*
				 * int tr = (int) (0.393 * r + 0.769 * g + 0.189 * b); int tg =
				 * (int) (0.349 * r + 0.686 * g + 0.168 * b); int tb = (int)
				 * (0.272 * r + 0.534 * g + 0.131 * b);
				 */
                int tr = (int) 255 - r;
                int tg = (int) 255 - g;
                int tb = (int) 255 - b;
                // check condition
                if (tr > 255) {
                    r = 255;
                } else {
                    r = tr;
                }

                if (tg > 255) {
                    g = 255;
                } else {
                    g = tg;
                }

                if (tb > 255) {
                    b = 255;
                } else {
                    b = tb;
                }

                // set new RGB value
                p = (a << 24) | (r << 16) | (g << 8) | b;

                img.setRGB(x, y, p);
            }
        }
        int redlar = largest(red);
        System.out.println(redlar + "                      red largest is");
        ImageIcon newI = new ImageIcon(img);
        ;
        label_1.setIcon(newI);

        editIcon = newI;
        counter = counter + 1;
        instanceCapture[counter] = editIcon;
        Calcsize();
        repaint();
        displayUndo();
		/*
		 * File currentDirFile = new File(" "); String path =
		 * currentDirFile.getAbsolutePath()+"temp.jpg"; f = new File(path );
		 * System.out.println(path );
		 * 
		 * try { ImageIO.write(img, "jpg", f); BufferedImage bufferedInputImage
		 * = FileDemo.readFile(path); ImageIcon temp = new
		 * ImageIcon(bufferedInputImage); // label_1.setIcon(temp); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
        // write image

    }

    public int largest(Integer temp[]) {
        int t1 = temp[0];
        for (int i = 1; i < temp.length; i++) {
            if (temp[i] > t1)
                t1 = temp[i];

        }
        return t1;

    }

    public void adjustBLUE() {
        BufferedImage img = null;
        File f = null;
        ImageIcon icon = editIcon;
        Image i = icon.getImage();
        img = new BufferedImage(i.getWidth(this), i.getHeight(this), BufferedImage.TYPE_INT_RGB);

        Graphics g1 = img.createGraphics();
        g1.drawImage(i, 0, 0, null);
        g1.dispose();
        // read image
        // try {
        // f = new File("D:\\Image\\zeenat.jpg");
        // img = ImageIO.read(f);

        // } catch (IOException e) {
        // System.out.println(e);
        // }

        // get width and height of the image
        int width = img.getWidth();
        int height = img.getHeight();

        // convert to sepia
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                // System.out.println(r + " " + g + " " + b);
                // calculate tr, tg, tb
                // int t= slider.getValue();
                float diff;
                float per;
                // System.out.println("r is" +r);
                int tb;
                if (slider_2.getValue() > 0) {
                    diff = 255.0F - (float) b;
                    per = diff / 10.0F;
                    tb = (int) ((float) b + ((float) slider_2.getValue()) * per);// +
                    // 0.769
                    // *
                    // g
                    // +
                    // 0.189
                    // *
                    // b);
                } else {
                    diff = (float) b;
                    per = diff / 10.0F;
                    tb = (int) ((float) b + ((float) slider_2.getValue()) * per);
                }

                if (tb > 255) {
                    b = 255;
                } else {
                    b = tb;
                }

                // set new RGB value
                p = (a << 24) | (r << 16) | (g << 8) | b;

                img.setRGB(x, y, p);
            }
        }
        System.out.println(slider_2.getValue());
        slider_2.setValue(0);
        ImageIcon newI = new ImageIcon(img);
        ;
        label_1.setIcon(newI);
        ;

        editIcon = newI;
        counter = counter + 1;
        instanceCapture[counter] = editIcon;
        Calcsize();
        repaint();
        displayUndo();
    }

    public void adjustGREEN() {
        BufferedImage img = null;
        File f = null;
        ImageIcon icon = editIcon;
        Image i = icon.getImage();
        img = new BufferedImage(i.getWidth(this), i.getHeight(this), BufferedImage.TYPE_INT_RGB);

        Graphics g1 = img.createGraphics();
        g1.drawImage(i, 0, 0, null);
        g1.dispose();
        // read image
        // try {
        // f = new File("D:\\Image\\zeenat.jpg");
        // img = ImageIO.read(f);

        // } catch (IOException e) {
        // System.out.println(e);
        // }

        // get width and height of the image
        int width = img.getWidth();
        int height = img.getHeight();

        // convert to sepia
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;
                // System.out.println(r + " " + g + " " + b);
                // calculate tr, tg, tb
                // int t= slider.getValue();
                float diff;
                float per;
                // System.out.println("r is" +r);
                int tg;
                if (slider_1.getValue() > 0) {
                    diff = 255.0F - (float) g;
                    per = diff / 10.0F;
                    tg = (int) ((float) g + ((float) slider_1.getValue()) * per);// +
                    // 0.769
                    // *
                    // g
                    // +
                    // 0.189
                    // *
                    // b);
                } else {
                    diff = (float) g;
                    per = diff / 10.0F;
                    tg = (int) ((float) g + ((float) slider_1.getValue()) * per);
                }

                if (tg > 255) {
                    g = 255;
                } else {
                    g = tg;
                }

                // set new RGB value
                p = (a << 24) | (r << 16) | (g << 8) | b;

                img.setRGB(x, y, p);
            }
        }
        System.out.println(slider_1.getValue());
        slider_1.setValue(0);
        ImageIcon newI = new ImageIcon(img);
        ;
        label_1.setIcon(newI);
        ;

        editIcon = newI;
        counter = counter + 1;
        instanceCapture[counter] = editIcon;
        Calcsize();
        repaint();
        displayUndo();
    }

    public void adjustRED() {
        BufferedImage img = null;
        File f = null;
        ImageIcon icon = editIcon;
        Image i = icon.getImage();
        img = new BufferedImage(i.getWidth(this), i.getHeight(this), BufferedImage.TYPE_INT_RGB);

        Graphics g1 = img.createGraphics();
        g1.drawImage(i, 0, 0, null);
        g1.dispose();

        // get width and height of the image
        int width = img.getWidth();
        int height = img.getHeight();

        // convert to sepia
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                float diff;
                float per;
                // System.out.println("r is" +r);
                int tr;
                if (slider.getValue() > 0) {
                    diff = 255.0F - (float) r;
                    per = diff / 10.0F;
                    tr = (int) ((float) r + ((float) slider.getValue()) * per);
                } else {
                    diff = (float) r;
                    per = diff / 10.0F;
                    tr = (int) ((float) r + ((float) slider.getValue()) * per);
                }

                // check condition
                if (tr > 255) {
                    r = 255;
                } else {
                    r = tr;
                }

                // set new RGB value
                p = (a << 24) | (r << 16) | (g << 8) | b;

                img.setRGB(x, y, p);
            }
        }
        System.out.println(slider.getValue());
        slider.setValue(0);
        ImageIcon newI = new ImageIcon(img);
        ;
        label_1.setIcon(newI);
        ;

        editIcon = newI;
        counter = counter + 1;
        instanceCapture[counter] = editIcon;
        Calcsize();
        repaint();
        displayUndo();
    }

    void save() {

        FileDialog d = new FileDialog(this, "Save Dialog", FileDialog.SAVE);
        d.setVisible(true);
        String path = d.getDirectory() + d.getFile();
        System.out.println(d.getDirectory() + " -----  " + d.getFile());

        System.out.println(path);
        try {


            FileDemo.saveFile(path, editIcon);
            System.out.println(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // textArea.setText("Cant Save File "+e);
        }
    }

    public void eq() {

        ImageIcon icon = editIcon;
        save();
        Image i = icon.getImage();
        BufferedImage img = new BufferedImage(i.getWidth(this), i.getHeight(this), BufferedImage.TYPE_INT_RGB);

        Graphics g1 = img.createGraphics();
        g1.drawImage(i, 0, 0, null);
        g1.dispose();


        BufferedImage buferedoutput = HistogramEQ.histogramEqualization(img);
        ImageIcon newI = new ImageIcon(buferedoutput);

        editIcon = newI;

        label_1.setIcon(newI);
        repaint();
        Calcsize();
        displayUndo();
    }
}
