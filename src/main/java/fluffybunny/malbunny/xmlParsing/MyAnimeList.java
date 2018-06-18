package fluffybunny.malbunny.xmlParsing;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType( propOrder = { "myinfo", "anime"})
@XmlRootElement(name = "myanimelist")
public class MyAnimeList {
	 	
	    private MyInfo myinfo;

	    private ArrayList<Anime> anime;

	    
	    public MyInfo getMyinfo ()
	    {
	        return myinfo;
	    }

	    @XmlElement(name = "myinfo")
	    public void setMyinfo (MyInfo myinfo)
	    {
	        this.myinfo = myinfo;
	    }

	    public ArrayList<Anime> getAnime ()
	    {
	        return anime;
	    }

	 	@XmlElement(name = "anime")
	    public void setAnime (ArrayList<Anime> anime)
	    {
	        this.anime = anime;
	    }

	    @Override
	    public String toString()
	    {
	        return "My Anime List: [myinfo = "+myinfo+", anime = "+anime+"]";
	    }
}
