package example.android.com.top10downloadingapps;

//this class is a container to store single application in the list

public class Application {
    private String name;
    private String artist;
    private String releaseDate;

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Name: "+ getName()+"\n"+
                "Artist:" +getArtist()+"\n"+
                "Release Date:" + getReleaseDate()+"\n";
    }
}
