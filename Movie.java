/**
 * Created by Nick on 3/28/2016.
 */
public class Movie {

    private String title;
    private String releaseDate;
    private String overview;

    Movie(){
        title = "";
        releaseDate = "";
        overview = "";
    }

    Movie(String t, String r, String o){
        title = t;
        releaseDate = r;
        overview = o;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String toString()
    {
        return title + ", " + releaseDate + ": " + overview;
    }
}
