/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amanuensis;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**class ImageModificator
 *
 * Image modificator to change properties
 * @author Hexahedron
 */

public class ImageModificator {
    
    public BufferedImage flip( BufferedImage image) // mirro image
    {
        if ( image != null)
        {
            Graphics2D g = image.createGraphics();
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx,
                 null);
            return op.filter(image, null);
            
        }
        return image;
    }
    
    public BufferedImage resize( BufferedImage image, double newWidth)
    {
        if( image != null)
        {
            Graphics2D g = image.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(newWidth, newWidth);
            AffineTransformOp op = new AffineTransformOp(at,
                 null);
            return op.filter(image, null);
        }
        return null;  
    }
    
    public BufferedImage resize( BufferedImage image, double newWidth, double newHeight)
    {
        if( image != null)
        {
            Graphics2D g = image.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(newWidth, newHeight);
            AffineTransformOp op = new AffineTransformOp(at,
                 null);
            return op.filter(image, null);
        }
        return null;  
    }
    
    public BufferedImage rotate( BufferedImage image, double vecx, double vecy)
    {
        if( image != null)
        {
            double a = Math.sqrt( vecx*vecx + vecy*vecy);
            double sinQ = Math.abs(vecy/a);
            double cosQ = Math.abs(vecx/a);
            double w = image.getWidth();
            double h = image.getHeight();
            Graphics2D g = image.createGraphics();
            AffineTransform rt = AffineTransform.getScaleInstance(1, 1);
            rt.translate( (cosQ*w + sinQ*h)/2 - w/2, (sinQ*w + cosQ*h)/2- h/2);
            rt.rotate( vecx, vecy, (double)(image.getWidth()/2), (double)(image.getHeight()/2));
            AffineTransformOp op = new AffineTransformOp(rt,
                     null);
                return op.filter(image, null); 
        }
        return image;    
    }  
}

