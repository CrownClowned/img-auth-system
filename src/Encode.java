
import exception.SteganographyException;
import exception.MessageConstants;
import exception.Constants;
import exception.PImageHash;
import java.io.File;
import javax.swing.JFileChooser;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import exception.SteganographyException;
import java.awt.Color;
//import java.awt.Canvas;
//import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.text.SimpleDateFormat;  
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author s
 */
public class Encode extends javax.swing.JFrame {

    /**
     * Creates new form Encode
     */
    private static String name;
    private String uname[] = new String[1];
    private String IMAGEFILE;
    private String STEGIMAGE="C:/Users/s/OneDrive/Pictures/steg/";
    
    //dImage = new ImageIcon(IMAGEFILE.getScaledInstance(790, 400));
    public Encode(){
        initComponents();
        this.setLocationRelativeTo(null);
        lb1.setText("");
        t2.setText("e.g. A.B");
        t2.setForeground(Color.LIGHT_GRAY);
    }
    public BufferedImage encode(final BufferedImage bufferedImage, final String message,final int offset)throws SteganographyException{
        return this.addText(bufferedImage, message);
    }
    private BufferedImage addText(BufferedImage bufferedImage, final String text)throws SteganographyException{
    // Convert all items to byte arrays: image, message, message length
        final byte msg[] = text.getBytes();//getbytes is a String method
        final byte len[] = bitConversion(msg.length);
        try{// Encode the message length
            bufferedImage = this.encodeText(bufferedImage, len, 0);//first len then message
            // Encode the message
            bufferedImage = this.encodeText(bufferedImage, msg, 0 + Constants.HIDDEN_MESSAGE_BIT_LENGTH);
        }
        catch (SteganographyException e){
            throw new SteganographyException(MessageConstants.ENCODING_ERROR_GENERAL, e);
        }
        return bufferedImage;
    }
    /*private void Hash(){
        BufferedImage image = resizeTo8x8();
        BufferedImage gImage = toGreyscale(image);
        String hash = buildHash(gImage);
    }*/
    private BufferedImage readImageFile() {
        BufferedImage image;
        File f = new File(IMAGEFILE);
        try{
            image = ImageIO.read(f);
        }
        catch(IOException e){
            System.exit(0);
            return null;
        }
        return image;
    }
    /*private byte[] getByteData(BufferedImage image){
                WritableRaster raster   = image.getRaster();
                DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
                return buffer.getData();
    }*/
    private byte[] bitConversion(int i){
        byte byte3 = (byte)((i & 0xFF000000) >>> 24); //0=255,0,0,0
        byte byte2 = (byte)((i & 0x00FF0000) >>> 16); //0=0,255,0,0
        byte byte1 = (byte)((i & 0x0000FF00) >>> 8 ); //0=0,0,255,0
        byte byte0 = (byte)((i & 0x000000FF)       );//0,0,0,2555
        //{0,0,0,byte0} is equivalent, since all shifts >=8 will be 0
        return(new byte[]{byte3,byte2,byte1,byte0});
    }
    private BufferedImage encodeText(final BufferedImage bufferedImage, final byte[] addition,final int offset) throws SteganographyException{
        // Gets the image dimensions
        final int height = bufferedImage.getHeight();
        final int width = bufferedImage.getWidth();
        // Initialize variables for iteration
        int i = offset / height;//values of i and j are used to set rgb values for eg: (0,0),(0,1),(0,2)
        int j = offset % height;//if(offset is 8 then i=0;j=8)(i,j) = (0,0),(1,0)

        if ((width * height) >= (addition.length * 8 + offset)) {//msg = hello 
                                                            //for if condition 320*902 >= 32 + 0....Hence will not enter the block
        // Iterates over all message's bytes
        for (final byte add : addition){//Iterates over all of the bits in the current byte
            for(int bit = 7; bit >= 0; --bit){// Gets the original image byte value
                final int imageValue = bufferedImage.getRGB(i, j);// Calculates the new image byte value//255,255,0,0 = 19078454747
                int b = (add >>> bit) & 1;//20>>>7 &1
                final int imageNewValue = ((imageValue & 0xFFFFFFFE) | b);// Sets the new image byte value
                bufferedImage.setRGB(i, j, imageNewValue);
                if(j < (height - 1)){
                    ++j;
                } 
                else{
                    ++i;
                    j = 0;
                }
            }
        }
    } 
    else{
      throw new SteganographyException(MessageConstants.ENCODING_ERROR_BIG_MESSAGE);
    }
    return bufferedImage;
  }
    private void display(){
        BufferedImage theImage = readImageFile();
        Image dimg = theImage.getScaledInstance(lb1.getWidth(), lb1.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon dImage = new ImageIcon(dimg);
        lb1.setIcon(dImage);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        b3 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        t1 = new javax.swing.JTextField();
        t3 = new javax.swing.JTextField();
        t2 = new javax.swing.JTextField();
        b1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lb1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WATERMARK");
        setMinimumSize(new java.awt.Dimension(955, 754));

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));

        b3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        b3.setText("COPYRIGHT");
        b3.setMaximumSize(new java.awt.Dimension(119, 25));
        b3.setMinimumSize(new java.awt.Dimension(119, 25));
        b3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b3ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton3.setText("HOME");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton4.setText("EXIT");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Poor Richard", 1, 20)); // NOI18N
        jLabel4.setText("ENTER YOUR INITIALS:");

        jLabel5.setFont(new java.awt.Font("Poor Richard", 1, 20)); // NOI18N
        jLabel5.setText("IMAGE :");

        t1.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        t1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t1MouseClicked(evt);
            }
        });

        t3.setEditable(false);
        t3.setBackground(new java.awt.Color(255, 255, 255));
        t3.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        t3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t3MouseClicked(evt);
            }
        });

        t2.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N
        t2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t2MouseClicked(evt);
            }
        });
        t2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                t2KeyTyped(evt);
            }
        });

        b1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        b1.setText("BROWSE IMAGE");
        b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Poor Richard", 1, 20)); // NOI18N
        jLabel6.setText("COPYRIGHT TEXT :");

        lb1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 143, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addGap(59, 59, 59)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(t3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(t1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(283, 283, 283))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(t2, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(b1)
                                .addGap(58, 58, 58))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(lb1, javax.swing.GroupLayout.PREFERRED_SIZE, 778, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(t1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(t2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(b1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(t3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(lb1, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b3ActionPerformed
        try {
            if(t2.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Empty IMAGE NAME Field!","ERROR",JOptionPane.ERROR_MESSAGE);
                return;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("_dd/MM/yyyy_HH:mm:ss");
            Date date = new Date();
            AverageHashImageHash avg = new AverageHashImageHash();
            String txt = t2.getText();
            String cprt = txt + formatter.format(date);
            System.out.println("Text: "+ cprt);
            t3.setText(cprt);
            BufferedImage theImage = readImageFile();
            File f = new File(STEGIMAGE);
            BufferedImage newImage = addText(theImage,cprt);
            ImageIO.write(newImage,"png",f);
            JOptionPane.showMessageDialog(this,"Image successfully watermarked.");
            long iWatermarkingHash = avg.getAHash(theImage);
            String wh = String.valueOf(iWatermarkingHash);
            System.out.println(iWatermarkingHash);
            Class.forName("java.sql.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/imgAuth","root","amolikamehta@2004");
            Statement s= con.createStatement();
            String r = t1.getText();
            String n = t3.getText();
            String uname = name;
            System.out.println(uname);
            String sql = "Insert into imgtable(imgName,certificate,d_signature,uname)values('"+(r)+"','"+(n)+"','"+(wh)+"','"+(uname)+"')";
            //
            s.executeUpdate(sql);
            //JOptionPane.showMessageDialog(this, "Record Inserted onto student table");
            //MyPanel mp = new MyPanel();
            /*BufferedImage myPicture = ImageIO.read(new File(IMAGEFILE));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            add(picLabel);*/
            //mp.display();
            s.close();
            con.close();

        } catch (SteganographyException | HeadlessException | IOException ex) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_b3ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        uname[0]=name;
        HomePage hp = new HomePage();
        hp.main(uname);
        hp.pack();
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void b1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b1ActionPerformed
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("C:/Users/s/OneDrive/Pictures"));
        //fc.addChoosableFileFilter(new ImageFilter());
        //In response to a button click:
        int returnVal = fc.showOpenDialog(b1);
        if (returnVal == JFileChooser.APPROVE_OPTION){
            File file = fc.getSelectedFile();
        }
        IMAGEFILE = fc.getSelectedFile().getAbsolutePath();
        File currFile = fc.getSelectedFile();
        t1.setText(currFile.getName());
        STEGIMAGE = STEGIMAGE + currFile.getName();
        //ImagePanel ip = new ImagePanel(IMAGEFILE);
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(IMAGEFILE));
        } catch (IOException ex) {
            Logger.getLogger(Encode.class.getName()).log(Level.SEVERE, null, ex);
        }
        display();
    }//GEN-LAST:event_b1ActionPerformed

    private void t1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t1MouseClicked
        if(t2.getText().equals("")){
            t2.setText("e.g. A.B");
            t2.setForeground(Color.LIGHT_GRAY);
        }
    }//GEN-LAST:event_t1MouseClicked

    private void t3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t3MouseClicked
        if(t2.getText().equals("")){
            t2.setText("e.g. A.B");
            t2.setForeground(Color.LIGHT_GRAY);
        }
    }//GEN-LAST:event_t3MouseClicked

    private void t2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t2MouseClicked
        if(t2.getText().equals("e.g. A.B")){
             t2.setForeground(Color.BLACK);
            t2.setText("");
        }
    }//GEN-LAST:event_t2MouseClicked

    private void t2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t2KeyTyped
        if(t2.getText().equals("e.g. A.B")){
             t2.setForeground(Color.BLACK);
            t2.setText("");
        }
    }//GEN-LAST:event_t2KeyTyped
class AverageHashImageHash implements PImageHash {

    @Override
    public long getAHash(final File imageFile) throws IOException {
        return this.getAHash(ImageIO.read(imageFile));
    }
    /*
    . . . . .
    . . . . .
    . . . . .
    . . . . .
    */
    @Override
    public long getAHash(final Image image) {
        //basic logic/basic algo is that you have to convert the image into 8*8 because you only want 64 pixels;
        //resize image
        //grayscale the image
        //binarize the gryscale using mean
        //convert it to integer
        //average = addition of 64 pix/64
        // Start by rescaling the image to an 8x8 square (a total of 64 pixels, each of which will ultimately map to a
        // bit in our hash). This may involve some squishing (or, in rare cases, stretching), but that's fine for our
        // purposes. We also want to go to greyscale so we only have a single channel to worry about.
        final BufferedImage scaledImage = new BufferedImage(8, 8, BufferedImage.TYPE_BYTE_GRAY);
        {
            final Graphics2D graphics = scaledImage.createGraphics();
            //interpolation - resize and distorting image
            // in a 2*2 area we calculate the average pixel value for an unknown pixel using average height of each pixel
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            graphics.drawImage(image, 0, 0, 8, 8, null);

            graphics.dispose();
        }

        final int[] pixels = new int[64];
        scaledImage.getData().getPixels(0, 0, 8, 8, pixels);

        final int average;
        {
            int total = 0;

            for (int pixel : pixels) {
                total += pixel;
            }
            average = total / 64;
        }

        long hash = 0;

        for (final int pixel : pixels) {
            hash <<= 1;

            if (pixel > average) {
                hash |= 1;
            }
        }
//6883619571623264255
        return hash;
    }
}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        name = args[0];
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Encode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Encode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Encode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Encode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Encode().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b1;
    private javax.swing.JButton b3;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lb1;
    private javax.swing.JTextField t1;
    private javax.swing.JTextField t2;
    private javax.swing.JTextField t3;
    // End of variables declaration//GEN-END:variables
}
