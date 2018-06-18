package fluffybunny.malbunny.xmlParsing;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "myinfo")
public class MyInfo
{
    private String user_name;

    private String user_total_watching;

    private String user_total_plantowatch;

    private String user_total_dropped;

    private String user_total_onhold;

    private String user_id;

    private String user_export_type;

    private String user_total_completed;

    private String user_total_anime;

    public String getUser_name ()
    {
        return user_name;
    }

    public void setUser_name (String user_name)
    {
        this.user_name = user_name;
    }

    public String getUser_total_watching ()
    {
        return user_total_watching;
    }

    public void setUser_total_watching (String user_total_watching)
    {
        this.user_total_watching = user_total_watching;
    }

    public String getUser_total_plantowatch ()
    {
        return user_total_plantowatch;
    }

    public void setUser_total_plantowatch (String user_total_plantowatch)
    {
        this.user_total_plantowatch = user_total_plantowatch;
    }

    public String getUser_total_dropped ()
    {
        return user_total_dropped;
    }

    public void setUser_total_dropped (String user_total_dropped)
    {
        this.user_total_dropped = user_total_dropped;
    }

    public String getUser_total_onhold ()
    {
        return user_total_onhold;
    }

    public void setUser_total_onhold (String user_total_onhold)
    {
        this.user_total_onhold = user_total_onhold;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getUser_export_type ()
    {
        return user_export_type;
    }

    public void setUser_export_type (String user_export_type)
    {
        this.user_export_type = user_export_type;
    }

    public String getUser_total_completed ()
    {
        return user_total_completed;
    }

    public void setUser_total_completed (String user_total_completed)
    {
        this.user_total_completed = user_total_completed;
    }

    public String getUser_total_anime ()
    {
        return user_total_anime;
    }

    public void setUser_total_anime (String user_total_anime)
    {
        this.user_total_anime = user_total_anime;
    }

    @Override
    public String toString()
    {
        return "MyInfo: [user_name = "+user_name+", user_total_watching = "+user_total_watching+", user_total_plantowatch = "+user_total_plantowatch+", user_total_dropped = "+user_total_dropped+", user_total_onhold = "+user_total_onhold+", user_id = "+user_id+", user_export_type = "+user_export_type+", user_total_completed = "+user_total_completed+", user_total_anime = "+user_total_anime+"]";
    }
}
		