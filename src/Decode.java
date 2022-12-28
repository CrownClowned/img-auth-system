import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import exception.Constants;
import exception.MessageConstants;
import exception.PImageHash;
import exception.SteganographyException;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author s
 */
public class Decode extends javax.swing.JFrame {
    private String STEGIMAGE;
    private static String name;
    private String uname[] = new String[1];
    /**
     * Creates new form Decode
     */
    public Decode() {
        initComponents();
        this.setLocationRelativeTo(null);
        lb1.setText("");
    }
    public String decode(final BufferedImage bufferedImage)throws SteganographyException {
        byte[] decode;
        try {
            decode = this.recoverText(bufferedImage, 0);
            return new String(decode);//explicit casting
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this,"The Image is not certified.");
            throw new SteganographyException(MessageConstants.DECODING_ERROR_GENERAL);
        }
    }
    private BufferedImage readImageFile() {
        BufferedImage image;
        File f = new File(STEGIMAGE);
        try{
            image = ImageIO.read(f);
            return image;
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Image could not be read!","Error",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return null;
    }
    private BufferedImage readImageFile(String s) {
        BufferedImage image;
        File f = new File(s);
        try{
            image = ImageIO.read(f);
            return image;
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Image could not be read!","Error",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return null;
    }
    /*private static byte[] getByteData(BufferedImage image){
    WritableRaster raster   = image.getRaster();
    DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
    return buffer.getData();
}*/
    private byte[] recoverText(final BufferedImage bufferedImage, final int startingOffset) {
        // Initialize starting variables
        final int height = bufferedImage.getHeight();
        final int offset = startingOffset + Constants.HIDDEN_MESSAGE_BIT_LENGTH;//0,0,0,20
        int length = 0;
        // Loop through 32 bytes of data to determine text length
        for (int i = startingOffset; i < offset; ++i) {
            final int h = i / height;
            final int w = i % height;//(0,0),(1,0)......
            final int imageValue = bufferedImage.getRGB(h, w);
            length = (length << 1) | (imageValue & 1);
        }
        byte[] result = new byte[length];
        // Initialize variables for iteration
        int i = offset / height;
        int j = offset % height;
        // Iterate from zero to message length
        for (int letter = 0; letter < length; ++letter) {
            // Iterates over each bit for the hidden message
            for (int bit = 7; bit >= 0; --bit) {
            // Gets the byte from the image
                final int imageValue = bufferedImage.getRGB(i, j);
                // Calculates the bit for the message
                result[letter] = (byte) ((result[letter] << 1) | (imageValue & 1));//[12,45,90,56....}
                if(j < (height - 1)){
                    ++j;
                }
                else{
                    ++i;
                    j = 0;
                }
            }   
        }
        return result;
    }
     private int waterMarkCheck(String iname, String cer, String dig_sign){//for signature comaprison
        int ch=0;
        try{
            String query = "select imgname,certificate,d_signature,uname from imgtable where imgName = ? and uname = ?";
            PreparedStatement st;
            st = DriverManager.getConnection("jdbc:mysql://localhost:3306/imgAuth","root","password").prepareStatement(query);
            String uname = name;
            st.setString(1,iname);
            st.setString(2, uname);
            ResultSet rs = st.executeQuery();
            System.out.println(cer);
            System.out.println(dig_sign);
            System.out.println();
            if(rs.next()){
                String cert = rs.getString(2);
                System.out.println(cert);
                String d_sig = rs.getString(3);
                if(cert.equals(cer) && dig_sign.equals(d_sig))
                    ch=1;
                else if(cert.equals(cer) && dig_sign.equals(d_sig)==false)
                    ch=2;
                else if(cert.equals(cer)==false && dig_sign.equals(d_sig))
                    ch=3;
                else
                    ch=4;
            }
            else{
                JOptionPane.showMessageDialog(this, "Invalid Image Name!","ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        return ch;
     }
     private void display(){
        BufferedImage theImage = readImageFile(STEGIMAGE);
        Image dimg = theImage.getScaledInstance(lb1.getWidth(), lb1.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon dImage = new ImageIcon(dimg);
        lb1.setIcon(dImage);
    }
    /*private BufferedImage user_space(BufferedImage image)
	{
		//create new_img with the attributes of image
		BufferedImage new_img  = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D    graphics = new_img.createGraphics();
		graphics.drawRenderedImage(image, null);
		graphics.dispose(); //release all allocated memory for this image
		return new_img;
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        t1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        b2 = new javax.swing.JButton();
        lb1 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        t2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AUTHENTICATION");
        setMinimumSize(new java.awt.Dimension(952, 750));
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setForeground(new java.awt.Color(51, 51, 51));

        jLabel4.setFont(new java.awt.Font("Poor Richard", 1, 20)); // NOI18N
        jLabel4.setText("WATERMARK:");

        jLabel5.setFont(new java.awt.Font("Poor Richard", 1, 20)); // NOI18N
        jLabel5.setText("IMAGE :");

        jLabel6.setFont(new java.awt.Font("Poor Richard", 1, 20)); // NOI18N
        jLabel6.setText("IMAGE :");

        t1.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N

        jButton5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton5.setText("REVEAL TEXT");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton6.setText("EXIT");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        b2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        b2.setText("BROWSE IMAGE");
        b2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b2ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton7.setText("HOME");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        t2.setEditable(false);
        t2.setBackground(new java.awt.Color(255, 255, 255));
        t2.setFont(new java.awt.Font("Palatino Linotype", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(jButton5)
                .addGap(116, 116, 116)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 176, 176))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(t1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(t2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(132, 132, 132)
                .addComponent(b2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(lb1, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(430, 430, 430)
                    .addComponent(jLabel5)
                    .addContainerGap(450, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(t1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(t2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(50, 50, 50)
                .addComponent(lb1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(74, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(333, 333, 333)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(417, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 950, 750);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try{
            if(t1.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Empty IMAGE NAME field!","ERROR",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BufferedImage theImage = readImageFile();
            AverageHashImageHash avg = new AverageHashImageHash();
            long bWatermarkingHash = avg.getAHash(theImage);
            System.out.println(bWatermarkingHash);
            String wh = String.valueOf(bWatermarkingHash);
            String txt = decode(theImage);
            t2.setText(txt);
            System.out.println(txt);
            String img = t1.getText();
            int choice = waterMarkCheck(img , txt, wh);
            switch (choice){
                case 1:
                JOptionPane.showMessageDialog(null,"Image is verified");
                break;
                case 2:
                JOptionPane.showMessageDialog(null,"Image Certificate has been altered!","WARNING",JOptionPane.ERROR_MESSAGE);
                break;
                case 3:
                JOptionPane.showMessageDialog(null,"Image is not certified!","DUPLICATE IMAGE",JOptionPane.ERROR_MESSAGE);
                break;
                case 4:
                JOptionPane.showMessageDialog(null,"Image has been altered!","WARNING",JOptionPane.ERROR_MESSAGE);
                break;
            }
            //JOptionPane.showMessageDialog(this,"Program Execution Complete");
        }
        catch(SteganographyException ex){
            Logger.getLogger(Decode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void b2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b2ActionPerformed
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("C:/Users/s/OneDrive/Pictures"));
        //fc.addChoosableFileFilter(new ImageFilter());
        //In response to a button click:

        int returnVal = fc.showOpenDialog(b2);
        if (returnVal == JFileChooser.APPROVE_OPTION){
            File file = fc.getSelectedFile();
        }
        STEGIMAGE = fc.getSelectedFile().getAbsolutePath();
        File currFile = fc.getSelectedFile();
        t1.setText(currFile.getName());
        display();
    }//GEN-LAST:event_b2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        uname[0]=name;
        HomePage hp = new HomePage();
        hp.main(uname);
        hp.pack();
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed
    class AverageHashImageHash implements PImageHash {

    @Override
    public long getAHash(final File imageFile) throws IOException {
        return this.getAHash(ImageIO.read(imageFile));
    }
    @Override
    public long getAHash(final Image image) {
        // Start by rescaling the image to an 8x8 square (a total of 64 pixels, each of which will ultimately map to a
        // bit in our hash). This may involve some squishing (or, in rare cases, stretching), but that's fine for our
        // purposes. We also want to go to greyscale so we only have a single channel to worry about.
        final BufferedImage scaledImage = new BufferedImage(8, 8, BufferedImage.TYPE_BYTE_GRAY);
        {
            final Graphics2D graphics = scaledImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.drawImage(image, 0, 0, 8, 8, null);
            graphics.dispose();
        }
        final int[] pixels = new int[64];
        scaledImage.getData().getPixels(0, 0, 8, 8, pixels);
        final int average;
        int total = 0;
        for (int pixel : pixels) {
            total += pixel;
        }
        average = total / 64;
        long hash = 0;
        for (final int pixel : pixels) {
            hash <<= 1;
            if (pixel > average) {
                hash |= 1;
            }
        }
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
            java.util.logging.Logger.getLogger(Decode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Decode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Decode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Decode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Decode().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lb1;
    private javax.swing.JTextField t1;
    private javax.swing.JTextField t2;
    // End of variables declaration//GEN-END:variables
}
