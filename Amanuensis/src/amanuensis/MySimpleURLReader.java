package amanuensis;

import cs1.SimpleURLReader;
/*Lab02
 *MySimpleURLReader class
 *
 *Author:Fırat Özbay
 *
 *Takes txt files in a browser page can return the page content
 */
class MySimpleURLReader extends SimpleURLReader {
	String url;
	
	public MySimpleURLReader(String string)
	{
		super(string);
		url = string;
	}
	
	public String getURL()
	{
		return url;
	}

	public String getName()
	{
            String str = "";

            for(int i = 0; i<url.length(); i++)
            {
                if (url.charAt(i) == '/')	
                {
                    str = "";					//Restarts the string
                }
                else
                {
                    str+=url.charAt(i);                             //Adds next letter to string
                }
            }

            return str;
	}
	
	public String getPageContents()			//Return content
	{	
		return super.getPageContents().substring(4);
	}
        
	
}
