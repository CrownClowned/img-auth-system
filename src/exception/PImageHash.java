/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author s
 */
public interface PImageHash {
    /*
      Calculates a perceptual hash of the image loaded from the given file.
      imageFile is the file from which to load the image to be hashed
      returns a 64-bit perceptual hash of the image in the given file
      throws IOException if an image could not be loaded from the given file for any reason
     */
    long getAHash(final File imageFile) throws IOException;
    /*
    Calculates a perceptual hash of the given image.
    image is the image to be hashed
    returns a 64-bit perceptual hash of the given image
     */
    long getAHash(final Image image); 
}
