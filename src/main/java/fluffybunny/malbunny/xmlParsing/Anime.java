package fluffybunny.malbunny.xmlParsing;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Anime
{
    private String series_type;

    private String my_storage;

    private String my_rated;

    private String my_watched_episodes;

    private String my_status;

    private String series_title;

    private String my_rewatching;

    private String my_finish_date;

    private String[] content;

    private String my_dvd;

    private int series_animedb_id;

    private int my_id;

    private String my_start_date;

    private String update_on_import;

    private String my_rewatch_value;

    private String my_comments;

    private String my_tags;

    private int my_score;

    private String series_episodes;

    private int my_times_watched;

    private int my_rewatching_ep;

    public String getSeries_type ()
    {
        return series_type;
    }

    public void setSeries_type (String series_type)
    {
        this.series_type = series_type;
    }

    public String getMy_storage ()
    {
        return my_storage;
    }

    public void setMy_storage (String my_storage)
    {
        this.my_storage = my_storage;
    }

    public String getMy_rated ()
    {
        return my_rated;
    }

    public void setMy_rated (String my_rated)
    {
        this.my_rated = my_rated;
    }

    public String getMy_watched_episodes ()
    {
        return my_watched_episodes;
    }

    public void setMy_watched_episodes (String my_watched_episodes)
    {
        this.my_watched_episodes = my_watched_episodes;
    }

    public String getMy_status ()
    {
        return my_status;
    }

    public void setMy_status (String my_status)
    {
        this.my_status = my_status;
    }

    public String getSeries_title ()
    {
        return series_title;
    }

    public void setSeries_title (String series_title)
    {
        this.series_title = series_title;
    }

    public String getMy_rewatching ()
    {
        return my_rewatching;
    }

    public void setMy_rewatching (String my_rewatching)
    {
        this.my_rewatching = my_rewatching;
    }

    public String getMy_finish_date ()
    {
        return my_finish_date;
    }

    public void setMy_finish_date (String my_finish_date)
    {
        this.my_finish_date = my_finish_date;
    }

    public String[] getContent ()
    {
        return content;
    }

    public void setContent (String[] content)
    {
        this.content = content;
    }

    public String getMy_dvd ()
    {
        return my_dvd;
    }

    public void setMy_dvd (String my_dvd)
    {
        this.my_dvd = my_dvd;
    }

    public int getSeries_animedb_id ()
    {
        return series_animedb_id;
    }

    public void setSeries_animedb_id (int series_animedb_id)
    {
        this.series_animedb_id = series_animedb_id;
    }

    public int getMy_id ()
    {
        return my_id;
    }

    public void setMy_id (int my_id)
    {
        this.my_id = my_id;
    }

    public String getMy_start_date ()
    {
        return my_start_date;
    }

    public void setMy_start_date (String my_start_date)
    {
        this.my_start_date = my_start_date;
    }

    public String getUpdate_on_import ()
    {
        return update_on_import;
    }

    public void setUpdate_on_import (String update_on_import)
    {
        this.update_on_import = update_on_import;
    }

    public String getMy_rewatch_value ()
    {
        return my_rewatch_value;
    }

    public void setMy_rewatch_value (String my_rewatch_value)
    {
        this.my_rewatch_value = my_rewatch_value;
    }

    public String getMy_comments ()
    {
        return my_comments;
    }

    public void setMy_comments (String my_comments)
    {
        this.my_comments = my_comments;
    }

    public String getMy_tags ()
    {
        return my_tags;
    }

    public void setMy_tags (String my_tags)
    {
        this.my_tags = my_tags;
    }

    public int getMy_score ()
    {
        return my_score;
    }

    public void setMy_score (int my_score)
    {
        this.my_score = my_score;
    }

    public String getSeries_episodes ()
    {
        return series_episodes;
    }

    public void setSeries_episodes (String series_episodes)
    {
        this.series_episodes = series_episodes;
    }

    public int getMy_times_watched ()
    {
        return my_times_watched;
    }

    public void setMy_times_watched (int my_times_watched)
    {
        this.my_times_watched = my_times_watched;
    }

    public int getMy_rewatching_ep ()
    {
        return my_rewatching_ep;
    }

    public void setMy_rewatching_ep (int my_rewatching_ep)
    {
        this.my_rewatching_ep = my_rewatching_ep;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [series_type = "+series_type+", my_storage = "+my_storage+", my_rated = "+my_rated+", my_watched_episodes = "+my_watched_episodes+", my_status = "+my_status+", series_title = "+series_title+", my_rewatching = "+my_rewatching+", my_finish_date = "+my_finish_date+", content = "+content+", my_dvd = "+my_dvd+", series_animedb_id = "+series_animedb_id+", my_id = "+my_id+", my_start_date = "+my_start_date+", update_on_import = "+update_on_import+", my_rewatch_value = "+my_rewatch_value+", my_comments = "+my_comments+", my_tags = "+my_tags+", my_score = "+my_score+", series_episodes = "+series_episodes+", my_times_watched = "+my_times_watched+", my_rewatching_ep = "+my_rewatching_ep+"]";
    }
}