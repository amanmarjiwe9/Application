package rspl_rahul.com.browserapplication;

/**
 * Created by rspl-aman on 12/10/17.
 */

public class Info {
    String id;
    String name;
    String website;

    public Info(String id, String name, String website) {
        this.id = id;
        this.name = name;
        this.website = website;
    }

    public Info() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
