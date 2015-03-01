/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amanuensis;

/**interface LevelListener
 *
 * Level listeners must have the methods levelIsChanged( int level) and levelTimer( int time, int timeMax)
 * @author Hexahedron
 */
public interface LevelListener {
    
    public void levelIsChanged( int level);
    
    public void levelTimer( int time, int timeMax);
}
