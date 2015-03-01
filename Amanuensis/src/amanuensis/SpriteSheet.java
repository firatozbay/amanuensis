package amanuensis;


import java.awt.image.BufferedImage;
/** class SpriteSheet
 *  
 * The game has sprites
 * @author Hexahedron
 */
public class SpriteSheet {

	private final BufferedImage sheet;
	
	public SpriteSheet(BufferedImage sheet){
		this.sheet = sheet;
	}
	
	public BufferedImage crop(int col, int row, int w, int h){
		return sheet.getSubimage(col * 300, row * 300, w, h);
	}
	
        public BufferedImage crop2(int col, int row, int w, int h){
            return sheet.getSubimage(col * 500, row * 500, w, h);
        }
}
